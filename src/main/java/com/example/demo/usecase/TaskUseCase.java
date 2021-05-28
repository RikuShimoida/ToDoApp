package com.example.demo.usecase;

import java.util.List;

import com.example.demo.entity.Task;

public interface TaskUseCase {
	
	List<Task> showAllTask(Integer userId);
	
	void createTask(Task task);
	
	void doneTask(Task task);
	
	void deleteTask(Task task);
	
	void editTask(Task task);
	
	Task getTask(Integer taskId);
	
	List<Task> showGarbageTasks(Integer userId);
	
	void restore(Task task);

}
