package com.bridgelabz.onlinebookstore.bookService.service;

import com.bridgelabz.onlinebookstore.bookService.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;

public interface IOrderService {
	
	ResponseDTO addOrder(String token, OrderDTO orderDTO);

	ResponseDTO getUserOrders(String email);

	ResponseDTO getAllOrders();
}
