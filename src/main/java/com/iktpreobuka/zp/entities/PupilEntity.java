package com.iktpreobuka.zp.entities;


import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class PupilEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pupil_id")
	private Integer id;
	@NotNull(message = "First name must be provided")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long")
	@Column
	private String firstName;
	@NotNull(message = "Last name must be provided")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long")
	@Column
	private String lastName;
	@NotNull(message = "Midlename must be provided")
	@Size(min = 2, max = 30, message = "Midlename must be between {min} and {max} characters long")
	@Column
	private String midleName;
	@JsonIgnore
	@JsonFormat(shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	@Column
	@NumberFormat
	private Integer grade;
	@Column
	@NumberFormat
	private Integer semester;
	
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Pupil",joinColumns = 
	{@JoinColumn(name = "pupil_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "subject_id", nullable = false, updatable = false)})
	protected List<SubjectEntity> subjects = new ArrayList<>();

	 @JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	protected UserEntity user;

	
	@JsonIgnore
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected List<MarkEntity> marks = new ArrayList<MarkEntity>();
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	protected ParentEntity parent;
	


	public PupilEntity() {
		super();
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public PupilEntity(Integer id,
			@NotNull(message = "First name must be provided") @Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters long") String firstName,
			@NotNull(message = "Last name must be provided") @Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters long") String lastName,
			@NotNull(message = "Midlename must be provided") @Size(min = 2, max = 30, message = "Midlename must be between {min} and {max} characters long") String midleName,
			Date dateOfBirth, Integer grade, Integer semester, List<SubjectEntity> subjects, UserEntity user,
			List<MarkEntity> marks, ParentEntity parent) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.midleName = midleName;
		this.dateOfBirth = dateOfBirth;
		this.grade = grade;
		this.semester = semester;
		this.subjects = subjects;
		this.user = user;
		this.marks = marks;
		this.parent = parent;
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



	public String getMidleName() {
		return midleName;
	}



	public void setMidleName(String midleName) {
		this.midleName = midleName;
	}



	public Integer getGrade() {
		return grade;
	}



	public void setGrade(Integer grade) {
		this.grade = grade;
	}



	public Integer getSemester() {
		return semester;
	}



	public void setSemester(Integer semester) {
		this.semester = semester;
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



	public List<MarkEntity> getMarks() {
		return marks;
	}



	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}



	public ParentEntity getParent() {
		return parent;
	}



	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

}