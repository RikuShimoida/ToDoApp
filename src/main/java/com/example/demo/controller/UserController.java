package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired HttpSession session;
	@Autowired UserRepository userRepository;
	@Autowired TaskRepository taskRepository;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password, ModelAndView mv) {
		
	User user = userRepository.findByEmailAndPassword(email, password);
		
		if(user != null) {
			session.setAttribute("user", user);
			mv.addObject("user", session.getAttribute("user"));
			mv.setViewName("user");
		}else {
			mv.addObject("message", "該当するユーザーは見つかりませんでした");
			mv.setViewName("index");
		}
		
		List<Task> tasks = taskRepository.findAllById(user.getId());
		mv.addObject("tasks", tasks);
		
		return mv;
	}

}
