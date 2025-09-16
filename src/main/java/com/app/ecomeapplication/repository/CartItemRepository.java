package com.app.ecomeapplication.repository;

import com.app.ecomeapplication.model.CartItem;
import com.app.ecomeapplication.model.Product;
import com.app.ecomeapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);



    List<CartItem> findByUser_Id(Long userId);


    void deleteByUser(User u);
}
