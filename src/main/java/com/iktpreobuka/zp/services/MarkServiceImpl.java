package com.iktpreobuka.zp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zp.entities.MarkEntity;
import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.SubjectEntity;
import com.iktpreobuka.zp.repositories.MarkRepository;
import com.iktpreobuka.zp.repositories.PupilRepository;
import com.iktpreobuka.zp.repositories.SubjectRepository;

@Service
public class MarkServiceImpl implements MarkService {
	
	@Autowired
	private PupilRepository pupilRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private MarkRepository markRepository;

	@Override
	public List<MarkEntity> findAllMarksByPupilLastName(String lastName) {
		List<PupilEntity> pupils = pupilRepository.findByLastName(lastName);
		List <MarkEntity> marks = new ArrayList<>();
		for (PupilEntity pupil : pupils) {
			marks.addAll(pupil.getMarks());
		}
		return marks;
	}

	@Override
	public List<MarkEntity> findMarkByPupil(Integer pupilId) {
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		return  pupil.getMarks();
	}

	@Override
	public List<MarkEntity> findMarkByPupilFromSubject(Integer pupilId, Integer subjectId) {
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		
		return markRepository.findAllBySubjectAndPupil(subject, pupil);
	}
}
