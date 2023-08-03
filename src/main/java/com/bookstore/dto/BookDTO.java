package com.bookstore.dto;

import lombok.Data;

@Data
public class BookDTO {
    String bookName;
    String bookAuthor;
    String bookPicture;
    int price;
    int quantity;
}
