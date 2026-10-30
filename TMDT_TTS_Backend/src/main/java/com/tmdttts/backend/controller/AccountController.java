package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.request.account.AddressRequest;
import com.tmdttts.backend.dto.request.account.UpdateProfileRequest;
import com.tmdttts.backend.dto.response.user.UserResponse;
import com.tmdttts.backend.dto.response.account.AddressResponse;
import com.tmdttts.backend.dto.response.MessageResponse;
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
    @GetMapping public UserResponse get(@AuthenticationPrincipal JwtUserPrincipal user){return service.profile(user.userId());}
    @PatchMapping public UserResponse update(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody UpdateProfileRequest body){return service.updateProfile(user.userId(),body);}
    @GetMapping("/addresses") public List<AddressResponse> addresses(@AuthenticationPrincipal JwtUserPrincipal user){return service.addresses(user.userId());}
    @PostMapping("/addresses") public AddressResponse addAddress(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody AddressRequest body){return service.addAddress(user.userId(),body);}
    @PutMapping("/addresses/{addressId}") public AddressResponse updateAddress(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long addressId,@Valid @RequestBody AddressRequest body){return service.updateAddress(user.userId(),addressId,body);}
    @DeleteMapping("/addresses/{addressId}") public MessageResponse deleteAddress(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long addressId){service.deleteAddress(user.userId(),addressId);return new MessageResponse("Đã xóa địa chỉ");}
}
