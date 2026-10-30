# API tham khảo

Các trường request được kiểm tra bằng Jakarta Validation. Response là DTO; mật khẩu/hash và entity JPA không được trả ra API. Lỗi dùng HTTP status 400/401/403/404/409 cùng thông điệp của Spring.

## Tài khoản

`POST /api/auth/register`

```json
{"name":"Nguyen Van A","email":"a@example.com","phone":"0900000000","password":"password123"}
```

`POST /api/auth/login`

```json
{"email":"a@example.com","password":"password123"}
```

Cả hai trả về:

```json
{"userId":1,"name":"Nguyen Van A","email":"a@example.com","role":"CUSTOMER","token":"<jwt>","tokenType":"Bearer"}
```

`GET /api/account` trả hồ sơ `{id,name,email,phone,role}`; `PATCH /api/account` nhận `{name,phone}`. Email không đổi qua endpoint hồ sơ.

Địa chỉ: `GET/POST /api/account/addresses`, `PUT/DELETE /api/account/addresses/{addressId}`. POST/PUT nhận `{recipientName,phone,detail,ward,district,city,postalCode,defaultAddress}`.

## Danh mục và sản phẩm

- `GET /api/categories`, `GET /api/categories/{id}`
- `GET /api/products?keyword=...&categoryId=...`
- `GET /api/products/{id}`, `GET /api/products/slug/{slug}`
- `GET /api/products/featured`, `GET /api/products/flash-sale`

Product response gồm thông tin sản phẩm, danh mục, `images[]` và các `variants[]` đang hoạt động. Giá biến thể `price` có thể null; khi đó dùng giá sale của sản phẩm nếu có, nếu không dùng giá gốc.

## Wishlist và giỏ hàng

- Wishlist: `GET /api/wishlist`, `POST /api/wishlist/{productId}`, `DELETE /api/wishlist/{productId}`.
- Giỏ hàng: `GET /api/cart`, `POST /api/cart/items`, `PATCH /api/cart/items/{cartItemId}`, `DELETE /api/cart/items/{cartItemId}`.
- Thêm giỏ nhận `{"variantId":12,"quantity":2}`; cập nhật nhận `{"quantity":3}`.
- Giỏ trả `items[]`, `subtotal`, `totalQuantity`. Mỗi dòng có tên sản phẩm, biến thể, giá hiện tại, số lượng, tồn kho và `lineTotal`.

## Checkout và đơn hàng

`POST /api/checkout` tạo đơn từ giỏ hiện tại. Request:

```json
{"recipientName":"Nguyen Van A","phone":"0900000000","addressDetail":"12 Nguyen Trai","ward":"Ben Thanh","district":"Quan 1","city":"Ho Chi Minh","postalCode":"700000","note":"Gọi trước khi giao","paymentMethod":"COD","couponCode":null}
```

`paymentMethod` nhận `COD` hoặc `VNPAY`. Giá/tồn kho/điều kiện coupon được tính lại phía backend; client không gửi giá. Checkout trả `{order, paymentUrl}`; `paymentUrl` null với COD, còn VNPay thì frontend chuyển khách tới URL đó.

`GET /api/orders`, `GET /api/orders/{id}`, `POST /api/orders/{id}/cancel`. Khách chỉ xem/hủy đơn của chính mình. Chỉ đơn đang chờ xử lý, chưa thanh toán và không có giao dịch VNPay đang mở mới được hủy.

VNPay gọi public `GET /api/payments/vnpay/ipn`. Backend kiểm tra chữ ký, mã giao dịch, số tiền và trạng thái; browser return không tự đánh dấu đơn đã thanh toán.

## Liên hệ

`POST /api/contact` công khai, nhận `{fullName,email,phone,subject,content}` và lưu lời nhắn với trạng thái `NEW`.
