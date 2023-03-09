package com.iktpreobuka.zp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {
	public SubjectEntity findByName(String name);
	public Iterable<SubjectEntity> findByNameStartingWith (String firstLetter);
	public Optional<SubjectEntity> findById(Integer id);
	
}
