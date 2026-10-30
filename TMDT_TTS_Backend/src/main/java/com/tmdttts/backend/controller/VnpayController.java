package com.tmdttts.backend.controller;

import com.tmdttts.backend.service.OrderService;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/payments/vnpay")
public class VnpayController {
    private final OrderService orders;
    public VnpayController(OrderService orders){this.orders=orders;}
    @GetMapping("/ipn") public Map<String,String> ipn(@RequestParam Map<String,String> parameters){return orders.handleVnpayIpn(parameters);}
}
