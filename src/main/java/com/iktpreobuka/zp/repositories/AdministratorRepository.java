package com.iktpreobuka.zp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.AdministratorEntity;

public interface AdministratorRepository extends CrudRepository<AdministratorEntity, Integer> {
	public Iterable<AdministratorEntity> findByFirstName (String firstName);
	public Iterable<AdministratorEntity> findByLastname (String lastname);
	public Iterable<AdministratorEntity> findByFirstNameAndLastname (String firstName, String lastname);
	public AdministratorEntity findByEmail (String email);
}
