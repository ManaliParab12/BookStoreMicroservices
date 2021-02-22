package com.bridgelabz.onlinebookstore.bookService.service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.bookService.configuration.RestTemplateConfiguration;
import com.bridgelabz.onlinebookstore.bookService.dto.BookDTO;
import com.bridgelabz.onlinebookstore.bookService.dto.ValidationDTO;
import com.bridgelabz.onlinebookstore.bookService.exception.BookException;
import com.bridgelabz.onlinebookstore.bookService.exception.UserException;
import com.bridgelabz.onlinebookstore.bookService.model.Book;
import com.bridgelabz.onlinebookstore.bookService.repository.BookRepository;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;
import com.bridgelabz.onlinebookstore.bookService.utility.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
@PropertySource("classpath:status.properties")
public class BookService implements IBookService {
	
	@Autowired
	RestTemplateConfiguration restTemplateConfiguration;

	@Autowired
	private Environment environment;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	ObjectMapper objectMapper;


	
	@Override
	public ResponseDTO addBook(String token, BookDTO bookDTO) {
		String id = Token.decodeToken(token);
		ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
		
		System.out.println("validtionDTO " +validationDTO);
		if(validationDTO.isvalid == false) {
			throw new UserException(environment.getProperty("status.login.error.message"));
		}
		Book book = new Book(bookDTO);
		book.setElasticId(UUID.randomUUID().toString());
		if (validationDTO.getType().equalsIgnoreCase("admin")) {
			book = bookRepository.save(book);
			return new ResponseDTO("Book Added Successfully");
		} else {
			return new ResponseDTO("You do not have permission to add book");
		}
	}

	@Override
	public ResponseDTO getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return new ResponseDTO("Book List" + books);
	}

//	public List<Book> findAll(String keyword) {
//		List<Book> books = bookRepository.findAll(keyword);
//		return books;
//	}

	@Override
	public ResponseDTO addAllBook(String token) {
		String id = Token.decodeToken(token);
		System.out.println("Printing token" + id);
		ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
		if (validationDTO.getType().equalsIgnoreCase("admin")) {
			List<Book> bookList = getBookFromCsv();
			bookList.forEach(book -> {
				book.setBookQuantity(5);
				Book books = bookRepository.save(book);
			});
			return new ResponseDTO("Books Added Successfully");
		} else {
			return new ResponseDTO("You do not have permission to add books");
		}
	}


	@Override
	public List<Book> getBookByAuthor(String bookAuthor) {
		List<Book> books = bookRepository.findByBookAuthor(bookAuthor);
		return books;
	}
	
	@Override
	public ResponseDTO removeBook(String bookId, String token) throws BookException {
		String userId = Token.decodeToken(token);
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookException("INVALID_BOOK_ID"));
		bookRepository.deleteById(book.getId());
		return new ResponseDTO("Book Removed");
	}

	private List<Book> getBookFromCsv() {
		System.out.println("Fixed Rate Scheduler" + new Date());
		try (Reader reader = Files.newBufferedReader(Paths.get("./src/main/resources/books_data.csv"));) {
			CsvToBeanBuilder<Book> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(Book.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<Book> csvToBean = csvToBeanBuilder.build();
			Iterator<Book> bookCSVIterator = csvToBean.iterator();
			List<Book> bookList = new ArrayList<>();
			while (bookCSVIterator.hasNext()) {
				Book bookData = bookCSVIterator.next();
				bookList.add(bookData);
			}
			System.out.println(bookList);
			return bookList;
		} catch (Exception e) {
			System.out.println("Exception while reading csv" + e);
		}
		return null;
	}
}
