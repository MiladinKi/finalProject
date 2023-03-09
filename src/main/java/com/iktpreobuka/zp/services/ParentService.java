package com.iktpreobuka.zp.services;

import java.util.List;

import com.iktpreobuka.zp.entities.ParentEntity;
import com.iktpreobuka.zp.entities.PupilEntity;

public interface ParentService {
	public List<ParentEntity> findParentByUsername(String username);
	
}
