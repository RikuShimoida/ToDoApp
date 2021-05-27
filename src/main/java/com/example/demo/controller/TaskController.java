package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.usecase.TaskUseCase;
import com.example.demo.usecase.UserUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.tools.jdeprscan.CSV;

@Controller
public class TaskController {
	
	@Autowired HttpSession session;
	@Autowired TaskUseCase taskUseCase;
	@Autowired UserUseCase userUseCase;
	
	@RequestMapping("/gotoCreateTask")
	public ModelAndView gotoCreateTask(ModelAndView mv) {	
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("today", sdf.format(date));
		
		mv.setViewName("createTask");
		return mv;
	}
		
	@RequestMapping("gotoCreateTask/createTask")
	public ModelAndView createTask(@RequestParam("taskname") String name, @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadline, ModelAndView mv) {
		
		taskUseCase.createTask(new Task(name, (Integer)session.getAttribute("userId"),  deadline, false));
		
		mv.addObject("message", "新規タスクを作成しました");
		
		User user = (User)session.getAttribute("user");
		
		mv.addObject("user", user);
		
		List<Task> tasks = taskUseCase.showAllTask(user.getId());
		mv.addObject("tasks", tasks);
		mv.setViewName("user");
		return mv;
	}
	
	@RequestMapping("/task/{id}/edit")
	public ModelAndView editTask(@PathVariable("id") Integer id, ModelAndView mv) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("today", sdf.format(date));
		
		Task task = taskUseCase.getTask(id);
		
		//更新後のログイン時に使いたい。
		session.setAttribute("task", task);
		
		mv.addObject("task", task);
		mv.setViewName("editTask");
		
		return mv;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("name") String name,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadline, ModelAndView mv) {
		
		 Task task = (Task)session.getAttribute("task");
		taskUseCase.editTask(new Task(task.getId(), name,task.getUserId(), deadline));
		
		User user = (User)session.getAttribute("user");
		mv.addObject("message", "タスクの情報を変更しました");
		
		List<Task> tasks = taskUseCase.showAllTask(user.getId());
		mv.addObject("user", user);
		mv.addObject("tasks", tasks);
		
		mv.setViewName("user");
		return mv;
	}
	
	@RequestMapping("/task/{id}/garbage")
	public ModelAndView garbageTask(@PathVariable("id") Integer id, ModelAndView mv) {
		
		taskUseCase.doneTask(taskUseCase.getTask(id));
		
		Task task = taskUseCase.getTask(id);
		
		User user = (User)session.getAttribute("user");
		mv.addObject("message", "タスクが一つ完了しました！");
		
		List<Task> tasks = taskUseCase.showAllTask(user.getId());
		mv.addObject("user", user);
		mv.addObject("tasks", tasks);
		
		mv.setViewName("user");
		
		return mv;
	}
	
	@RequestMapping("gotoGarbageBox")
	public ModelAndView garbageBox(ModelAndView mv) {
		
		User user = (User)session.getAttribute("user");
		
		List<Task> tasks = taskUseCase.showGarbageTasks(user.getId());
		if(tasks.size() == 0) {
			mv.addObject("message", "ゴミ箱にタスクが1件もありません。");
		}
		mv.addObject("user", user);
		mv.addObject("tasks", tasks);
		
		mv.setViewName("garbageBox");
		
		return mv;
	}
	
	@RequestMapping("/task/{id}/delete")
	public ModelAndView deleteTask(@PathVariable("id") Integer id, ModelAndView mv) {
		
		taskUseCase.deleteTask(taskUseCase.getTask(id));
		
		User user = (User)session.getAttribute("user");
		mv.addObject("message", "タスクを完全に削除しました");
		
		List<Task> tasks = taskUseCase.showAllTask(user.getId());
		mv.addObject("user", user);
		mv.addObject("tasks", tasks);
		
		mv.setViewName("user");
		
		return mv;
	}
	
	 @PostMapping(value = "/csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
		      + "; charset=UTF-8; Content-Disposition: attachment")
		  @ResponseBody
		  public Object csvDownload(@ModelAttribute("csvForm") CSV records) throws JsonProcessingException {
		    List<CsvColumn> csvList = new ArrayList<>();
		    for (int i = 0; i < records.getId().size(); i++) { // レコードの数ぶんだけループ回して
		      csvList.add(new CsvColumn(records.getId().get(i), records.getCategoryId().get(i), records.getAdminName().get(i), records.getAssetName().get(i), records.getRemarks().get(i)));
		    }
		    CsvMapper mapper = new CsvMapper();
		    CsvSchema schema = mapper.schemaFor(CsvColumn.class).withHeader();
		    return mapper.writer(schema).writeValueAsString(csvList);
		  }

}
