package com.bookstore.repository;

import com.bookstore.models.BookModel;
import com.bookstore.models.CartBookModel;
import com.bookstore.models.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartBookRepo  extends JpaRepository<CartBookModel,Integer> {

    Optional<CartBookModel> findByCartModelAndBookModel (CartModel cartModel, Optional<BookModel> bookModel);

    List<CartBookModel> findAllByCartModel (CartModel cartModel);

    void deleteAllByCartModel (CartModel cartModel);

}
