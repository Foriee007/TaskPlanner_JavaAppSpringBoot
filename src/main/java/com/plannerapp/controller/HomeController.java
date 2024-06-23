package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.Task;
import com.plannerapp.service.TaskService;
import com.plannerapp.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping(name = "/")
public class HomeController {
    private final UserSession loggedUser;
    private final UserServiceImpl userService;
    private final TaskService taskService;
    private final UserSession userSession;

    public HomeController(UserSession loggedUser, UserServiceImpl userService, TaskService taskService, UserSession userSession) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.taskService = taskService;
        this.userSession = userSession;
    }

    @GetMapping("/")
    public String index(){ // for Not logged user
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String logged(Model model){
        if (!userSession.isLoggedIn()){
            return "redirect:/"; // Use for home page redirect to index to log in.
        }

        Set<Task> unassignedTasks = taskService.getAllUnassignedTasks();
        model.addAttribute("unassignedTasks", unassignedTasks);

        Set<Task> myTasks = taskService.getAllTasksAssignedToUser(userSession.getUserId());
        model.addAttribute("myTasks", myTasks);


        long allCount = taskService.getAllCount();
        model.addAttribute("allCount", allCount);

        return "home";
    }

}
