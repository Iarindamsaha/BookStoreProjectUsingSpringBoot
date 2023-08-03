package com.bookstore.service;

import com.bookstore.dto.*;
import com.bookstore.models.*;
import com.bookstore.repository.*;
import com.bookstore.user_exception.UserException;
import com.bookstore.utility.EmailSendingUtil;
import com.bookstore.utility.JWTUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class UserServicesIMPL implements IUserServices {
    @Autowired
    IUserDetailsRepo userRepo;
    @Autowired
    ICartRepo cartRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmailSendingUtil emailSending;
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    IBookRepo bookRepo;
    @Autowired
    ICartBookRepo cartBookRepo;
    @Autowired
    UserCartDTO userCartDTO;
    @Autowired
    IOrderModel orderModelRepo;
    @Autowired
    OrderConfirmDTO orderConfirmDTO;

    @Override
    public void registrationAPI(RegistrationDTO register) {

        UserDetailsModel userEmailCheck = userRepo.findByEmail(register.getEmail());
        if(userEmailCheck != null){
            throw new UserException("Email Already Used For Another Account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDetailsModel userUsernameCheck = userRepo.findByUsername(register.getUsername());
        if(userUsernameCheck != null){
            throw new UserException("Username Already Taken By Another User",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDetailsModel addUser = modelMapper.map(register,UserDetailsModel.class);
        userRepo.save(addUser);

        String subject = "Greetings " + register.getFullName();
        String body = "Dear " + register.getFullName() + " , \n" +
                "Welcome To Our Online Bookstore, Your account has been created successfully ." +
                "\n Your Username is : " + register.getUsername() +
                "\n Enjoy Shopping With Us";

        emailSending.sendMail(body,subject,register.getEmail());

    }

    @Override
    public String loginAPI(LoginDTO login) {

        UserDetailsModel userLogin = userRepo.findByUsernameAndPassword(login.getUsername(),login.getPassword());
        if(userLogin == null){
            throw new UserException("User Not Registered",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return jwtUtility.generateToken(login);
        }

    }

    @Override
    public void addToCart(String token, int bookId) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetailsModel user = userRepo.findByUsernameAndPassword(loginDetails.getUsername(),loginDetails.getPassword());
        CartModel cart = cartRepo.findByCartId(user.getCart().getCartId());
        Optional<BookModel> book = bookRepo.findById(bookId);
        Optional<CartBookModel> cartCheck = cartBookRepo.findByCartModelAndBookModel(user.getCart(),book);

        if(book.isPresent()) {
            if(cartCheck.isPresent()){
                if(book.get().getQuantity() > 0){
                    cartCheck.get().setUserOrderQuantity(cartCheck.get().getUserOrderQuantity()+1);
                    cartCheck.get().setPrice(cartCheck.get().getPrice()+book.get().getPrice());
                    cartBookRepo.save(cartCheck.get());
                }
                else {
                    throw new UserException("Book Out Of Stock",HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
            else {
                if(book.get().getQuantity() > 0){
                    CartBookModel cartBook = new CartBookModel();
                    cartBook.setCartModel(cart);
                    cartBook.setBookModel(book.get());
                    cartBook.setPrice(book.get().getPrice());
                    cartBookRepo.save(cartBook);
                }
                else {
                    throw new UserException("Book Out of Stock",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        else {
            throw new UserException("Book Not Present",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserCartDTO deleteFromCart(String token, int bookId) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetailsModel user = userRepo.findByUsernameAndPassword(loginDetails.getUsername(),loginDetails.getPassword());
        Optional<BookModel> book = bookRepo.findById(bookId);
        Optional<CartBookModel> cartCheck = cartBookRepo.findByCartModelAndBookModel(user.getCart(),book);

        if(book.isPresent()){
            if(cartCheck.isPresent()){

                userCartDTO.setBookName(book.get().getBookName());
                userCartDTO.setQuantity(cartCheck.get().getUserOrderQuantity());
                userCartDTO.setPrice(cartCheck.get().getPrice());
                cartBookRepo.deleteById(cartCheck.get().getId());
                return userCartDTO;

            }
            else {
                throw new UserException("Cart Is Empty",HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new UserException("Book Not available in Your Cart",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserCartDTO plusButton(String token,int bookId) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetailsModel user = userRepo.findByUsernameAndPassword(loginDetails.getUsername(),loginDetails.getPassword());
        Optional<BookModel> book = bookRepo.findById(bookId);
        Optional<CartBookModel> cartCheck = cartBookRepo.findByCartModelAndBookModel(user.getCart(),book);

        if(book.isPresent()) {
            if (cartCheck.isPresent()) {

                    if (book.get().getQuantity() > 0) {
                        cartCheck.get().setUserOrderQuantity(cartCheck.get().getUserOrderQuantity() + 1);
                        cartCheck.get().setPrice(cartCheck.get().getPrice() + book.get().getPrice());
                        cartBookRepo.save(cartCheck.get());
                        userCartDTO.setBookName(book.get().getBookName());
                        userCartDTO.setQuantity(cartCheck.get().getUserOrderQuantity());
                        userCartDTO.setPrice(cartCheck.get().getPrice());
                        return userCartDTO;
                    }
                    else {
                        throw new UserException("Book Sold Out",HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            else {
                throw new UserException("Cart is Empty", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new UserException("Book Not Available",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserCartDTO minusButton(String token, int bookId) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetailsModel user = userRepo.findByUsernameAndPassword(loginDetails.getUsername(),loginDetails.getPassword());
        Optional<BookModel> book = bookRepo.findById(bookId);
        Optional<CartBookModel> cartCheck = cartBookRepo.findByCartModelAndBookModel(user.getCart(),book);

        if(book.isPresent()) {
            if (cartCheck.isPresent()) {
                if (cartCheck.get().getUserOrderQuantity() == 1) {
                    throw new UserException("Only One Book In Your Cart || Use Remove Option To Remove The Book", HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    cartCheck.get().setUserOrderQuantity(cartCheck.get().getUserOrderQuantity() - 1);
                    cartCheck.get().setPrice(cartCheck.get().getPrice() - book.get().getPrice());
                    cartBookRepo.save(cartCheck.get());
                    userCartDTO.setBookName(book.get().getBookName());
                    userCartDTO.setQuantity(cartCheck.get().getUserOrderQuantity());
                    userCartDTO.setPrice(cartCheck.get().getPrice());
                    return userCartDTO;
                }
            } else {
                throw new UserException("Cart is Empty", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new UserException("Book Not Available",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CartBookModel> cartDetails(String token) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return cartBookRepo.findAll();
        
    }

    @Override
    public List<BookModel> sortByPriceLowToHigh() {

//        Comparator<BookModel> compare = (o1, o2) -> {
//
//            if(o1.getPrice() > o2.getPrice()){
//                return 1;
//            }
//            else{
//                return-1;
//            }
//        };

        //        booksLowToHigh.sort(compare);
        return bookRepo.getAllBooksByPriceLowToHigh();
    }

    @Override
    public List<BookModel> sortByPriceHighToLow() {

//        Comparator<BookModel> compare = (o1, o2) -> {
//
//            if(o1.getPrice() < o2.getPrice()){
//                return 1;
//            }
//            else{
//                return-1;
//            }
//        };

        //        booksHighToLow.sort(compare);
        return bookRepo.getAllBooksByPriceHighToLow();
    }


    @Override
    public OrderConfirmDTO placeOrder(String token, OrderDTO orderDetails) {

        LoginDTO loginDetails = jwtUtility.decodeToken(token);
        if(loginDetails == null){
            throw new UserException("User Not Logged In",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserDetailsModel user = userRepo.findByUsernameAndPassword(loginDetails.getUsername(),loginDetails.getPassword());
        List<CartBookModel> cartBook = cartBookRepo.findAllByCartModel(user.getCart());
        OrderModel orderModel = modelMapper.map(orderDetails,OrderModel.class);

        String bookIds = "";
        String booksNames = "";

        for (CartBookModel cartBookModel : cartBook) {

            bookIds += cartBookModel.getBookModel().getProductId() + ",";
            booksNames += cartBookModel.getBookModel().getBookName() + ",";
            BookModel books = bookRepo.findById(cartBookModel.getBookModel().getProductId()).orElse(null);
            if(books != null){
                books.setQuantity(books.getQuantity()-cartBookModel.getUserOrderQuantity());
                bookRepo.save(books);
            }
            else {
                throw new UserException("Something Went Wrong",HttpStatus.BAD_REQUEST);
            }

        }

        int totalPrice = 0;

        for (CartBookModel cartBookModel : cartBook) {

            totalPrice += cartBookModel.getPrice();

        }



        orderModel.setBookId(bookIds);
        orderModel.setCartId(user.getCart().getCartId());
        orderModel.setPrice(totalPrice);


        orderModelRepo.save(orderModel);

        //Order Details For Output
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
        String dateAndTime = currentDateTime.format(formatter);

        orderConfirmDTO.setName(orderModel.getName());
        orderConfirmDTO.setProducts(orderModel.getBookId());
        orderConfirmDTO.setTotalPrice(orderModel.getPrice());
        orderConfirmDTO.setOrderDate(dateAndTime);

        String subject = " ORDER PLACED SUCCESSFULLY";
        String body = "Your Order Has Been Placed.\n" +
                "Books Ordered Are : " + booksNames + "\nTotal Price : " + orderModel.getPrice() +
                "\nOrder Date : " + dateAndTime +"\n\n\n\n\nThank You For Shopping With us,\n" + "BookStore.com";

        emailSending.sendMail(body,subject,user.getEmail());

        cartBookRepo.deleteAllByCartModel(user.getCart());

        return orderConfirmDTO;

    }

}
