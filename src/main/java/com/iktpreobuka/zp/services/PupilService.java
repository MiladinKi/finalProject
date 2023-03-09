package com.iktpreobuka.zp.services;

import java.util.List;

import com.iktpreobuka.zp.entities.PupilEntity;


public interface PupilService {
	public List<PupilEntity> findPupilByUsername(String username);
}
