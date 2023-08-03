package com.bookstore.repository;

import com.bookstore.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderModel extends JpaRepository<OrderModel,Integer> {

}
