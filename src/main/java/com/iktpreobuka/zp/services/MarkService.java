package com.iktpreobuka.zp.services;

import java.util.List;

import com.iktpreobuka.zp.entities.MarkEntity;

public interface MarkService {
	public List<MarkEntity> findAllMarksByPupilLastName(String lastName);
	public List<MarkEntity> findMarkByPupil(Integer pupilId);
	public List<MarkEntity> findMarkByPupilFromSubject(Integer pupilId, Integer subjectId);
}
