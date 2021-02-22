package com.bridgelabz.onlinebookstore.userservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.userservice.dto.UserDTO;
import com.bridgelabz.onlinebookstore.userservice.model.User;
import com.bridgelabz.onlinebookstore.userservice.service.IUserService;
import com.bridgelabz.onlinebookstore.userservice.utility.ResponseDTO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/validate")
	public ResponseEntity<ResponseDTO> validateToken(@RequestHeader("Token") String token) {
		ResponseDTO responseDTO = userService.validateToken(token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/hello")
	public String demoAPI() {
		return "Hello World";
	}
	
	@GetMapping("/email")
	public ResponseEntity<ResponseDTO> getUserByEmail(@RequestHeader("Email") String email) {
		ResponseDTO responseDTO = userService.getUserByEmail(email);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<ResponseDTO> getUser() {
		 List<User> userList = userService.getAllUser();
		 System.out.println("caching");
	     ResponseDTO responseDTO = new ResponseDTO("List of Users", userList);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@ApiOperation("This Api is used for registering new user")
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> registerUser( @Valid @RequestBody UserDTO userDTO) {
		log.debug("User DTO" +userDTO.toString());
		ResponseDTO responseDTO = userService.registerUser(userDTO);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);		
	}

	
	@PutMapping("/login")
	public ResponseEntity<ResponseDTO> loginUser(@RequestBody UserDTO userDTO) {
		ResponseDTO responseDTO = userService.userLogin(userDTO);
		 return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);		
	}

	
	@GetMapping("/verify/{token}")
	public ResponseEntity<ResponseDTO> verifyUser(@RequestHeader ("Token") String token){
        return new ResponseEntity<ResponseDTO>(userService.verifyUser(token), HttpStatus.OK);		
	}

	
	@PutMapping("/update/{email}")
	public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestParam ("email") String email, UserDTO userDTO) {
		 ResponseDTO responseDTO = userService.updateUser(email, userDTO);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	
	@PutMapping("/forget-password")
	public ResponseEntity<ResponseDTO> sendResetPasswordMail(@Valid @RequestParam ("email") String email) {			
	    ResponseDTO responseDTO = userService.forgetPassword(email);
	    return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);	
	} 

	@PostMapping("/reset-password/{token}")
	public ResponseEntity<ResponseDTO> resetPassword(@RequestHeader ("Token") String token, @RequestBody UserDTO userDTO ) {
		return new ResponseEntity<ResponseDTO>(userService.updatePassword(token, userDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{email}")
	public ResponseEntity<ResponseDTO> removeUser(@RequestParam ("email") String email) {
		ResponseDTO responseDTO = userService.deleteUser(email);
	    return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

}
