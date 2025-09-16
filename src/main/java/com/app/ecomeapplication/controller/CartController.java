package com.app.ecomeapplication.controller;

import com.app.ecomeapplication.dto.CartitemRequest;
import com.app.ecomeapplication.model.CartItem;
import com.app.ecomeapplication.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID")String userId,
            @RequestBody CartitemRequest request
    ) {
   if (!cartService.addToCart(userId,request)) {
       return ResponseEntity.badRequest().body("Product : Out of Stock / Not found , or User not found");
   }

    return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        @DeleteMapping("/items/{productId}")
        public ResponseEntity<Void> removeFromCart( @RequestHeader("X-USER-ID") String userId , @PathVariable Long productId) {

       boolean deleted = cartService.deleteItemFromCart(userId ,productId);

       return deleted ?  ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }


        @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-USER-ID") String userId) {

     Optional<List<CartItem>> cartItems = cartService.getCartItems(userId);

    if(cartItems.isPresent()) {

        return ResponseEntity.ok(cartItems.get());
    }

    return ResponseEntity.notFound().build();

        }

}
