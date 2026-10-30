package com.tmdttts.backend.service;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.entity.User;
import com.tmdttts.backend.mapper.ApiMapper;
import com.tmdttts.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt; private final ApiMapper mapper;
    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt, ApiMapper mapper) {
        this.users=users; this.encoder=encoder; this.jwt=jwt; this.mapper=mapper;
    }
    @Transactional public Responses.Auth register(Requests.Register request) {
        String email=request.email().trim().toLowerCase();
        if(users.existsByUserEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Email đã được sử dụng");
        User user=new User(); user.setUserName(request.name().trim()); user.setUserEmail(email);
        user.setUserPhone(request.phone()); user.setUserPasswordHash(encoder.encode(request.password()));
        user.setUserRole("CUSTOMER"); user.setUserStatus("ACTIVE"); users.save(user);
        return auth(user);
    }
    @Transactional(readOnly=true) public Responses.Auth login(Requests.Login request) {
        User user=users.findByUserEmailIgnoreCase(request.email().trim()).filter(u->encoder.matches(request.password(),u.getUserPasswordHash()))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email hoặc mật khẩu không đúng"));
        if(!"ACTIVE".equals(user.getUserStatus())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Tài khoản không hoạt động");
        return auth(user);
    }
    private Responses.Auth auth(User u) { return new Responses.Auth(u.getUserId(),u.getUserName(),u.getUserEmail(),u.getUserRole(),jwt.issue(u.getUserId(),u.getUserRole()),"Bearer"); }
}
