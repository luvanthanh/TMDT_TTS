# TMDT_TTS Backend (starter)

Stack: Java 21, Spring Boot 3.5.6, Spring Data JPA, PostgreSQL, Spring Security, BCrypt, JWT libraries prepared for auth implementation.

## Run in IntelliJ
1. Open this folder as a Maven project.
2. In pgAdmin 4 create database `TMDT_TTS` and run the finalized SQL schema before starting this app.
3. Set environment variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` to match PostgreSQL.
4. Set `APP_JWT_SECRET` to a Base64-encoded random secret of at least 32 bytes (auth/JWT is the next implementation phase).
5. Run `TmdtTtsBackendApplication`.

`spring.jpa.hibernate.ddl-auto=validate`: Hibernate validates the schema; it does not create/alter tables.

## Implemented starter endpoints
- `GET /api/categories` — active categories
- `GET /api/categories/{id}` — category details
- `GET /api/products` — active products; optional `keyword` and `categoryId`
- `GET /api/products/{id}` — active product details
- `GET /api/products/slug/{slug}` — active product by slug

## Important scope note
This is the first runnable foundation, not the complete Figma backend yet. The remaining work is auth/JWT + roles, address/account, product images/variants/admin CRUD, cart/wishlist, checkout/order/order history, coupon validation, VNPay create-payment + verified return/IPN, reviews/returns, contact/newsletter, and Postman collection. Implement those in stages and preserve the provided PostgreSQL schema/column names. Do not mark a VNPay order paid from the browser return alone; validate the signature, amount and provider response, and make IPN idempotent.
