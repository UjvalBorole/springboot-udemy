package com.ecom.application.controllers;

import com.ecom.application.Dto.OrderResponse;
import com.ecom.application.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orerService;

    @PostMapping
    public ResponseEntity<OrderResponse>createOrder(
            @RequestHeader("X-User-ID") String userId
            ){
        return orerService.createOrder(userId)
                .map(orderResponse -> new ResponseEntity<>(orderResponse,HttpStatus.CREATED))
                .orElseGet(()-> ResponseEntity.badRequest().build());
    }
}
