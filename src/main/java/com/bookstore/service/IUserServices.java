package com.bookstore.service;

import com.bookstore.dto.*;
import com.bookstore.models.BookModel;
import com.bookstore.models.CartBookModel;

import java.awt.print.Book;
import java.util.List;

public interface IUserServices {
    void registrationAPI(RegistrationDTO register);
    String loginAPI(LoginDTO login);
    void addToCart(String token, int bookId);
    UserCartDTO deleteFromCart(String token,int bookId);
    UserCartDTO plusButton(String token,int bookId);
    UserCartDTO minusButton(String token,int bookId);
    List<CartBookModel> cartDetails(String token);
    List<BookModel> sortByPriceLowToHigh();
    List<BookModel> sortByPriceHighToLow();
    OrderConfirmDTO placeOrder(String token, OrderDTO orderDetails);

}
