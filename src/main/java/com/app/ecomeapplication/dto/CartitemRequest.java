package com.app.ecomeapplication.dto;

import lombok.Data;

@Data
public class CartitemRequest {

    private Long productId;
    private Integer quantity;

}
