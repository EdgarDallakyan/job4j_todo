package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.dto.TaskListDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;

public interface TaskService {

    Task save(TaskDto taskDto);

    void update(TaskDto taskDto);

    void delete(int id);

    List<TaskListDto> getAllForUserTimezone(User user);

    List<TaskListDto> findByStatusForUserTimezone(boolean done, User user);

    TaskDto findById(int id, boolean editing);

    void markDone(int id);

}