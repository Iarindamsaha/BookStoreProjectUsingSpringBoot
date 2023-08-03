package com.bookstore.controller;

import com.bookstore.configurations.Response;
import com.bookstore.dto.LoginDTO;
import com.bookstore.dto.OrderDTO;
import com.bookstore.dto.RegistrationDTO;
import com.bookstore.dto.UserCartDTO;
import com.bookstore.models.BookModel;
import com.bookstore.models.CartBookModel;
import com.bookstore.service.IUserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storeUser")

public class UserController {

    @Autowired
    IUserServices userServices;
    @Autowired
    Response response;

    @PostMapping("/userRegistration")
    public ResponseEntity<Response> userRegistration(@RequestBody RegistrationDTO register){

        userServices.registrationAPI(register);
        response.setMessage("User Registered Successfully");
        response.setObject(register);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }

    @PostMapping("/userLogin")
    public ResponseEntity<Response> userLogin(@RequestBody LoginDTO login){

        String token = userServices.loginAPI(login);
        response.setMessage("User Logged In");
        response.setObject(token);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/addToCart")
    public ResponseEntity<Response> addProducts(@RequestHeader String token, @RequestParam int bookId){

        userServices.addToCart(token,bookId);
        response.setMessage("Book added Successfully");
        response.setObject(null);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @DeleteMapping("/deleteFromCart")
    public ResponseEntity<Response> deleteProductFromCart(@RequestHeader String token, @RequestParam int bookId){

        UserCartDTO userCartDTO = userServices.deleteFromCart(token,bookId);
        response.setMessage("Book Deleted From Cart");
        response.setObject(userCartDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PutMapping("/plusButton")
    public ResponseEntity<Response> plusButton (@RequestHeader String token, @RequestParam int bookId){

        UserCartDTO cartDetails = userServices.plusButton(token,bookId);
        response.setMessage("Quantity Updated Successfully");
        response.setObject(cartDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PutMapping("/minusButton")
    public ResponseEntity<Response> minusButton (@RequestHeader String token, @RequestParam int bookId){

        UserCartDTO cartDetails = userServices.minusButton(token,bookId);
        response.setMessage("Quantity Updated Successfully");
        response.setObject(cartDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/cartDetails")
    public ResponseEntity<Response> cartDetails(@RequestHeader String token){

        List<CartBookModel> cartDetails = userServices.cartDetails(token);
        response.setMessage("Your Cart Details are");
        response.setObject(cartDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/priceLowToHigh")
    public ResponseEntity<Response> sortPriceLowToHigh(){

        response.setMessage("Price Low To High");
        response.setObject(userServices.sortByPriceLowToHigh());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/priceHighToLow")
    public ResponseEntity<Response> sortPriceHighToLow(){

        response.setMessage("Price High To Low");
        response.setObject(userServices.sortByPriceHighToLow());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token, @RequestBody OrderDTO order){

        response.setMessage("Order Placed Successfully");
        response.setObject(userServices.placeOrder(token,order));
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
