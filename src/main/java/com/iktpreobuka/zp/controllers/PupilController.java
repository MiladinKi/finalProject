package com.iktpreobuka.zp.controllers;


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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zp.entities.MarkEntity;
import com.iktpreobuka.zp.entities.ParentEntity;
import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.SubjectEntity;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.MarkRepository;
import com.iktpreobuka.zp.repositories.ParentRepository;
import com.iktpreobuka.zp.repositories.PupilRepository;
import com.iktpreobuka.zp.repositories.SubjectRepository;
import com.iktpreobuka.zp.repositories.UserRepository;
import com.iktpreobuka.zp.services.PupilService;

@RestController
@RequestMapping(path = "/finalProject/pupils")
@CrossOrigin(origins = "http://localhost:3000")
public class PupilController {
	private static final Logger log = LoggerFactory.getLogger(PupilController.class);
	
	@Autowired
	private PupilRepository pupilRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private PupilService pupilService;
	
	@Autowired
	private MarkRepository markRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	@RequestMapping(method = RequestMethod.POST, path = "newPupil/parent/{parentId}/user/{userId}")
	public ResponseEntity<?> addNewPupil(@Valid @RequestBody PupilEntity newPupil, BindingResult result,
			@PathVariable Integer parentId, @PathVariable Integer userId) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		PupilEntity pupil = new PupilEntity();
		log.info("Adding new pupil {}",newPupil.getFirstName());
		pupil.setFirstName(newPupil.getFirstName());
		log.info("Adding new pupil {}",newPupil.getLastName());
		pupil.setLastName(newPupil.getLastName());
		log.info("Adding new pupil {}",newPupil.getMidleName());
		pupil.setMidleName(newPupil.getMidleName());
		log.info("Adding new pupil {}",newPupil.getDateOfBirth());
		pupil.setDateOfBirth(newPupil.getDateOfBirth());
		log.info("Adding new pupil {}",newPupil.getGrade());
		pupil.setGrade(newPupil.getGrade());
		log.info("Adding new pupil {}",newPupil.getSemester());
		pupil.setSemester(newPupil.getSemester());

		ParentEntity parent = parentRepository.findById(parentId).get();
		pupil.setParent(parent);
		UserEntity user = userRepository.findById(userId).get();
		pupil.setUser(user);

		pupilRepository.save(pupil);
		log.error("An exception occured while posting a new pupil!");
		return new ResponseEntity<>(newPupil, HttpStatus.CREATED);
		
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	
	@RequestMapping (method = RequestMethod.POST, path = "/pupil/{pupil_id}/subject/{subject_id}")
	public PupilEntity addSubjectToPupil(@Valid @PathVariable Integer pupil_id, @PathVariable Integer subject_id) {
	PupilEntity pupil = pupilRepository.findById(pupil_id).get();
	SubjectEntity subject = subjectRepository.findById(subject_id).get();
	pupil.getSubjects().add(subject);
	pupilRepository.save(pupil);
	log.error("An exception occured while posting a pupil ID with subject ID!");
	return pupil;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<PupilEntity> getAllPupils() {
		log.error("An exception occured while getting all pupils");
		return pupilRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> changePupil(@Valid @RequestBody PupilEntity updatedPupil, @PathVariable Integer id,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		PupilEntity pupil = pupilRepository.findById(id).get();
		if (pupil.getFirstName() != null) {
			log.error("An exception occured while changing pupil firstname!");
			pupil.setFirstName(updatedPupil.getFirstName());
		}
		if (pupil.getLastName() != null) {
			log.error("An exception occured while changing pupil lastname!");
			pupil.setLastName(updatedPupil.getLastName());
		}
		if (pupil.getMidleName() != null) {
			log.error("An exception occured while changing pupil midlename!");
			pupil.setMidleName(updatedPupil.getMidleName());
		}
		if (pupil.getDateOfBirth() != null) {
			log.error("An exception occured while changing pupil date of birth!");
			pupil.setDateOfBirth(updatedPupil.getDateOfBirth());
		}
		if (pupil.getGrade() != null) {
			log.error("An exception occured while changing pupil grade!");
			pupil.setGrade(updatedPupil.getGrade());
		}
		if (pupil.getSemester() != null) {
			log.error("An exception occured while changing pupil semester!");
			pupil.setSemester(updatedPupil.getSemester());
		}

		pupilRepository.save(pupil);
		log.error("An exception occured while changing pupil");
		return new ResponseEntity<>(pupil, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deletePupil(@PathVariable Integer id){
		pupilRepository.deleteById(id);
		log.error("An exception occured while deleting pupil!");
		return new ResponseEntity<>("Pupil is deleted", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id){
		for (PupilEntity pupil : pupilRepository.findAll()) {
			if(pupil.getId().equals(id)) {
				log.error("An exception occured while getting pupil by ID!");
				return new ResponseEntity<>(pupil, HttpStatus.OK);
			}
		}
		log.error("An exception occured because pupil with that ID don't exist!");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-FLMname")
	public Iterable<PupilEntity> findByFLMName(@PathParam(value = "firstName") String firstName, 
			@PathParam(value = "lastName") String lastName, @PathParam(value = "midleName") String midleName){
		log.error("An exception occured while getting pupil with that firstname, lastname and midlename!");
		return pupilRepository.findByFirstNameAndLastNameAndMidleName(firstName, lastName, midleName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-grade/{grade}")
	public ResponseEntity<?> findByGrade(@PathVariable Integer grade, @RequestBody PupilEntity pupil){
			List<PupilEntity> pupils = pupilRepository.findByGrade(grade);
		if(!pupils.isEmpty()) {
			log.error("An exception occured while geting pupils by grade!");
				return new ResponseEntity<>(pupils, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "by-lastname/{lastName}")
	public List<PupilEntity> getByLastName(@PathVariable String lastName){
		log.error("An exception occured while getting pupil with that lastname!");
		return pupilRepository.findByLastName(lastName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "by-firstname/{firstName}")
	public List<PupilEntity> getByFirstName(@PathVariable String firstName){
		log.error("An exception occured while getting pupil with that firstname!");
		return pupilRepository.findByFirstName(firstName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/pupilUser/{username}")
	public List<PupilEntity> getPupilByUsername(@PathVariable String username){
		log.error("An exception occured while getting pupil with that username!");
		return pupilService.findPupilByUsername(username);
	}
		
	
}
