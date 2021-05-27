package com.example.demo.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Component
public class UserUseCaseImpl implements UserUseCase {
	
	@Autowired UserRepository userRepository;

	@Override
	public User doLogin(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	@Override
	public void createUser(User user) {
		userRepository.saveAndFlush(user);
	}

}
