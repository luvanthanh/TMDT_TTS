package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.*;
import com.tmdttts.backend.mapper.ApiMapper;
import com.tmdttts.backend.repository.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
    private final UserRepository users; private final CartRepository carts; private final CartItemRepository cartItems;
    private final ProductVariantRepository variants; private final OrderRepository orders; private final OrderItemRepository orderItems;
    private final OrderStatusHistoryRepository history; private final CouponRepository coupons;
    private final CouponRedemptionRepository redemptions; private final PaymentRepository payments;
    private final ApiMapper mapper; private final VnpayService vnpay;
    public OrderService(UserRepository users,CartRepository carts,CartItemRepository cartItems,ProductVariantRepository variants,
            OrderRepository orders,OrderItemRepository orderItems,OrderStatusHistoryRepository history,CouponRepository coupons,
            CouponRedemptionRepository redemptions,PaymentRepository payments,ApiMapper mapper,VnpayService vnpay) {
        this.users=users;this.carts=carts;this.cartItems=cartItems;this.variants=variants;this.orders=orders;this.orderItems=orderItems;
        this.history=history;this.coupons=coupons;this.redemptions=redemptions;this.payments=payments;this.mapper=mapper;this.vnpay=vnpay;
    }
    @Transactional public Responses.CheckoutResult checkout(Long userId,Requests.Checkout request,String clientIp) {
        User user=users.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Tài khoản không tồn tại"));
        Cart cart=carts.findByCartUser_UserId(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Giỏ hàng đang trống"));
        List<CartItem> cartLines=cartItems.findByCartItemCart_CartIdOrderByCartItemAddedAtAsc(cart.getCartId());
        if(cartLines.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Giỏ hàng đang trống");
        Map<Long,ProductVariant> locked=new LinkedHashMap<>();
        cartLines.stream().map(i->i.getCartItemProductVariant().getProductVariantId()).distinct().sorted().forEach(id->
                locked.put(id,variants.findByIdForUpdate(id).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"Một sản phẩm không còn tồn tại"))));
        BigDecimal subtotal=BigDecimal.ZERO;
        for(CartItem item:cartLines) {
            ProductVariant v=locked.get(item.getCartItemProductVariant().getProductVariantId());
            if(!"ACTIVE".equals(v.getProductVariantStatus())||!"ACTIVE".equals(v.getProductVariantProduct().getProductStatus()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Có sản phẩm không còn được bán");
            if(item.getCartItemQuantity()>v.getProductVariantStockQuantity())throw new ResponseStatusException(HttpStatus.CONFLICT,"Tồn kho không đủ cho "+v.getProductVariantProduct().getProductName());
            subtotal=subtotal.add(unitPrice(v).multiply(BigDecimal.valueOf(item.getCartItemQuantity())));
        }
        Coupon coupon=null;BigDecimal discount=BigDecimal.ZERO;
        if(request.couponCode()!=null&&!request.couponCode().isBlank()) {
            coupon=coupons.findByCodeForUpdate(request.couponCode().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mã giảm giá không hợp lệ"));
            var now=java.time.OffsetDateTime.now();
            if(!"ACTIVE".equals(coupon.getCouponStatus())||(coupon.getCouponStartAt()!=null&&now.isBefore(coupon.getCouponStartAt()))||(coupon.getCouponEndAt()!=null&&now.isAfter(coupon.getCouponEndAt()))||(coupon.getCouponUsageLimit()!=null&&coupon.getCouponUsedCount()>=coupon.getCouponUsageLimit())||subtotal.compareTo(coupon.getCouponMinOrderAmount())<0||redemptions.existsByCouponRedemptionUser_UserIdAndCouponRedemptionCoupon_CouponId(userId,coupon.getCouponId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mã giảm giá hết hạn hoặc không đủ điều kiện");
            discount="PERCENT".equals(coupon.getCouponDiscountType())?subtotal.multiply(coupon.getCouponDiscountValue()).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP):coupon.getCouponDiscountValue();
            if(coupon.getCouponMaxDiscountAmount()!=null)discount=discount.min(coupon.getCouponMaxDiscountAmount());discount=discount.min(subtotal);
        }
        String method=request.paymentMethod().trim().toUpperCase(Locale.ROOT);
        if(!List.of("COD","VNPAY").contains(method))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phương thức thanh toán không được hỗ trợ");
        Order order=new Order();order.setOrderCode("OD"+UUID.randomUUID().toString().replace("-","").substring(0,30).toUpperCase(Locale.ROOT));
        order.setOrderUser(user);order.setOrderCoupon(coupon);order.setOrderCouponCodeSnapshot(coupon==null?null:coupon.getCouponCode());
        order.setOrderRecipientName(request.recipientName().trim());order.setOrderRecipientPhone(request.phone().trim());
        order.setOrderShippingAddress(addressText(request));order.setOrderSubtotal(subtotal);order.setOrderDiscountAmount(discount);
        order.setOrderShippingFee(BigDecimal.ZERO);order.setOrderTotalAmount(subtotal.subtract(discount));order.setOrderCustomerNote(request.note());
        order.setOrderPaymentMethod(method);order.setOrderPaymentStatus("VNPAY".equals(method)?"PENDING":"UNPAID");order.setOrderStatus("PENDING");orders.save(order);
        for(CartItem line:cartLines) {
            ProductVariant v=locked.get(line.getCartItemProductVariant().getProductVariantId());Product p=v.getProductVariantProduct();
            BigDecimal price=unitPrice(v);OrderItem oi=new OrderItem();oi.setOrderItemOrder(order);oi.setOrderItemProductVariant(v);
            oi.setOrderItemProductName(p.getProductName());oi.setOrderItemSkuSnapshot(v.getProductVariantSku());
            oi.setOrderItemVariantSnapshot(String.join(" / ",java.util.stream.Stream.of(v.getProductVariantColor(),v.getProductVariantSize()).filter(s->s!=null&&!s.isBlank()).toList()));
            oi.setOrderItemUnitPrice(price);oi.setOrderItemQuantity(line.getCartItemQuantity());oi.setOrderItemLineTotal(price.multiply(BigDecimal.valueOf(line.getCartItemQuantity())));
            orderItems.save(oi);v.setProductVariantStockQuantity(v.getProductVariantStockQuantity()-line.getCartItemQuantity());
        }
        OrderStatusHistory h=new OrderStatusHistory();h.setOrderStatusHistoryOrder(order);h.setOrderStatusHistoryOldStatus(null);h.setOrderStatusHistoryNewStatus("PENDING");h.setOrderStatusHistoryNote("Tạo đơn hàng");h.setOrderStatusHistoryChangedByUser(user);history.save(h);
        if(coupon!=null){coupon.setCouponUsedCount(coupon.getCouponUsedCount()+1);CouponRedemption redemption=new CouponRedemption();redemption.setCouponRedemptionCoupon(coupon);redemption.setCouponRedemptionUser(user);redemption.setCouponRedemptionOrder(order);redemptions.save(redemption);}
        cartItems.deleteByCartItemCart_CartId(cart.getCartId());
        String paymentUrl=null;
        if("VNPAY".equals(method)){if(order.getOrderTotalAmount().signum()<=0)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Đơn hàng VNPay phải có giá trị lớn hơn 0");Payment payment=new Payment();payment.setPaymentOrder(order);payment.setPaymentProvider("VNPAY");payment.setPaymentTransactionRef(order.getOrderCode());payment.setPaymentAmount(order.getOrderTotalAmount());payments.save(payment);
            paymentUrl=vnpay.createPaymentUrl(payment.getPaymentTransactionRef(),order.getOrderTotalAmount().movePointRight(2).longValueExact(),clientIp,"Thanh toan "+order.getOrderCode());}
        return new Responses.CheckoutResult(mapper.order(order,orderItems.findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(order.getOrderId())),paymentUrl);
    }
    @Transactional(readOnly=true) public List<Responses.Order> myOrders(Long userId) {
        return orders.findByOrderUser_UserIdOrderByOrderCreatedAtDesc(userId).stream().map(o->mapper.order(o,orderItems.findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(o.getOrderId()))).toList();
    }
    @Transactional(readOnly=true) public Responses.Order getMyOrder(Long userId,Long orderId) {
        Order o=orders.findByOrderIdAndOrderUser_UserId(orderId,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy đơn hàng"));
        return mapper.order(o,orderItems.findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(o.getOrderId()));
    }
    @Transactional public Responses.Order cancel(Long userId,Long orderId) {
        Order o=orders.findByOrderIdAndOrderUser_UserId(orderId,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy đơn hàng"));
        if(!"PENDING".equals(o.getOrderStatus())||"PAID".equals(o.getOrderPaymentStatus())||("VNPAY".equals(o.getOrderPaymentMethod())&&"PENDING".equals(o.getOrderPaymentStatus())))throw new ResponseStatusException(HttpStatus.CONFLICT,"Chỉ có thể hủy đơn chưa thanh toán và không có giao dịch đang xử lý");
        for(OrderItem item:orderItems.findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(orderId))if(item.getOrderItemProductVariant()!=null){var v=variants.findByIdForUpdate(item.getOrderItemProductVariant().getProductVariantId()).orElse(null);if(v!=null)v.setProductVariantStockQuantity(v.getProductVariantStockQuantity()+item.getOrderItemQuantity());}
        o.setOrderStatus("CANCELLED");if("PENDING".equals(o.getOrderPaymentStatus()))o.setOrderPaymentStatus("FAILED");
        OrderStatusHistory h=new OrderStatusHistory();h.setOrderStatusHistoryOrder(o);h.setOrderStatusHistoryOldStatus("PENDING");h.setOrderStatusHistoryNewStatus("CANCELLED");h.setOrderStatusHistoryNote("Khách hàng hủy đơn");h.setOrderStatusHistoryChangedByUser(users.findById(userId).orElse(null));history.save(h);
        redemptions.findByCouponRedemptionOrder_OrderId(orderId).ifPresent(r->{Coupon c=r.getCouponRedemptionCoupon();c.setCouponUsedCount(Math.max(0,c.getCouponUsedCount()-1));redemptions.delete(r);});
        return mapper.order(o,orderItems.findByOrderItemOrder_OrderIdOrderByOrderItemIdAsc(orderId));
    }
    @Transactional public Map<String,String> handleVnpayIpn(Map<String,String> params) {
        if(!vnpay.verify(params))return Map.of("RspCode","97","Message","Invalid signature");
        Payment payment=payments.findByTransactionRefForUpdate(params.get("vnp_TxnRef")).orElse(null);
        if(payment==null)return Map.of("RspCode","01","Message","Order not found");
        Order order=payment.getPaymentOrder();
        if(!Long.toString(payment.getPaymentAmount().movePointRight(2).longValueExact()).equals(params.get("vnp_Amount")))return Map.of("RspCode","04","Message","Invalid amount");
        if("SUCCESS".equals(payment.getPaymentStatus()))return Map.of("RspCode","00","Message","Confirm Success");
        String response=params.getOrDefault("vnp_ResponseCode","");String transactionStatus=params.getOrDefault("vnp_TransactionStatus","");
        payment.setPaymentResponseCode(response);payment.setPaymentProviderTransactionNo(params.get("vnp_TransactionNo"));
        payment.setPaymentBankCode(params.get("vnp_BankCode"));payment.setPaymentCardType(params.get("vnp_CardType"));
        if("00".equals(response)&&"00".equals(transactionStatus)) {payment.setPaymentStatus("SUCCESS");payment.setPaymentPaidAt(java.time.OffsetDateTime.now());order.setOrderPaymentStatus("PAID");}
        else {payment.setPaymentStatus("FAILED");order.setOrderPaymentStatus("FAILED");}
        return Map.of("RspCode","00","Message","Confirm Success");
    }
    private BigDecimal unitPrice(ProductVariant v){Product p=v.getProductVariantProduct();return v.getProductVariantPriceOverride()!=null?v.getProductVariantPriceOverride():(p.getProductSalePrice()!=null?p.getProductSalePrice():p.getProductPrice());}
    private String addressText(Requests.Checkout r){return String.join(", ",java.util.stream.Stream.of(r.addressDetail(),r.ward(),r.district(),r.city(),r.postalCode()).filter(s->s!=null&&!s.isBlank()).toList());}
}
