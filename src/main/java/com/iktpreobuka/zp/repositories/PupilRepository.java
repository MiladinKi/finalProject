package com.iktpreobuka.zp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.iktpreobuka.zp.entities.PupilEntity;
import com.iktpreobuka.zp.entities.SubjectEntity;

public interface PupilRepository extends CrudRepository<PupilEntity, Integer> {
	public Iterable<PupilEntity> findByFirstNameAndLastNameAndMidleName(String firstName, String lastName, String midleName);
	public List<PupilEntity> findByGrade(Integer grade);
	public Optional<PupilEntity> findById(Integer id);
	public List<PupilEntity> findByLastName(String lastName);
	public List<PupilEntity> findByFirstName(String firstName);
	
}
