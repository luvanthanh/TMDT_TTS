package com.tmdttts.backend.controller;

import com.tmdttts.backend.dto.request.order.CheckoutRequest;
import com.tmdttts.backend.dto.response.order.OrderResponse;
import com.tmdttts.backend.dto.response.order.CheckoutResponse;
import com.tmdttts.backend.security.JwtUserPrincipal;
import com.tmdttts.backend.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service){this.service=service;}
    @PostMapping("/checkout") public CheckoutResponse checkout(@AuthenticationPrincipal JwtUserPrincipal user,@Valid @RequestBody CheckoutRequest body,jakarta.servlet.http.HttpServletRequest request){return service.checkout(user.userId(),body,request.getRemoteAddr());}
    @GetMapping("/orders") public List<OrderResponse> orders(@AuthenticationPrincipal JwtUserPrincipal user){return service.myOrders(user.userId());}
    @GetMapping("/orders/{id}") public OrderResponse get(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long id){return service.getMyOrder(user.userId(),id);}
    @PostMapping("/orders/{id}/cancel") public OrderResponse cancel(@AuthenticationPrincipal JwtUserPrincipal user,@PathVariable Long id){return service.cancel(user.userId(),id);}
}
