package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDto {
    private int id;
    private String title;
    private boolean done;
    private String authorName;
    private int priorityId;
    private String createdFormatted;
}