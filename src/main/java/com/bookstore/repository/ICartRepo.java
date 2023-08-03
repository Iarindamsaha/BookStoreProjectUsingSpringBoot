package com.bookstore.repository;

import com.bookstore.models.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepo extends JpaRepository<CartModel,Integer> {

    CartModel findByCartId (int id);

}
