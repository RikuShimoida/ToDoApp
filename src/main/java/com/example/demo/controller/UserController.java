package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.usecase.TaskUseCase;
import com.example.demo.usecase.UserUseCase;

@Controller
public class UserController {
	
	@Autowired HttpSession session;
	@Autowired UserUseCase userUseCase;
	@Autowired TaskUseCase taskUseCase;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/gotoCreateUser")
	public String gotoCreateUser() {
		return "createUser";
	}
	
	@RequestMapping("/createUser")
	public ModelAndView createUser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, ModelAndView mv){
		
		userUseCase.createUser(new User(name, email, password));
		
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password, ModelAndView mv) {
	
		User user = userUseCase.doLogin(email, password);
		
		if(user != null) {
			session.setAttribute("user", user);
			mv.addObject("user", session.getAttribute("user"));
			mv.setViewName("user");
		}else {
			mv.addObject("message", "該当するユーザーは見つかりませんでした");
			mv.setViewName("index");
		}
		
		session.setAttribute("userId", user.getId());
		
		List<Task> tasks = taskUseCase.showAllTask(user.getId());
		mv.addObject("tasks", tasks);
		
		return mv;
	}
}
