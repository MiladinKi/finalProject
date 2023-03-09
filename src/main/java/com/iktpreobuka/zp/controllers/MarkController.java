package com.iktpreobuka.zp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.iktpreobuka.zp.entities.MarkEntity;
import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.SubjectEntity;
import com.iktpreobuka.zp.repositories.MarkRepository;
import com.iktpreobuka.zp.repositories.PupilRepository;
import com.iktpreobuka.zp.repositories.SubjectRepository;
import com.iktpreobuka.zp.services.MarkService;

@RestController
@RequestMapping(path = "/finalProject/marks")
public class MarkController {
	private static final Logger log = LoggerFactory.getLogger(MarkController.class);

	@Autowired
	public MarkRepository markRepository;
	
	@Autowired
	private MarkService markService;

	@Autowired
	public PupilRepository pupilRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/newMark/{pupilId}/{subjectId}")
	public ResponseEntity<?> addNewMark(@Valid @RequestBody MarkEntity newMark, BindingResult result, 
			@PathVariable Integer pupilId, @PathVariable Integer subjectId) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		MarkEntity mark = new MarkEntity();
		log.info("Adding new mark {}",newMark.getMark());
		mark.setMark(newMark.getMark());
		if(mark.getMark()!= null || mark.getMark()<1 || mark.getMark()>5) {
			log.error("An exception occured while posting a new mark beacuse mark is not in the range!");
			return new ResponseEntity<>("Mark is in the range from 1 to 5", HttpStatus.BAD_REQUEST);
		}
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		mark.setSubject(subject);
		mark.setPupil(pupil);
		markRepository.save(mark);
		log.error("An exception occured while posting a new mark!");
		return new ResponseEntity<>(newMark, HttpStatus.CREATED);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<MarkEntity> getAllMarks() {
		log.error("An exception occured while getting all marks!");
		return markRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public MarkEntity changeMark(@Valid @PathVariable Integer id, @RequestBody MarkEntity updateMark) {
		MarkEntity mark = markRepository.findById(id).get();

	if (mark.getMark() != null) {
		log.error("An exception occured while changing mark beacuse mark is not null!");
			mark.setMark(updateMark.getMark());
		}
		log.error("An exception occured while changing mark!");
		return markRepository.save(mark);

	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteMark(@PathVariable Integer id) {
		markRepository.deleteById(id);
		log.error("An exception occured while deleting mark!");
		return new ResponseEntity<>("Mark is delete", HttpStatus.OK);
	}
	

	@RequestMapping(method = RequestMethod.GET, path = "/marksPupil/{lastName}")
	public List<MarkEntity> getMarksByPupil(@PathVariable String lastName){
		log.error("An exception occured while finding marks by pupil lastname!");
		List<MarkEntity> marks =  markService.findAllMarksByPupilLastName(lastName);
		for (MarkEntity mark : marks) {
			log.error("An exception occured while listing mark by pupil lastname!");
			mark.getPupil().setSubjects(null);
		}
		return marks;
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/pupil/{pupilId}/subject/{subjectId}")
	public List<MarkEntity> findMarksByPupilFromSubject(@PathVariable Integer pupilId, @PathVariable Integer subjectId){
		List<MarkEntity> marks = markService.findMarkByPupilFromSubject(pupilId, subjectId);
		for (MarkEntity mark : marks) {
			log.error("An exception occured while listing marks by pupil and subject id!");
			mark.getPupil().setSubjects(null);
		}
		return marks;
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/marksPupilId/{pupilId}")
	public List<MarkEntity> getMarksByPupil(@PathVariable Integer pupilId){
		List<MarkEntity> marks = markService.findMarkByPupil(pupilId);
		for (MarkEntity mark : marks) {
			log.error("An exception occured while listing marks by pupil id!");
			mark.getPupil().setSubjects(null);
		}
		return marks;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/avgMark/subject/{subjectId}/pupil/{pupilId}")
	public double averageMarkForSubject(@PathVariable Integer subjectId, @PathVariable Integer pupilId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		List<MarkEntity> marks = markRepository.findAllBySubjectAndPupil(subject, pupil);
		long sum = 0;
		for (MarkEntity mark : marks) {
			sum += mark.getMark();
		}
		return (double) sum/marks.size();
		}
	
	@RequestMapping(method = RequestMethod.GET, path = "/avgMark/pupil/{pupilId}")
	public double averageMarkForPupil( @PathVariable Integer pupilId) {
		
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		List<MarkEntity> marks = markRepository.findAllByPupil(pupil);
		long sum = 0;
		for (MarkEntity mark : marks) {
			sum += mark.getMark();
		}
		return (double) sum/marks.size();
		}
	
	@RequestMapping(method = RequestMethod.GET, path = "/avgMarkPerSubject/pupil/{pupilId}")
	public Map<String, Double> averageMarkPerSubjectForPupil( @PathVariable Integer pupilId) {
		
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		List<MarkEntity> marks = markRepository.findAllByPupil(pupil);
		Map<String, Integer> sumPerSubject = new HashMap<>();
		Map<String, Integer> counterPerSubject = new HashMap<>();
		for (MarkEntity mark : marks) {
			String subjectName = mark.getSubject().getName();
			if(sumPerSubject.containsKey(subjectName)) {
				Integer sum = sumPerSubject.get(subjectName)+mark.getMark();
				sumPerSubject.put(subjectName, sum);
			} else {
				sumPerSubject.put(subjectName, mark.getMark());
			}
			if(counterPerSubject.containsKey(subjectName)) {
				Integer sum = counterPerSubject.get(subjectName) + 1;
				counterPerSubject.put(subjectName, sum);
			} else {
				counterPerSubject.put(subjectName, 1);
			}
		}
		Map<String, Double> avgPerSubject = new HashMap<>(sumPerSubject.size());
		for (Map.Entry<String, Integer> sumEntry : sumPerSubject.entrySet()) {
			String subjectName =sumEntry.getKey();
			Integer counter = counterPerSubject.get(subjectName);
			avgPerSubject.put(subjectName, ((double) sumEntry.getValue()/counter));
		}
		
		return avgPerSubject;
	}
}
