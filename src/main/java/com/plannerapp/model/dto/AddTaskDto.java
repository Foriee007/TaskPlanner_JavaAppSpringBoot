package com.plannerapp.model.dto;

import com.plannerapp.model.entity.PriorityType;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddTaskDto {

    @Size(min = 2, max = 40, message = "Description length must be between  2 and 50 characters!")
    @NotNull
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The input date must be in the feature or today!")
    @NotNull(message = "You must select a date!")
    private LocalDate dueDate;

    @NotNull(message = "You must select a priority!")
    private PriorityType priority;

    public AddTaskDto() {
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public  LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate( LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public  PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }
}
