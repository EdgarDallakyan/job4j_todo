package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskStore {

    Task save(Task task);

    void update(Task task);

    void delete(int id);

    List<Task> findAll();

    List<Task> findByStatus(boolean done);

    Optional<Task> findById(int id);

    void markDone(int id);

}