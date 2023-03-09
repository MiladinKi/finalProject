package com.iktpreobuka.zp.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zp.entities.EUserRole;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.UserRepository;

@RestController
@RequestMapping(path = "/finalProject/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserEntity newUser, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		UserEntity user = new UserEntity();
		log.info("Adding new user {}",newUser.getUsername());
		user.setUsername(newUser.getUsername());
		log.info("Adding new user {}",newUser.getPassword());
		user.setPassword(newUser.getPassword());
		log.info("Adding new user {}",newUser.getUserRole());
		user.setUserRole(newUser.getUserRole());
		userRepository.save(user);
		log.error("An exception occured while posting a new user!");
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAllUsers(){
		log.error("An exception occured while getting all users!");
		return userRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> changeUser(@Valid @RequestBody UserEntity updatedUser, @PathVariable Integer id,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		UserEntity user = userRepository.findById(id).get();
		if(user.getUsername() != null) {
			log.error("An exception occured while changing user username with!");
			user.setUsername(updatedUser.getUsername());
		}
		if(user.getPassword() != null) {
			log.error("An exception occured while changing user password!");
			user.setPassword(updatedUser.getPassword());
		}
		if(user.getUserRole() != null) {
			log.error("An exception occured while changing user userrole!");
			user.setUserRole(updatedUser.getUserRole());
		}
		userRepository.save(user);
		log.error("An exception occured while changing user with that ID!");
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
		userRepository.deleteById(id);
		log.error("An exception occured while deleting user with that ID!");
		return new ResponseEntity<>("User is deleted", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{userRole}")
	public Iterable<UserEntity> getRole(@PathVariable EUserRole userRole){
		log.error("An exception occured while getting user with that userrole!");
		return userRepository.findByUserRole(userRole);
	}
	

	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public UserEntity getByUsername(@PathVariable String username) {
		log.error("An exception occured while getting user with that username!");
		return userRepository.findByUsername(username);
	}
}
