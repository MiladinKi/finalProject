package com.iktpreobuka.zp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zp.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
	public TeacherEntity findByFirstNameAndLastName(String firstName, String lastName);
	public List<TeacherEntity> findByFirstName(String firstName);
	public List<TeacherEntity> findByLastName(String lastName);
	public Optional<TeacherEntity> findById(Integer id);

}
