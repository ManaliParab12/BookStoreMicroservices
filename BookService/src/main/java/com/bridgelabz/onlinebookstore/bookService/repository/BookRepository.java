package com.bridgelabz.onlinebookstore.bookService.repository;


import java.util.List;


//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.bookService.model.Book;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
//	
//	@Query(value="SELECT * FROM book b WHERE b.book_name OR b.book_author OR b.book_description LIKE %?1%", nativeQuery = true)
//	public  List<Book> findAll(String keyword);	
	
	List<Book> findByBookAuthor(String bookAuthor);
	
//	JPQL
//	@Query("SELECT b FROM Book b WHERE b.bookName LIKE %?1%" + "OR b.bookAuthor LIKE %?1%" + "OR b.bookDescription LIKE %?1%")
//	public  List<Book> findAll(String keyword);
	
}
