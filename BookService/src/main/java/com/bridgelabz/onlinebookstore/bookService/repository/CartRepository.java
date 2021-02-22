package com.bridgelabz.onlinebookstore.bookService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.bookService.model.Book;
import com.bridgelabz.onlinebookstore.bookService.model.Cart;


@Repository
public interface CartRepository extends  MongoRepository<Cart, String> {

	Optional<Cart> findByBook(Book book);
	
	List<Cart> findAllBooksByUserId(String  userId);

	List<Cart> findByUserId(String userId);
}
