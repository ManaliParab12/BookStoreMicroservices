package com.bridgelabz.onlinebookstore.bookService.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.onlinebookstore.bookService.dto.CartDTO;

import lombok.Data;

@Document(collection = "cart")
public @Data class Cart  {


	@Id
	private String id;
	
	private int quantity;
	
	private String userId; 

	private Book book;
	
	public Cart() {}
	
	
	public Cart(CartDTO cartDTO) {
		this.quantity = cartDTO.quantity;
		this.book = cartDTO.book;
		this.userId = cartDTO.userId;
	}
}