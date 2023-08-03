package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.models.BookModel;

import java.util.List;

public interface IAdminServices {

    void addBooks (BookDTO bookDetails);
    void updateBooks (int id, BookDTO updateDetails);
    void deleteBooks (int id);
    List<BookModel> booksAvailable ();
}
