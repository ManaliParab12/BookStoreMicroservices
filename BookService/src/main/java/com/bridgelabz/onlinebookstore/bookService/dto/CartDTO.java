package com.bridgelabz.onlinebookstore.bookService.dto;


import com.bridgelabz.onlinebookstore.bookService.model.Book;

import lombok.Data;

public @Data class CartDTO {
	public int quantity;
	public Book book;
	public String userId;
}
