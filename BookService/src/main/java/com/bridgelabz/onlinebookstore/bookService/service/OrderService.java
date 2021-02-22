package com.bridgelabz.onlinebookstore.bookService.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.bookService.configuration.RestTemplateConfiguration;
import com.bridgelabz.onlinebookstore.bookService.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.bookService.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.bookService.dto.ValidationDTO;
import com.bridgelabz.onlinebookstore.bookService.exception.UserException;
import com.bridgelabz.onlinebookstore.bookService.model.Order;
import com.bridgelabz.onlinebookstore.bookService.repository.OrderRepository;
import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;
import com.bridgelabz.onlinebookstore.bookService.utility.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@Service
@PropertySource("classpath:status.properties")
public class OrderService implements IOrderService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	OrderRepository orderRepository;
	
//    @Autowired
//    UserRepository userRepository;
    
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
	 @Autowired
	 private Binding bind;
	 
	 @Autowired
	 private Gson gson;
	 
		@Autowired
		private ObjectMapper objectMapper;
		
		@Autowired
		private RestTemplateConfiguration restTemplateConfiguration;
	 

	@Override
	public ResponseDTO addOrder(String token, OrderDTO orderDTO) {
		String userId = Token.decodeToken(token);
		ValidationDTO validationDTO = objectMapper.convertValue(restTemplateConfiguration.validateToken(token).getData(), ValidationDTO.class);
			if(validationDTO.isIsvalid()== false)
		throw new UserException(environment.getProperty("status.login.error.message"));
			orderDTO.setDate(LocalDate.now());
			ModelMapper modelMapper = new ModelMapper();
			Order order = modelMapper.map(orderDTO, Order.class);
			orderRepository.save(order);
			rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
					gson.toJson(new EmailDTO(order.getEmail(), "Order Confirmed",
							"Hello " + order.getEmail() + " " + "\n Your Order has been placed successfully!"
									 + "\nOrder Details :" + "\nOrder date : " + order.getDate() + "\nOrder Id : #12345"
									 + "\nThank You")));
			return new ResponseDTO("Your Order has been placed successfully!");
	}

	@Override
	public ResponseDTO  getUserOrders(String email) {
		List<Order> order = orderRepository.findByEmail(email);	    						
		return new ResponseDTO("Your Orders" +order);
	}
	@Override
    public ResponseDTO  getAllOrders() {
		List<Order> order = orderRepository.findAll();
		return new ResponseDTO("Your Orders" +order);
    }
}
