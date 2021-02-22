package com.bridgelabz.onlinebookstore.bookService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.bookService.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.bookService.service.IOrderService;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;


@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	IOrderService orderService;
	
	@PostMapping("/add")
    public ResponseEntity<ResponseDTO> addOrder( @RequestHeader("Token") String token, @RequestBody OrderDTO orderDTO) {
		ResponseDTO responseDTO = orderService.addOrder( token, orderDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);	
    }	
	
	@GetMapping("/get")
	public ResponseEntity<ResponseDTO> getUserOrders(@RequestHeader("Email") String email) {
		ResponseDTO responseDTO = orderService.getUserOrders(email);
	    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAllOrders() {
		ResponseDTO responseDTO = orderService.getAllOrders();
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
