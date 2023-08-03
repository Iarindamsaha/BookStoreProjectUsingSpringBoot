package com.bookstore.dto;

import com.bookstore.models.CartModel;
import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;
    private  String fullName;
    private String email;
    private String password;
    CartModel cart;
}
