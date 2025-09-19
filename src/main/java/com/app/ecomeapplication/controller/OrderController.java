package com.app.ecomeapplication.controller;

import com.app.ecomeapplication.dto.OrderResponse;
import com.app.ecomeapplication.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder( @RequestHeader("X-USER-ID") String userId) {



        return  orderService.createOrder(userId).map(orderResponse -> new ResponseEntity<>(orderResponse , HttpStatus.CREATED)).orElseGet(()->ResponseEntity.badRequest().build());
    }
}
