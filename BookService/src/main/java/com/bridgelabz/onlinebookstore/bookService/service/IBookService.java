package com.bridgelabz.onlinebookstore.bookService.service;


import java.util.List;

import com.bridgelabz.onlinebookstore.bookService.dto.BookDTO;
import com.bridgelabz.onlinebookstore.bookService.exception.BookException;
import com.bridgelabz.onlinebookstore.bookService.model.Book;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;



public interface IBookService {

	ResponseDTO addBook(String token, BookDTO bookDTO);

	ResponseDTO getAllBooks();

	ResponseDTO addAllBook(String token);

	ResponseDTO removeBook(String bookId, String token) throws BookException;

	List<Book> getBookByAuthor(String bookAuthor);

//	List<Book> findAll(String keyword);



}