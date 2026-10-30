package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.AccountService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/account")
public class AccountController {
    private final AccountService service;
    public AccountController(AccountService service){this.service=service;}
    @GetMapping public Responses.User get(@AuthenticationPrincipal JwtUserPrincipal user){return service.profile(user.userId());}
    @PatchMapping public Responses.User update(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody Requests.Profile body){return service.updateProfile(user.userId(),body);}
    @GetMapping("/addresses") public List<Responses.Address> addresses(@AuthenticationPrincipal JwtUserPrincipal user){return service.addresses(user.userId());}
    @PostMapping("/addresses") public Responses.Address addAddress(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody Requests.Address body){return service.addAddress(user.userId(),body);}
    @PutMapping("/addresses/{addressId}") public Responses.Address updateAddress(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long addressId,@Valid @RequestBody Requests.Address body){return service.updateAddress(user.userId(),addressId,body);}
    @DeleteMapping("/addresses/{addressId}") public Responses.Message deleteAddress(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long addressId){service.deleteAddress(user.userId(),addressId);return new Responses.Message("Đã xóa địa chỉ");}
}
