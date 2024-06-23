package com.plannerapp.service;

import com.plannerapp.model.dto.AddTaskDto;
import com.plannerapp.model.entity.Task;

import java.util.Set;

public interface TaskService {
    void addTask(AddTaskDto addTaskDto);
    void assignTaskWithId(Long taskId, Long userId);
    void removeTaskById(Long taskId, Long userId);
    Set<Task> getAllUnassignedTasks();
    Set<Task> getAllTasksAssignedToUser(Long userId);
    void returnTask(Long taskId, Long userId);
    long getAllCount();
}
