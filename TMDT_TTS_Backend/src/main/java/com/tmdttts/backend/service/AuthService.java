package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.request.auth.RegisterRequest;
import com.tmdttts.backend.dto.request.auth.LoginRequest;
import com.tmdttts.backend.dto.response.auth.AuthResponse;

import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users=users; this.encoder=encoder; this.jwt=jwt;
    }
    @Transactional public AuthResponse register(RegisterRequest request) {
        String email=request.email().trim().toLowerCase();
        if(users.existsByUserEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Email đã được sử dụng");
        User user=new User(); user.setUserName(request.name().trim()); user.setUserEmail(email);
        user.setUserPhone(request.phone()); user.setUserPasswordHash(encoder.encode(request.password()));
        user.setUserRole("CUSTOMER"); user.setUserStatus("ACTIVE"); users.save(user);
        return auth(user);
    }
    @Transactional(readOnly=true) public AuthResponse login(LoginRequest request) {
        User user=users.findByUserEmailIgnoreCase(request.email().trim()).filter(u->encoder.matches(request.password(),u.getUserPasswordHash()))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email hoặc mật khẩu không đúng"));
        if(!"ACTIVE".equals(user.getUserStatus())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Tài khoản không hoạt động");
        return auth(user);
    }
    private AuthResponse auth(User u) { return new AuthResponse(u.getUserId(),u.getUserName(),u.getUserEmail(),u.getUserRole(),jwt.issue(u.getUserId(),u.getUserRole()),"Bearer"); }
}
