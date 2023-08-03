package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.models.BookModel;
import com.bookstore.repository.IBookRepo;
import com.bookstore.user_exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServicesIMPL implements IAdminServices {
    @Autowired
    IBookRepo bookRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void addBooks(BookDTO bookDetails) {

        BookModel books = modelMapper.map(bookDetails,BookModel.class);
        bookRepo.save(books);

    }

    @Override
    public void updateBooks(int id, BookDTO updateDetails) {

        BookModel updateBook = bookRepo.findById(id).orElse(null);
        if(updateBook == null){
            throw new UserException("Book Not Available", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            updateBook.setBookName(updateDetails.getBookName());
            updateBook.setBookAuthor(updateDetails.getBookAuthor());
            updateBook.setBookPicture(updateDetails.getBookPicture());
            updateBook.setPrice(updateBook.getPrice());
            updateBook.setQuantity(updateBook.getQuantity()+updateDetails.getQuantity());

            bookRepo.save(updateBook);
        }
    }

    @Override
    public void deleteBooks(int id) {

        BookModel deletingBook = bookRepo.findById(id).orElse(null);
        if(deletingBook == null){
            throw new UserException("Book Not Available",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            bookRepo.deleteById(id);
        }
    }

    @Override
    public List<BookModel> booksAvailable() {

        return bookRepo.findAll();


    }

}
