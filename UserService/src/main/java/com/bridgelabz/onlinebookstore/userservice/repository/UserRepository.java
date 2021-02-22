package com.bridgelabz.onlinebookstore.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.userservice.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>  {
	Optional<User> findByEmail(String email); 

}
