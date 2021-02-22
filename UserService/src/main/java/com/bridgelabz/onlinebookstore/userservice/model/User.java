package com.bridgelabz.onlinebookstore.userservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.onlinebookstore.userservice.dto.UserDTO;

import lombok.Data;

@Document(collection = "user")
public @Data  class User {
	
	@Id	
    private String id;
	
    private String firstName;	
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private boolean isVerify;
    private String type;

    public User() {}
       
	public User(UserDTO userDTO) {
	    this.updateUser(userDTO);
	    }

	public void updateUser(UserDTO userDTO) {
		 this.firstName = userDTO.firstName;
		 this.lastName = userDTO.lastName;
		 this.email = userDTO.email;
		 this.phoneNumber = userDTO.phoneNumber;
		 this.address = userDTO.address;
		 this.password = userDTO.password;
		 this.type = userDTO.type;		
	}

}
