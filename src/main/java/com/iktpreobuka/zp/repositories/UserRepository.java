package com.iktpreobuka.zp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.EUserRole;
import com.iktpreobuka.zp.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	Iterable<UserEntity> findByUserRole(EUserRole userRole);
	public UserEntity findByUsername(String username);

}
