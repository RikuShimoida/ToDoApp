package com.example.demo.usecase;

import com.example.demo.entity.User;

public interface UserUseCase {
	
	User doLogin(String email, String password);
	
	void createUser(User user);

}
