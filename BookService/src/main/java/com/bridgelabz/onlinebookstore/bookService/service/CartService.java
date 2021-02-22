package com.bridgelabz.onlinebookstore.bookService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.bookService.configuration.RestTemplateConfiguration;
import com.bridgelabz.onlinebookstore.bookService.dto.CartDTO;
import com.bridgelabz.onlinebookstore.bookService.dto.ValidationDTO;
import com.bridgelabz.onlinebookstore.bookService.exception.CartException;
import com.bridgelabz.onlinebookstore.bookService.exception.UserException;
import com.bridgelabz.onlinebookstore.bookService.model.Book;
import com.bridgelabz.onlinebookstore.bookService.model.Cart;
import com.bridgelabz.onlinebookstore.bookService.repository.BookRepository;
import com.bridgelabz.onlinebookstore.bookService.repository.CartRepository;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;
import com.bridgelabz.onlinebookstore.bookService.utility.Token;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@PropertySource("classpath:status.properties")
public class CartService implements ICartService {

	@Autowired
	private Environment environment;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RestTemplateConfiguration restTemplateConfiguration;

	@Override
	public ResponseDTO addBookToCart(CartDTO cartDTO) throws CartException {
		Cart cart = new Cart(cartDTO);
		if (cartRepository.findByBook(cartDTO.getBook()).isPresent())
			throw new CartException("Book Already Present in cart");
		cartRepository.save(cart);
		return new ResponseDTO("Book Added to cart");
	}
	
	@Override
	public ResponseDTO updateCart(String token,String bookId, int quantity) throws CartException {
		String userId = Token.decodeToken(token);
		System.out.println("token " +token);
		ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
		System.out.println("validationDTO " +validationDTO );
		if(validationDTO.isIsvalid()== false)
		throw new UserException(environment.getProperty("status.login.error.message"));
		List<Cart> carts = cartRepository.findByUserId(userId);
		Cart cart = carts.stream().filter(userCart -> userCart.getBook().getId().equals(bookId)).findFirst()
								  .orElseThrow(() -> new CartException("INVALID_BOOK_ID"));
		cart.setQuantity(quantity);
		cartRepository.save(cart);
		return new ResponseDTO("Book Quantity updated");
	}

	@Override
	public ResponseDTO getListOfBooksInCart(String token) {
		String userId = Token.decodeToken(token);
	ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
	if(validationDTO.isIsvalid()== false)
		throw new UserException(environment.getProperty("status.login.error.message"));
	List<Cart> cart = cartRepository.findAllBooksByUserId(userId);
		return new ResponseDTO("Books in cart", cart);
	}
	
	@Override
	public ResponseDTO removeBookFromCart(String bookId, String token) throws CartException {
		String userId = Token.decodeToken(token);
	System.out.println("token " +token);
	ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
	System.out.println("validationDTO " +validationDTO );
	if(validationDTO.isIsvalid()== false)
	throw new UserException(environment.getProperty("status.login.error.message"));
	List<Cart> carts = cartRepository.findByUserId(userId);
	Cart cart = carts.stream().filter(userCart -> userCart.getBook().getId().equals(bookId)).findFirst()
							  .orElseThrow(() -> new CartException("INVALID_BOOK_ID"));
		cartRepository.deleteById(cart.getId());
		return new ResponseDTO("Book Removed from cart");
	}
}