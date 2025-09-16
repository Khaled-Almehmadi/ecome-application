package com.app.ecomeapplication.service;

import com.app.ecomeapplication.dto.CartitemRequest;
import com.app.ecomeapplication.model.CartItem;
import com.app.ecomeapplication.model.Product;
import com.app.ecomeapplication.model.User;
import com.app.ecomeapplication.repository.CartItemRepository;
import com.app.ecomeapplication.repository.ProductRepository;
import com.app.ecomeapplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartitemRequest request) {

        Optional<Product> productOptional= productRepository.findById(request.getProductId());
        if (productOptional.isEmpty())
            return false ;
        Product product = productOptional.get();
        if(product.getQuantity() < request.getQuantity())
            return false ;

       Optional<User> userOptional  = userRepository.findById(Long.valueOf(userId));

       if(userOptional.isEmpty())
            return false ;
       User user = userOptional.get();

       CartItem exsistingCartItem = cartItemRepository.findByUserAndProduct(user,product);

       if(exsistingCartItem != null){
           //if user already have product on cart we top up the qty
           exsistingCartItem.setQuantity(request.getQuantity()+exsistingCartItem.getQuantity());
           exsistingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exsistingCartItem.getQuantity())));
           cartItemRepository.save(exsistingCartItem);
       }else {
           CartItem cartItem = new CartItem();
           cartItem.setUser(user);
           cartItem.setProduct(product);
           cartItem.setQuantity(request.getQuantity());
           cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
           cartItemRepository.save(cartItem);
       }

       return true;

    }

    public boolean deleteItemFromCart(String userId, Long productId) {


        Optional<Product> productOptional= productRepository.findById(productId);
        Optional<User> userOptional  = userRepository.findById(Long.valueOf(userId));

       if(productOptional.isPresent() && userOptional.isPresent()){
           cartItemRepository.deleteByUserAndProduct(userOptional.get(),productOptional.get() );
           return true;
       }
       return false;
    }

    public Optional<List<CartItem>> getCartItems(String userId) {

        Optional<User> userOptional  = userRepository.findById(Long.valueOf(userId));

        if(userOptional.isPresent()){
            return Optional.of(cartItemRepository.findByUser_Id(Long.valueOf(userId)));
        }
        return Optional.empty();
    }

    public void clearCart(String userId) {

        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
