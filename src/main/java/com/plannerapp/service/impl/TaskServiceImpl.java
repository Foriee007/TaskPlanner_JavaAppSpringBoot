package com.plannerapp.service.impl;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PriorityRepository priorityRepository;
    private final UserSession userSession;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, PriorityRepository priorityRepository, UserSession userSession) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.priorityRepository = priorityRepository;
        this.userSession = userSession;
    }

    @Override
    public void addTask(AddTaskDto addTaskDto) {
        if (!userSession.isLoggedIn()) {
            throw  new IllegalArgumentException("Invalid user");
        }
        Task task = new Task();
        task.setDescription(addTaskDto.getDescription());
        task.setDate(addTaskDto.getDueDate());
        task.setPriority(priorityRepository.findByName(addTaskDto.getPriority()).orElseThrow(() -> new IllegalArgumentException("Invalid priority")));
        task.setUser(null);
        taskRepository.save(task);
    }

    @Override
    public void assignTaskWithId(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public void removeTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        if (task.getUser().getId().equals(userId)) {
            taskRepository.delete(task);
        }
    }

    @Override
    public Set<Task> getAllUnassignedTasks() {
        return taskRepository.findAllByUserIsNull();
    }

    @Override
    public Set<Task> getAllTasksAssignedToUser(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public void returnTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));
        if (task.getUser().getId().equals(userId)) {
            task.setUser(null);
            taskRepository.save(task);
        }
    }

    @Override
    public long getAllCount() {
        long count =  taskRepository.countAllByUserIsNull();
        return count;
    }
}
