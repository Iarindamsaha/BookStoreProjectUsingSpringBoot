package com.bookstore.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserCartDTO {

    String bookName;
    int price;
    int quantity;

}
