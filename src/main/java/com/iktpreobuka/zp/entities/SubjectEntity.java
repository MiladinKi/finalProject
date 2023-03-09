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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table

public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subject_id")
	private Integer id;
	@Column
	@NumberFormat
	private Integer weekClassFund;
	@NotNull(message = "Name must be provided")
	@Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long")
	@Column
	private String name;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Teacher",joinColumns = 
	{@JoinColumn(name = "subject_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "teacher_id", nullable = false, updatable = false)})
	protected List<TeacherEntity> teachers = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Pupil",joinColumns = 
	{@JoinColumn(name = "subject_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "pupil_id", nullable = false, updatable = false)})
	protected List<PupilEntity> pupils = new ArrayList<>();


	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<MarkEntity> marks = new ArrayList<>();

	public SubjectEntity() {
		super();
	}

	public SubjectEntity(Integer id, Integer weekClassFund,
			@NotNull(message = "Name must be provided") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long") String name,
			List<TeacherEntity> teachers, List<PupilEntity> pupils, List<MarkEntity> marks) {
		super();
		this.id = id;
		this.weekClassFund = weekClassFund;
		this.name = name;
		this.teachers = teachers;
		this.pupils = pupils;
		this.marks = marks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWeekClassFund() {
		return weekClassFund;
	}

	public void setWeekClassFund(Integer weekClassFund) {
		this.weekClassFund = weekClassFund;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}

	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}


}