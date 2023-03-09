package com.iktpreobuka.zp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	public Iterable<ParentEntity> findByLastName(String lastName);
	public Iterable <ParentEntity> findByFirstName(String firstName);
	public ParentEntity findByFirstNameAndLastName(String firstName, String lastName);
	public ParentEntity findByEmail(String email);
}
