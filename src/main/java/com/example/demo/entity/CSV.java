package com.example.demo.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CSV {
	
	  List<Integer> id;

	  List<String> name;

	  List<String> deadline;

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> taskName) {
		this.name = taskName;
	}

	public List<String> getDeadline() {
		return deadline;
	}

	public void setDeadline(List<String> deadline) {
		this.deadline = deadline;
	}
}
