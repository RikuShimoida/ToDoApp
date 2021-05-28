package com.example.demo.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@Component
public class TaskUseCaseImpl implements TaskUseCase {

	@Autowired TaskRepository taskRepository;
	
	@Override
	public List<Task> showAllTask(Integer userId) {
		return taskRepository.findByUserIdAndIsDeletedOrderByIdAsc(userId, false);
	}
	
	@Override
	public void createTask(Task task) {
		taskRepository.saveAndFlush(task);
	}
	
	@Override
	public void doneTask(Task task) {
		
		Optional<Task> optionalTask = taskRepository.findById(task.getId());
		
		if(optionalTask.isPresent()) {
			Task garbageTask = optionalTask.get();
			taskRepository.saveAndFlush(new Task(garbageTask.getId(), garbageTask.getName(), garbageTask.getUserId(), garbageTask.getDeadline(), true));
		}
	}
	
	@Override
	public void editTask(Task task) {
		taskRepository.saveAndFlush(task);
	}
	
	@Override
	public Task getTask(Integer taskId) {
		return taskRepository.findById(taskId).get();
	}
	
	@Override
	public List<Task> showGarbageTasks(Integer userId){
		return taskRepository.findByUserIdAndIsDeletedOrderByIdAsc(userId, true);
	}

	@Override
	public void deleteTask(Task task) {
		
		Optional<Task> willDeleteTask = taskRepository.findById(task.getId());
		
		if(willDeleteTask.isPresent()) {
		Task deleteTask = willDeleteTask.get();
		taskRepository.delete(new Task(deleteTask.getId(), deleteTask.getName(), deleteTask.getUserId(), deleteTask.getDeadline(), true));
		}
		
	}
	
	@Override
	public void restore(Task task) {
		
		Optional<Task> willRestoreTask = taskRepository.findById(task.getId());
		
		if(willRestoreTask.isPresent()) {
			Task restoreTask = willRestoreTask.get();
			taskRepository.saveAndFlush(new Task(restoreTask.getId(), restoreTask.getName(), restoreTask.getUserId(), restoreTask.getDeadline(), false));
			}
	}
}
