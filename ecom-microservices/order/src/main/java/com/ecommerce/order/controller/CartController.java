package com.ecommerce.order.controller;



import com.ecommerce.order.Dto.CartItemRequest;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(
        @RequestHeader("X-User_ID") String userId,
        @RequestBody CartItemRequest request
    ){
        if(!cartService.addToCart(userId,request)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User not Found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User_ID") String userId,
            @PathVariable String productId
    ){
        boolean deleted = cartService.deleteItemFromCart(userId,productId);
        return deleted ? ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User_ID") String userId
    ){
        return ResponseEntity.ok(cartService.getCart(userId));
    }
}
