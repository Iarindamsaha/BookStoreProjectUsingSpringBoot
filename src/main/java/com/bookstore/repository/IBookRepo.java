package com.bookstore.repository;

import com.bookstore.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepo extends JpaRepository<BookModel,Integer> {

    BookModel findByBookName (String bookName);

    @Query("select b FROM BookModel b ORDER BY b.price DESC")
    List<BookModel> getAllBooksByPriceHighToLow();

    @Query("select b FROM BookModel b ORDER BY b.price ASC")
    List<BookModel> getAllBooksByPriceLowToHigh();

}
