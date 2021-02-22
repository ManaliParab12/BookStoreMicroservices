package com.bridgelabz.onlinebookstore.userservice.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.onlinebookstore.userservice.dto.UserDTO;
import com.bridgelabz.onlinebookstore.userservice.exception.UserException;
import com.bridgelabz.onlinebookstore.userservice.model.User;
import com.bridgelabz.onlinebookstore.userservice.utility.ResponseDTO;

public interface IUserService {

	ResponseDTO registerUser(UserDTO userDTO) throws UserException;

	ResponseDTO getUserByEmail(String email);

	List<User> getAllUser();

	ResponseDTO updateUser(String email, UserDTO userDTO);

	ResponseDTO deleteUser(String email);

	ResponseDTO userLogin(UserDTO userDTO);

	ResponseDTO verificationMail(User user);

	ResponseDTO verifyUser(String token);

	ResponseDTO forgetPassword(String email);

	ResponseDTO updatePassword(String token, UserDTO userDTO);
	
	public ResponseDTO validateToken(String token);

}
