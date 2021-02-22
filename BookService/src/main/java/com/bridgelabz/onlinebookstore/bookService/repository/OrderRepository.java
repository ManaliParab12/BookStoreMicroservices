package com.bridgelabz.onlinebookstore.bookService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.bookService.model.Order;

@Repository
public interface OrderRepository  extends MongoRepository<Order, String> {

	List<Order> findByEmail(String email);
}
