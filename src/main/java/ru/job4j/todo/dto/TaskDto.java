package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.job4j.todo.model.User;

import java.time.ZonedDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private int id;
    private String title;
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime created;

    private boolean done;
    private boolean editing;
    private User user;
    private int priorityId;
    private List<Integer> categoryIds = new ArrayList<>();
}