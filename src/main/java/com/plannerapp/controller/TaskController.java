package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.model.entity.Task;
import com.plannerapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserSession userSession;

    public TaskController(TaskService taskService, UserSession userSession) {
        this.taskService = taskService;
        this.userSession = userSession;
    }

    @ModelAttribute("addTaskDto")
    public AddTaskDto initAddTaskDto() {
        return new AddTaskDto();
    }

    @GetMapping("/add")
    public String showAddTaskForm() {
        if (!userSession.isLoggedIn()){
            return "redirect:/login"; // Use for home page redirect to index to log in.
        }
        return "task-add";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("addTaskDto") AddTaskDto addTaskDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addTaskDto", addTaskDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTaskDto", bindingResult);
            return "redirect:/tasks/add";
        }

        taskService.addTask(addTaskDto);
        return "redirect:/home";
    }

   /* @GetMapping("/unassigned")
    public String viewUnassignedTasks(Model model) {
        Set<Task> unassignedTasks = taskService.getAllUnassignedTasks();
        model.addAttribute("unassignedTasks", unassignedTasks);
        return "home";
    }*/

   /* @GetMapping("/my-tasks")
    public String viewMyTasks(Model model) {
        Set<Task> myTasks = taskService.getAllTasksAssignedToUser(userSession.getUserId());
        model.addAttribute("myTasks", myTasks);
        return "my-tasks";
    }*/

    @PostMapping("/assign/{taskId}")
    public String assignTask(@PathVariable Long taskId) {
        taskService.assignTaskWithId(taskId, userSession.getUserId());
        return "redirect:/home";
    }

    @PostMapping("/return/{taskId}")
    public String returnTask(@PathVariable Long taskId) {
        taskService.returnTask(taskId, userSession.getUserId());
        return "redirect:/home";
    }

    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskService.removeTaskById(taskId, userSession.getUserId());
        return "redirect:/home";
    }
}
