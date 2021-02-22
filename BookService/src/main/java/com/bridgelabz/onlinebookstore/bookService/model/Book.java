package com.bridgelabz.onlinebookstore.bookService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.onlinebookstore.bookService.dto.BookDTO;
import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Document(collection = "book")
public @Data  class Book {
	
	@Id
	private String id;
	
	@CsvBindByName(column = "title")
	private String bookName;
	
	@CsvBindByName(column = "author")
	private String bookAuthor;
	
	@CsvBindByName(column = "image")
	private String bookImage;
	
	private int bookQuantity;
	
	@CsvBindByName(column = "price")
	private double bookPrice;
	
	@CsvBindByName(column = "description")
	private String bookDescription;
	
	private String elasticId;
	

	public Book() { }
	
	public Book(BookDTO bookDTO) {
		this.bookName = bookDTO.bookName;
		this.bookAuthor = bookDTO.bookAuthor;
		this.bookImage = bookDTO.bookImage;
		this.bookQuantity = bookDTO.bookQuantity;
		this.bookPrice = bookDTO.bookPrice;
		this.bookDescription = bookDTO.bookDescription;
	}	
	
	public Book(String id, String bookName, String bookAuthor, String bookImage, int bookQuantity, double bookPrice, String bookDescription) {
		this.id = id;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookImage = bookImage;
		this.bookQuantity = bookQuantity;
		this.bookPrice = bookPrice;
		this.bookDescription = bookDescription;
	}
}
