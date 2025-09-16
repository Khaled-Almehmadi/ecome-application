package com.app.ecomeapplication.dto;

import com.app.ecomeapplication.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role ;
    private AddressDTO address;
}
