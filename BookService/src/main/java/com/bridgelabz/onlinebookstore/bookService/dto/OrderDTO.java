package com.bridgelabz.onlinebookstore.bookService.dto;

import java.time.LocalDate;

import com.bridgelabz.onlinebookstore.bookService.model.Book;

import lombok.Data;

public @Data class OrderDTO {
	public int quantity;
	
	public int  totalPrice;	
	
	public LocalDate date;	
	
	public String userId;
	
	public String address;
	
	public String phoneNumber;
	
	public String email;
	
	public Book book;

}
