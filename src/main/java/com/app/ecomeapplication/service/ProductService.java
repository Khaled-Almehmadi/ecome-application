package com.app.ecomeapplication.service;

import com.app.ecomeapplication.dto.ProductRequest;
import com.app.ecomeapplication.dto.ProductResponse;
import com.app.ecomeapplication.model.Product;
import com.app.ecomeapplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        updateProductFromRequest(product , productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setQuantity(savedProduct.getQuantity());
        response.setActive(savedProduct.isActive());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {


    product.setName(productRequest.getName());
    product.setCategory(productRequest.getCategory());
    product.setImageUrl(productRequest.getImageUrl());
    product.setQuantity(productRequest.getQuantity());
    product.setDescription(productRequest.getDescription());
    product.setPrice(productRequest.getPrice());


    }


    public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {

       return productRepository.findById(id)
                .map(product -> {

            updateProductFromRequest(product, productRequest);
            Product savedProduct = productRepository.save(product);
            return mapToProductResponse(savedProduct);

        });
    }

    public List<ProductResponse> getAllProducts() {
        
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
}

    public boolean deleteProduct(Long id) {

        return productRepository.findById(id).map(product -> {
                product.setActive(false);
                productRepository.save(product);
                return  true;
        }).orElse(false);

    }



    public List<ProductResponse> searchProducts(String keyword) {

        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }
}