package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.CustomerListService;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/wishlist")
public class WishlistController {
    private final CustomerListService service;
    public WishlistController(CustomerListService service){this.service=service;}
    @GetMapping public List<Responses.Product> get(@AuthenticationPrincipal JwtUserPrincipal user){return service.wishlist(user.userId());}
    @PostMapping("/{productId}") public List<Responses.Product> add(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long productId){return service.addWishlist(user.userId(),productId);}
    @DeleteMapping("/{productId}") public List<Responses.Product> remove(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long productId){return service.removeWishlist(user.userId(),productId);}
}
