package com.iktpreobuka.zp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.UserEntity;
import com.iktpreobuka.zp.repositories.PupilRepository;
import com.iktpreobuka.zp.repositories.UserRepository;

@Service
public class PupilServiceImpl implements PupilService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PupilRepository pupilRepository;

	@Override
	public List<PupilEntity> findPupilByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username);
		return user.getPupils();
	}
	
}
