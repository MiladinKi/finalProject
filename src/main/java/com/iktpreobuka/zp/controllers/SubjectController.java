package com.iktpreobuka.zp.controllers;

import java.util.Optional;
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

import com.iktpreobuka.zp.entities.SubjectEntity;
import com.iktpreobuka.zp.repositories.SubjectRepository;

@RestController
@RequestMapping(path = "/finalProject/subjects")
public class SubjectController {
	private static final Logger log = LoggerFactory.getLogger(SubjectController.class);
	@Autowired
	private SubjectRepository subjectRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectEntity newSubject, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		SubjectEntity subject = new SubjectEntity();
		log.info("Adding new subject {}",newSubject.getWeekClassFund());
		subject.setWeekClassFund(newSubject.getWeekClassFund());
		log.info("Adding new subject {}",newSubject.getName());
		subject.setName(newSubject.getName());
		subjectRepository.save(subject);
		log.error("An exception occured while posting a new subject");
		return new ResponseEntity<>(newSubject, HttpStatus.CREATED);
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable <SubjectEntity> getAllSubjects(){
		log.error("An exception occured while getting all subjects");
		return subjectRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> changeSubject(@Valid @RequestBody SubjectEntity updatedSubject, @PathVariable Integer id,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		SubjectEntity subject = subjectRepository.findById(id).get();
		if(subject.getName() != null) {
			log.error("An exception occured while changing subject name");
			subject.setName(updatedSubject.getName());
		}
		if(subject.getWeekClassFund() != null) {
			log.error("An exception occured while changing subject week class fund");
			subject.setWeekClassFund(updatedSubject.getWeekClassFund());
		}
		subjectRepository.save(subject);
		log.error("An exception occured while changing subject");
		return new ResponseEntity<>(subject, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id){
		subjectRepository.deleteById(id);
		log.error("An exception occured while deleting subject!");
		return new ResponseEntity<>("Subject is deleted", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-name/{name}")
	public SubjectEntity findByName(@PathVariable String name) {
		log.error("An exception occured while getting subject by name!");
		return subjectRepository.findByName(name);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{firstLetter}")
	public Iterable<SubjectEntity> findByFirstLetter(@PathVariable String firstLetter){
		log.error("An exception occured while geting subject by first letter");
		return subjectRepository.findByNameStartingWith(firstLetter);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "subjectId/{id}")
	public Optional<SubjectEntity> getById(@PathVariable Integer id) {
		log.error("An exception occured while getting subject by ID!");
		return subjectRepository.findById(id);
	}
	
	

}
