package com.plannerapp.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "priorities")
public class Priority extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private PriorityType name;

    private  String description;

    @OneToMany(mappedBy = "priority")
    private Set<Task> tasks;

    public Priority() {
        this.tasks = new HashSet<>();
    }

    public Priority(PriorityType name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public PriorityType getName() {
        return name;
    }

    public void setName(PriorityType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
