package com.app.ecomeapplication.controller;

import com.app.ecomeapplication.dto.ProductRequest;
import com.app.ecomeapplication.dto.ProductResponse;
import com.app.ecomeapplication.model.Product;
import com.app.ecomeapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){

        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);

    }



    @PutMapping  ("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id , @RequestBody  ProductRequest productRequest){

        return productService.updateProduct(productRequest,id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }



    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){

        return  ResponseEntity.ok(productService.getAllProducts());

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> getProduct(@RequestParam String keyword){

        return  ResponseEntity.ok(productService.searchProducts(keyword));

    }

    @DeleteMapping  ("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id ){

        boolean deleted = productService.deleteProduct(id);

        return  deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }
}
