package com.iktpreobuka.zp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.MarkEntity;
import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.SubjectEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {

	public List <MarkEntity> findAllBySubjectAndPupil(SubjectEntity subject, PupilEntity pupil);
	public List<MarkEntity> findAllBySubject(SubjectEntity subject);
	public List<MarkEntity> findAllBySubjectIn(List<SubjectEntity> subjects);
	public List<MarkEntity> findAllByPupil(PupilEntity pupil);
	}
