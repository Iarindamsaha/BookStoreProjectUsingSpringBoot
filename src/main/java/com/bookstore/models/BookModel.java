package com.bookstore.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Products")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int productId;
    String bookName;
    String bookAuthor;
    String bookPicture;
    int price;
    int quantity;


}
