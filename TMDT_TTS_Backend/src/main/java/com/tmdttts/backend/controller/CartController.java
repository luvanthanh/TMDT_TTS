package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.Requests;
import com.tmdttts.backend.dto.Responses;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.CustomerListService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/cart")
public class CartController {
    private final CustomerListService service;
    public CartController(CustomerListService service){this.service=service;}
    @GetMapping public Responses.Cart get(@AuthenticationPrincipal JwtUserPrincipal user){return service.cart(user.userId());}
    @PostMapping("/items") public Responses.Cart add(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody Requests.CartItem body){return service.addCartItem(user.userId(),body.variantId(),body.quantity());}
    @PatchMapping("/items/{itemId}") public Responses.Cart update(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long itemId,@Valid @RequestBody Requests.Quantity body){return service.updateCartItem(user.userId(),itemId,body.quantity());}
    @DeleteMapping("/items/{itemId}") public Responses.Cart remove(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long itemId){return service.removeCartItem(user.userId(),itemId);}
}
