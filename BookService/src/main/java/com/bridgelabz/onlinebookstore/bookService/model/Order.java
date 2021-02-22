package com.bridgelabz.onlinebookstore.bookService.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.onlinebookstore.bookService.dto.OrderDTO;

import lombok.Data;

@Document(collection = "order")
public @Data class Order {
	
	@Id
	private String id;
	
	private int quantity;
	
	private int  totalPrice;
	
	private LocalDate date;
	
	private String userId;
	
	private String address;
	
	private String phoneNumber;
	
	private String email;
	
	
	
	private Book book;

	public Order() {}
	
	public Order(OrderDTO orderDTO) {
		this.quantity = orderDTO.quantity;
		this.totalPrice = orderDTO.totalPrice;
		this.date = orderDTO.date;
		this.userId = orderDTO.userId;
		this.address = orderDTO.address;
		this.phoneNumber = orderDTO.phoneNumber;
		this.email = orderDTO.email;
		this.book = orderDTO.book;
	}

	
}
