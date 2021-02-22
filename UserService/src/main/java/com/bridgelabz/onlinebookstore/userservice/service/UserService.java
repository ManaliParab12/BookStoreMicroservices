package com.bridgelabz.onlinebookstore.userservice.service;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.userservice.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.userservice.dto.UserDTO;
import com.bridgelabz.onlinebookstore.userservice.dto.ValidationDTO;
import com.bridgelabz.onlinebookstore.userservice.exception.UserException;
import com.bridgelabz.onlinebookstore.userservice.model.User;
import com.bridgelabz.onlinebookstore.userservice.repository.UserRepository;
import com.bridgelabz.onlinebookstore.userservice.utility.ResponseDTO;
import com.bridgelabz.onlinebookstore.userservice.utility.Token;
import com.google.gson.Gson;

@PropertySource("classpath:status.properties")
@Service
public class UserService implements IUserService {

	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Binding bind;

	@Autowired
	private Gson gson;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public ResponseDTO validateToken(String token) {
		String id = Token.decodeToken(token);
		User user = userRepository.findById(id).get();
		return new ResponseDTO("User is Present", new ValidationDTO(id, user.getType(), user!=null));
	}

	public ResponseDTO registerUser(UserDTO userDTO) throws UserException {
		String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		User user = new User(userDTO);
		if (userRepository.findByEmail(user.getEmail()).isPresent())
			throw new UserException("User is Already Registered with this Email Id");
		user.setPassword(password);
		userRepository.save(user);
		verificationMail(user);
		return new ResponseDTO("Registration successful, verification link has been sent to your email id: ",
				user.getEmail());
	}

	public ResponseDTO verificationMail(User user) {
		String token = Token.generateToken(user.getId());
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
				gson.toJson(new EmailDTO(user.getEmail(), "verification Link ", getVerificationURL(token))));
		return new ResponseDTO("verification link has been sent to your registered email id : " + user.getEmail());
	}

	public ResponseDTO verifyUser(String token) {
		String userId = Token.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.setVerify(true);
		userRepository.save(user);
		return ResponseDTO.getResponse("Verified Successfully", user);
	}

	public ResponseDTO userLogin(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		if (bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()) && user.isVerify())
			return new ResponseDTO(environment.getProperty("status.login.message"));
		throw new UserException(("Identity Verification, Action Required!"));
	}

	public ResponseDTO getUserByEmail(String email) {
		userRepository.findByEmail(email)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		return new ResponseDTO("User Details");
	}

	public List<User> getAllUser() {
		String message = environment.getProperty("dev.message");
		System.out.println("Getting user data from " + message);
		return userRepository.findAll();
	}

	public ResponseDTO updateUser(String email, UserDTO userDTO) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.updateUser(userDTO);
		userRepository.save(user);
		return ResponseDTO.getResponse("Record updated successfully", user);
	}

	public ResponseDTO deleteUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		userRepository.delete(user);
		return ResponseDTO.getResponse("Record has been successfully deleted", user);
	}

	private String getVerificationURL(String token) {
		System.out.println("token " + token);
		return "Click on below link \n" + "http://localhost:8081/swagger-ui.html#!/user-controller/verifyUserUsingGET"
				+ "\n token : " + token;
	}

	public ResponseDTO forgetPassword(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		String token = Token.generateToken(user.getId());
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
				gson.toJson(new EmailDTO(user.getEmail(), "Reset Password Link ", resetURL(token))));
		return new ResponseDTO("Reset Password link has been sent to your registered email id : " + user.getEmail());
	}

	private String resetURL(String token) {
		return "Click on below link to Reset your Password \n"
				+ "http://localhost:8081/swagger-ui.html#!/user-controller/resetPasswordUsingPOST" + "\n token : "
				+ token;
	}

	public ResponseDTO updatePassword(String token, UserDTO userDTO) {
		String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		String userId = Token.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.setPassword(password);
		userRepository.save(user);
		return ResponseDTO.getResponse("Your password has been changed successfully!", user);
	}

}
