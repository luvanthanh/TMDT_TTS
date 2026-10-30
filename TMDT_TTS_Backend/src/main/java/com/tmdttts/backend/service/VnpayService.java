package com.tmdttts.backend.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VnpayService {
    @Value("${app.vnpay.tmn-code:}") private String tmnCode;
    @Value("${app.vnpay.hash-secret:}") private String hashSecret;
    @Value("${app.vnpay.pay-url:https://sandbox.vnpayment.vn/paymentv2/vpcpay.html}") private String payUrl;
    @Value("${app.vnpay.return-url:}") private String returnUrl;
    @Value("${app.vnpay.ipn-url:}") private String ipnUrl;

    public String createPaymentUrl(String txnRef,long amountInSmallestUnit,String ipAddress,String orderInfo) {
        if(tmnCode.isBlank()||hashSecret.isBlank()||returnUrl.isBlank()||ipnUrl.isBlank())
            throw new IllegalStateException("Cần cấu hình APP_VNPAY_TMN_CODE, APP_VNPAY_HASH_SECRET, APP_VNPAY_RETURN_URL và APP_VNPAY_IPN_URL");
        ZonedDateTime now=ZonedDateTime.now(java.time.ZoneId.of("Asia/Ho_Chi_Minh"));
        Map<String,String> p=new TreeMap<>();
        p.put("vnp_Version","2.1.0");p.put("vnp_Command","pay");p.put("vnp_TmnCode",tmnCode);
        p.put("vnp_Amount",Long.toString(amountInSmallestUnit));p.put("vnp_CurrCode","VND");p.put("vnp_TxnRef",txnRef);
        p.put("vnp_OrderInfo",orderInfo);p.put("vnp_OrderType","other");p.put("vnp_Locale","vn");
        p.put("vnp_ReturnUrl",returnUrl);p.put("vnp_IpnUrl",ipnUrl);
        p.put("vnp_IpAddr",ipAddress==null||ipAddress.isBlank()?"127.0.0.1":ipAddress);
        p.put("vnp_CreateDate",now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        p.put("vnp_ExpireDate",now.plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String hashData=encode(p);String secureHash=hmac(hashData);
        return payUrl+"?"+hashData+"&vnp_SecureHash="+secureHash;
    }
    public boolean verify(Map<String,String> params) {
        String supplied=params.get("vnp_SecureHash");if(supplied==null||hashSecret.isBlank())return false;
        Map<String,String> data=new TreeMap<>(params);data.remove("vnp_SecureHash");data.remove("vnp_SecureHashType");
        return java.security.MessageDigest.isEqual(hmac(encode(data)).getBytes(StandardCharsets.US_ASCII),supplied.getBytes(StandardCharsets.US_ASCII));
    }
    private String encode(Map<String,String> params) {
        return params.entrySet().stream().map(e->URLEncoder.encode(e.getKey(),StandardCharsets.UTF_8)+"="+URLEncoder.encode(e.getValue(),StandardCharsets.UTF_8))
                .collect(java.util.stream.Collectors.joining("&"));
    }
    private String hmac(String data) {
        try { Mac mac=Mac.getInstance("HmacSHA512");mac.init(new SecretKeySpec(hashSecret.getBytes(StandardCharsets.UTF_8),"HmacSHA512"));
            return java.util.HexFormat.of().formatHex(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch(Exception e) { throw new IllegalStateException("Không thể ký yêu cầu VNPay",e); }
    }
}
