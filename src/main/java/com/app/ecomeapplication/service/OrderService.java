package com.app.ecomeapplication.service;

import com.app.ecomeapplication.dto.OrderItemDTO;
import com.app.ecomeapplication.dto.OrderResponse;
import com.app.ecomeapplication.model.*;
import com.app.ecomeapplication.repository.OrderRepository;
import com.app.ecomeapplication.repository.ProductRepository;
import com.app.ecomeapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository ;
    private final OrderRepository orderRepository ;
    private final ProductRepository productRepository ;
    public Optional<OrderResponse> createOrder(String userId) {


        Optional<List<CartItem>> cartItems = cartService.getCartItems(userId);

        if (cartItems.isEmpty()) {

            return Optional.empty();

        }

        Optional<User>  user = Optional.of(userRepository.getById(Long.valueOf(userId)));
        if (user.isEmpty()) {

            return Optional.empty();
        }

        BigDecimal totalPrice = cartItems.get()
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order = new Order();
        order.setUser(user.get());
        order.setTotalAmount(totalPrice);
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItems.get()
                .stream()
                .map(cartItem -> new OrderItem(
                        null,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order
                ))
                .toList();

        order.setItems(orderItems);

       Order savedOrder = orderRepository.save(order);


       cartService.clearCart(userId);

        return Optional.of(maptoOrderResponse(savedOrder));
    }

    private OrderResponse maptoOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderStatus(order.getStatus());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setItems(order.getItems().stream().map(orderItem -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(orderItem.getId());
            itemDTO.setProductId(orderItem.getProduct().getId());
            itemDTO.setQuantity(orderItem.getQuantity());
            itemDTO.setPrice(orderItem.getPrice());
            return itemDTO;
        })
                .toList());

        return orderResponse;
    }
}
