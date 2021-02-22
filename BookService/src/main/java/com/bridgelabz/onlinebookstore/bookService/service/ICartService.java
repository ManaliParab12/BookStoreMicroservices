package com.bridgelabz.onlinebookstore.bookService.service;


import java.util.List;

import com.bridgelabz.onlinebookstore.bookService.dto.CartDTO;
import com.bridgelabz.onlinebookstore.bookService.exception.CartException;
import com.bridgelabz.onlinebookstore.bookService.model.Cart;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;



public interface ICartService {

	ResponseDTO addBookToCart(CartDTO cartDTO) throws CartException;

	ResponseDTO  updateCart(String token, String bookId, int quantity) throws CartException;

	ResponseDTO  removeBookFromCart(String bookId, String token) throws CartException;

	ResponseDTO getListOfBooksInCart(String token);
	
	
}
