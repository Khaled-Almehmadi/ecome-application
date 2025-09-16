package com.app.ecomeapplication.dto;

import com.app.ecomeapplication.model.OrderItem;
import com.app.ecomeapplication.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> Items;
    private LocalDateTime createdAt;
}
