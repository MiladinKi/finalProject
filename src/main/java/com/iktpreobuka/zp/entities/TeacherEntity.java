package com.iktpreobuka.zp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table

public class TeacherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "teacher_id")
	private Integer id;
	@NotNull(message = "First name must be provided")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long")
	@Column
	private String firstName;
	@NotNull(message = "Last name must be provided")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long")
	@Column
	private String lastName;
	@NotNull(message = "Age must be provided")
	@Min(value = 22, message = "Age must be 22 or higher")
	@Column
	private Integer age;
	
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Teacher",joinColumns = 
	{@JoinColumn(name = "teacher_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "subject_id", nullable = false, updatable = false)})
	protected List<SubjectEntity> subjects = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	protected UserEntity user;


	public TeacherEntity() {
		super();
	}


	public TeacherEntity(Integer id, String firstName, String lastName, Integer age, List<SubjectEntity> subjects,
			UserEntity user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.subjects = subjects;
		this.user = user;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public List<SubjectEntity> getSubjects() {
		return subjects;
	}


	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
}