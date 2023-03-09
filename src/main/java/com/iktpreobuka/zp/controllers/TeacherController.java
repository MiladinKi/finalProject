package com.iktpreobuka.zp.controllers;

import java.util.List;
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
import com.iktpreobuka.zp.entities.SubjectEntity;
import com.iktpreobuka.zp.entities.TeacherEntity;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.SubjectRepository;
import com.iktpreobuka.zp.repositories.TeacherRepository;
import com.iktpreobuka.zp.repositories.UserRepository;

@RestController
@RequestMapping(path = "/finalProject/teachers")
public class TeacherController {
	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST, path = "/newTeacher/user/{userId}")
	public ResponseEntity<?> addNewTeacher(@Valid @RequestBody TeacherEntity newTeacher, BindingResult result,
			@PathVariable Integer userId){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		TeacherEntity teacher = new TeacherEntity();
		log.info("Adding new teacher {}",newTeacher.getFirstName());
		teacher.setFirstName(newTeacher.getFirstName());
		log.info("Adding new teacher {}",newTeacher.getLastName());
		teacher.setLastName(newTeacher.getLastName());
		log.info("Adding new teacher {}",newTeacher.getAge());
		teacher.setAge(newTeacher.getAge());
		log.info("Adding new teacher {}",newTeacher.getUser());
		UserEntity user = userRepository.findById(userId).get();
		teacher.setUser(user);
		teacherRepository.save(teacher);
		log.error("An exception occured while posting a new teacher!");
		return new ResponseEntity<>(newTeacher, HttpStatus.CREATED);
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/teacher/{teacher_id}/subject/{subject_id}")
	public TeacherEntity addTeacherToSubject(@Valid @PathVariable Integer teacher_id, @PathVariable Integer subject_id) {
		TeacherEntity teacher = teacherRepository.findById(teacher_id).get();
		SubjectEntity subject = subjectRepository.findById(subject_id).get();
		teacher.getSubjects().add(subject);
		teacherRepository.save(teacher);
		log.error("An exception occured while posting teacherID with subjectID!");
		return teacher;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable <TeacherEntity> getAllTeachers(){
		log.error("An exception occured while getting all teachers!");
		return teacherRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> changeTeacher(@Valid @RequestBody TeacherEntity updatedTeacher, @PathVariable Integer id,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		TeacherEntity teacher = teacherRepository.findById(id).get();
		if(teacher.getFirstName() != null) {
			log.error("An exception occured while changing teacher's firstname!");
			teacher.setFirstName(updatedTeacher.getFirstName());
		}
		if(teacher.getLastName() != null) {
			log.error("An exception occured while changing teacher's lastname!");
			teacher.setLastName(updatedTeacher.getLastName());
		}
		if(teacher.getAge() != null) {
			log.error("An exception occured while changing teacher's age!");
			teacher.setAge(updatedTeacher.getAge());
		}
		teacherRepository.save(teacher);
		log.error("An exception occured while changing teacher with that ID!");
		return new ResponseEntity<>(teacher, HttpStatus.ACCEPTED);
	}
	

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public TeacherEntity deleteTacher(@PathVariable Integer id){
		TeacherEntity teacher = new TeacherEntity();
		teacher = teacherRepository.findById(id).get();
		teacherRepository.delete(teacher);
		log.error("An exception occured while deleting teacher with that ID!");
		return teacher;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/teacherId/{id}")
	public TeacherEntity getById(@PathVariable Integer id) {
		log.error("An exception occured while geting teacher with that ID!");
		return teacherRepository.findById(id).get();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{firstName}")
	public List<TeacherEntity> getByFirstName(@PathVariable String firstName){
		log.error("An exception occured while getting teacher with that firstname!");
		return teacherRepository.findByFirstName(firstName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/teacherLastName/{lastName}")
	public List<TeacherEntity> getByLastName(@PathVariable String lastName){
		log.error("An exception occured while getting teacher with that lastname!");
		return teacherRepository.findByLastName(lastName);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/by-firstAndLastName")
	public TeacherEntity findByFirstAndLastName(@PathParam(value = "firstName") String firstName, @PathParam(value = "lastName") String lastName) {
		log.error("An exception occured while posting getting teacher with that firstname and lastname!");
		return teacherRepository.findByFirstNameAndLastName(firstName, lastName);
	}
}
