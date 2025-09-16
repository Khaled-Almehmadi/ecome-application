package com.app.ecomeapplication.dto;

import com.app.ecomeapplication.model.UserRole;
import lombok.Data;


@Data
public class AddressDTO {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode ;
}
