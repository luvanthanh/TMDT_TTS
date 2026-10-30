# TMDT_TTS Backend

Backend cửa hàng trực tuyến dùng Java 21, Spring Boot 3.5.6, Spring Data JPA và PostgreSQL.

## Chạy ứng dụng

1. Tạo PostgreSQL database `TMDT_TTS` và chạy schema SQL đã thống nhất.
2. Cấu hình `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
3. Cấu hình `APP_JWT_SECRET` bằng chuỗi Base64 chứa ít nhất 32 byte ngẫu nhiên.
4. Chạy `TmdtTtsBackendApplication` trên cổng 8080.

Hibernate dùng `ddl-auto: validate`; ứng dụng chỉ kiểm tra schema, không tự tạo/sửa bảng.

Để dùng VNPay, cấu hình thêm `APP_VNPAY_TMN_CODE`, `APP_VNPAY_HASH_SECRET`, `APP_VNPAY_RETURN_URL` (URL frontend nhận redirect) và `APP_VNPAY_IPN_URL` (URL public trỏ tới `/api/payments/vnpay/ipn`). Có thể đặt `APP_VNPAY_PAY_URL`; mặc định là sandbox. Chỉ IPN có chữ ký hợp lệ mới cập nhật thanh toán thành công.

## API

Request/response mẫu, quyền truy cập và danh sách endpoint ở [docs/API.md](docs/API.md). API yêu cầu đăng nhập nhận header `Authorization: Bearer <token>`. API danh mục, sản phẩm, xác thực và gửi liên hệ được mở công khai.

DTO được chia riêng theo chức năng tại `dto/request/<feature>` và `dto/response/<feature>`; mapper cũng tách theo resource (`ProductMapper`, `CartMapper`, `OrderMapper`, `AccountMapper`, `CategoryMapper`). Controller gọi service, service xử lý nghiệp vụ và mapper chuyển entity sang response DTO.

Các luồng đã có: xác thực khách hàng, đọc danh mục/sản phẩm, giỏ hàng, wishlist, checkout COD/VNPay, xem/hủy đơn đang chờ, thông tin cá nhân/địa chỉ và form liên hệ. Trang About và 404 là nội dung frontend.

Phần quản trị sản phẩm/đơn hàng, newsletter, đánh giá và trả hàng chưa triển khai vì không cần cho luồng mua hàng khách hàng cốt lõi hiện tại.
