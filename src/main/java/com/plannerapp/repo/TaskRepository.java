package com.plannerapp.repo;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Set<Task> findAllByPriority(Priority priority);
    Set<Task> findAllByUserIsNull();
    Set<Task> findAllByUserId(Long userId);
    long countAllByUserIsNull();
}
