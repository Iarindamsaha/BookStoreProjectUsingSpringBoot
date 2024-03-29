package com.bookstore.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int cartId;

}
