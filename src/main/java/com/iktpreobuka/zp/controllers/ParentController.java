package com.iktpreobuka.zp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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

import com.iktpreobuka.zp.entities.ParentEntity;
import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.ParentRepository;
import com.iktpreobuka.zp.repositories.PupilRepository;
import com.iktpreobuka.zp.repositories.UserRepository;
import com.iktpreobuka.zp.services.ParentService;

@RestController
@RequestMapping(path = "/finalProject/parents")
public class ParentController {
	private static final Logger log = LoggerFactory.getLogger(ParentController.class);
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private PupilRepository pupilRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	@RequestMapping(method = RequestMethod.POST, path = "/newParent/user/{userId}")
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentEntity newParent, BindingResult result,
			 @PathVariable Integer userId){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		
		ParentEntity parent = new ParentEntity();
		log.info("Adding new parent {}",newParent.getFirstName());
		parent.setFirstName(newParent.getFirstName());
		log.info("Adding new parent {}",newParent.getLastName());
		parent.setLastName(newParent.getLastName());
		log.info("Adding new parent {}",newParent.getEmail());
		parent.setEmail(newParent.getEmail());
		log.info("Adding new parent {}",newParent.getUser());
	
		UserEntity user = userRepository.findById(userId).get();
		parent.setUser(user);
		parentRepository.save(parent);
		log.error("An exception occured while posting a new parent!");
		return new ResponseEntity<>(newParent, HttpStatus.CREATED);
		
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable <ParentEntity> getAllParents(){
		log.error("An exception occured while getting all parents!");
		return parentRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> changeParent(@Valid @RequestBody ParentEntity updatedParent,@Valid @PathVariable Integer id,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		ParentEntity parent = parentRepository.findById(id).get();
		if(parent.getFirstName()!= null) {
			log.error("An exception occured while changing parent's firstname!");
			parent.setFirstName(updatedParent.getFirstName());
		}
		if(parent.getLastName()!= null) {
			log.error("An exception occured while changing parent's lastname!");
			parent.setLastName(updatedParent.getLastName());
		}
		if(parent.getEmail()!= null) {
			log.error("An exception occured while changing parent's email!");
			parent.setEmail(updatedParent.getEmail());
		}
		if(parent.getUser()!= null) {
			parent.setUser(updatedParent.getUser());
		}
		parentRepository.save(parent);
		log.error("An exception occured while changing parent with ID!!");
		return new ResponseEntity<>(parent, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer id){
		parentRepository.deleteById(id);
		log.error("An exception occured while deleting parent with that ID!!");
		return new ResponseEntity<>("Parent is deleted", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "by-lastName/{lastName}")
	public Iterable<ParentEntity> findByName(@PathVariable String lastName){
		log.error("An exception occured while getting parent with that lastname!");
		return  parentRepository.findByLastName(lastName);
	
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{firstName}")
	public Iterable<ParentEntity> getByLastName(@PathVariable String firstName){
		log.error("An exception occured while getting parent with that firstname!");
		return parentRepository.findByFirstName(firstName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-firstAndLastName")
	public ParentEntity findByFirstAndLastName(@PathParam(value = "firstName") String firstName, @PathParam(value = "lastName") String lastName) {
		log.error("An exception occured while getting parent with that firstname!");
		return parentRepository.findByFirstNameAndLastName(firstName, lastName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-email/{email}")
	public ParentEntity findByEmail(@PathVariable String email) {
		log.error("An exception occured while getting parent with that email!");
		return parentRepository.findByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/parentUser/{username}")
	public List<ParentEntity> getParentByUsername (@PathVariable String username) {
		log.error("An exception occured while getting parent with that username!");
		return parentService.findParentByUsername(username);
	}
	
	
}
