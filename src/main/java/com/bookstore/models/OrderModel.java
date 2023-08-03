package com.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    String name;
    long phoneNumber;
    long pinCode;
    String locality;
    String address;
    String cityOrTown;
    String landmark;
    String addressType;
    int cartId;
    String bookId;
    int price;



}
