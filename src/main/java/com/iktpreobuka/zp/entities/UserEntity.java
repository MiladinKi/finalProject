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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Username must be provided")
	@Size(min = 2, max = 15, message = "Username must be between {min} and {max} characters long")
	@Column
	private String username;
	@NotNull(message = "Password must be provided")
	@Size(min = 10, max = 15, message = "Password must be between {min} and {max} characters long")
	@Column
	
	private String password;
	@Column
	private EUserRole userRole;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<PupilEntity> pupils = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<TeacherEntity> teachers = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<ParentEntity> parents = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<AdministratorEntity> admin = new ArrayList<>();

	public UserEntity() {
		super();
	}


	public List<AdministratorEntity> getAdmin() {
		return admin;
	}


	public void setAdmin(List<AdministratorEntity> admin) {
		this.admin = admin;
	}



	public UserEntity(Integer id, String username, String password, EUserRole userRole, List<PupilEntity> pupils,
			List<TeacherEntity> teachers, List<ParentEntity> parents, List<AdministratorEntity> admin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.pupils = pupils;
		this.teachers = teachers;
		this.parents = parents;
		this.admin = admin;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

	public List<ParentEntity> getParents() {
		return parents;
	}

	public void setParents(List<ParentEntity> parents) {
		this.parents = parents;
	}
	
	
}