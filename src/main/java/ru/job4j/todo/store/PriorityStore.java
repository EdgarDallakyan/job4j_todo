package ru.job4j.todo.store;

import ru.job4j.todo.model.Priority;

import java.util.Optional;

public interface PriorityStore {

    Optional<Priority> findById(int id);

}