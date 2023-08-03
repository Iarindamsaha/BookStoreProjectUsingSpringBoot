package com.bookstore.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data

public class OrderConfirmDTO {

    String name;
    String products;
    int totalPrice;
    String orderDate;

}
