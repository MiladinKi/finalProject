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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zp.entities.AdministratorEntity;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.AdministratorRepository;
import com.iktpreobuka.zp.repositories.UserRepository;

@RestController
@RequestMapping("/finalProject/administrators")
public class AdministratorController {
	private static final Logger log = LoggerFactory.getLogger(AdministratorController.class);
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newAdmin/user/{userId}")
	public ResponseEntity<?> addNewAdmin(@Valid @RequestBody AdministratorEntity addAdmin, BindingResult result,
			@PathVariable Integer userId){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		AdministratorEntity admin = new AdministratorEntity();
		log.info("Adding new administrator{}", addAdmin.getFirstName());
		admin.setFirstName(addAdmin.getFirstName());
		log.info("Adding new administrator{}", addAdmin.getLastname());
		admin.setLastname(addAdmin.getLastname());
		log.info("Adding new administrator{}", addAdmin.getEmail());
		admin.setEmail(addAdmin.getEmail());
		log.info("Adding new administrator{}", addAdmin.getUser());
		UserEntity user = userRepository.findById(userId).get();
		admin.setUser(user);
		administratorRepository.save(admin);
		log.error("An exception occured while posting a new administrator");
		return new ResponseEntity<>(addAdmin, HttpStatus.CREATED);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<AdministratorEntity> getAllAdmins(){
		log.error("An exception occured while getting all administrators!");
		return administratorRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path ="/{id}")
	public ResponseEntity<?> changeAdmin(@Valid @PathVariable Integer id, @RequestBody AdministratorEntity updatedAdmin,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		AdministratorEntity admin = administratorRepository.findById(id).get();
		if(admin.getFirstName() != null) {
			log.error("An exception occured while changing administrator's firstname!");
			admin.setFirstName(updatedAdmin.getFirstName());
		}
		if(admin.getLastname() != null) {
			log.error("An exception occured while changing administrator's lastname!");
			admin.setLastname(updatedAdmin.getLastname());
		}
		if(admin.getEmail() != null) {
			log.error("An exception occured while changing administrator's email!");
			admin.setEmail(updatedAdmin.getEmail());
		}
		administratorRepository.save(admin);
		log.error("An exception occured while changing administrator by ID!");
		return new ResponseEntity<>(admin, HttpStatus.ACCEPTED);
	}
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
		administratorRepository.deleteById(id);
		log.error("An exception occured while deleting administrator!");
		return new ResponseEntity<>("Admin is deleted", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public AdministratorEntity getById(@PathVariable Integer id) {
		log.error("An exception occured while getting administrator by ID!");
		return administratorRepository.findById(id).get();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/firstName")
	public Iterable<AdministratorEntity> getByFirstname(@RequestParam String firstName){
		log.error("An exception occured while getting administrator by firstname!");
		return administratorRepository.findByFirstName(firstName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/lastName")
	public Iterable<AdministratorEntity> getByLastname(@RequestParam String lastname){
		log.error("An exception occured while getting administrator by lastname!");
		return administratorRepository.findByLastname(lastname);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/firstNameAndlastName")
	public Iterable<AdministratorEntity> getByFirstNameAndLastname(@RequestParam String firstName, @RequestParam String lastname){
		log.error("An exception occured while gettingw administrator by first and last name!");
		return administratorRepository.findByFirstNameAndLastname(firstName, lastname);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-email/{email}")
	public AdministratorEntity getByEmail(@PathVariable String email) {
		log.error("An exception occured while getting administrator by email!");
		return administratorRepository.findByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-ID/{adminId}")
	public AdministratorEntity findById(@PathVariable Integer adminId) {
		log.error("An exception occured while getting administrator by ID!");
		return administratorRepository.findById(adminId).get();
	}

}
