package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.request.cart.AddCartItemRequest;
import com.tmdttts.backend.dto.request.cart.UpdateCartItemRequest;
import com.tmdttts.backend.dto.response.cart.CartResponse;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/cart")
public class CartController {
    private final CartService service;
    public CartController(CartService service){this.service=service;}
    @GetMapping public CartResponse get(@AuthenticationPrincipal JwtUserPrincipal user){return service.getCart(user.userId());}
    @PostMapping("/items") public CartResponse add(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody AddCartItemRequest body){return service.addItem(user.userId(),body.variantId(),body.quantity());}
    @PatchMapping("/items/{itemId}") public CartResponse update(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long itemId,@Valid @RequestBody UpdateCartItemRequest body){return service.updateItem(user.userId(),itemId,body.quantity());}
    @DeleteMapping("/items/{itemId}") public CartResponse remove(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long itemId){return service.removeItem(user.userId(),itemId);}
}
