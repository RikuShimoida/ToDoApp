package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="deadline")
	private Date deadline;

	public Task() {
		super();
	}

	public Task(Integer id, String name,Integer userId, Date deadline) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.deadline = deadline;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Date getDeadline() {
		return deadline;
	}

	public void setDeadLine(Date deadline) {
		this.deadline = deadline;
	}
	
	
	
}