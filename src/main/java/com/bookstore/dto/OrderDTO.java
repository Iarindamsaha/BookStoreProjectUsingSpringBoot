package com.bookstore.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class OrderDTO {

    String name;
    long phoneNumber;
    long pinCode;
    String locality;
    String address;
    String cityOrTown;
    String landmark;
    String addressType;

}
