package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskService {

    Task save(TaskDto taskDto);

    boolean update(TaskDto taskDto);

    void delete(int id);

    List<Task> findAll();

    List<Task> findByStatus(boolean done);

    TaskDto findById(int id, boolean editing);

    void markDone(int id);

}