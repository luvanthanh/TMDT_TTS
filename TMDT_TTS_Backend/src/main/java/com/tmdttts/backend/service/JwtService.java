package com.tmdttts.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${app.jwt.secret:}") private String secret;
    @Value("${app.jwt.expiration-ms:86400000}") private long expirationMs;

    public String issue(Long userId, String role) {
        Date now = new Date();
        return Jwts.builder().subject(userId.toString()).claim("role", role).issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs)).signWith(key()).compact();
    }

    public JwtUserPrincipal parse(String token) {
        var claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
        return new JwtUserPrincipal(Long.valueOf(claims.getSubject()), claims.get("role", String.class));
    }

    private SecretKey key() {
        if (secret == null || secret.isBlank()) throw new IllegalStateException("APP_JWT_SECRET must be a Base64 secret of at least 32 bytes");
        byte[] bytes = Decoders.BASE64.decode(secret);
        if (bytes.length < 32) throw new IllegalStateException("APP_JWT_SECRET must be at least 32 bytes");
        return Keys.hmacShaKeyFor(bytes);
    }
}
