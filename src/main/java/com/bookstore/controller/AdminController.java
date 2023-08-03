package com.bookstore.controller;

import com.bookstore.configurations.Response;
import com.bookstore.dto.BookDTO;
import com.bookstore.service.IAdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookStoreAdmin")
public class AdminController {

    @Autowired
    IAdminServices adminServices;

    @Autowired
    Response response;

    @PostMapping("/addBooks")
    public ResponseEntity<Response> addBooks(@RequestBody BookDTO books){

        adminServices.addBooks(books);
        response.setMessage("Book Added Successfully");
        response.setObject(books);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }

    @PutMapping("/updateBooks/{id}")
    public ResponseEntity<Response> updateBooks(@PathVariable int id , @RequestBody BookDTO updateBook){

        adminServices.updateBooks(id,updateBook);
        response.setMessage("Book Updated Successfully");
        response.setObject(updateBook);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @DeleteMapping("/deleteBooks")
    public ResponseEntity<Response> deleteBook (@RequestParam int id){

        adminServices.deleteBooks(id);
        response.setMessage("Book Deleted Successfully");
        response.setObject(null);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/availableBooks")
    public ResponseEntity<Response> availableBooks(){

        response.setMessage("Available Books Are");
        response.setObject(adminServices.booksAvailable());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

}
