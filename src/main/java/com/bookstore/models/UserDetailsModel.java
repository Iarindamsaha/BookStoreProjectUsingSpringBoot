package com.bookstore.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String fullName;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    CartModel cart;

}
