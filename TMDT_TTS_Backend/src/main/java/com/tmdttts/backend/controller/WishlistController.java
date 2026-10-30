package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.response.product.ProductResponse;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.WishlistService;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService service;
    public WishlistController(WishlistService service){this.service=service;}
    @GetMapping public List<ProductResponse> get(@AuthenticationPrincipal JwtUserPrincipal user){return service.getWishlist(user.userId());}
    @PostMapping("/{productId}") public List<ProductResponse> add(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long productId){return service.addProduct(user.userId(),productId);}
    @DeleteMapping("/{productId}") public List<ProductResponse> remove(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long productId){return service.removeProduct(user.userId(),productId);}
}
