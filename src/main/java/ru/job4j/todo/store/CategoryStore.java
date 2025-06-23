package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryStore {

    List<Category> findAll();

    List<Category> findByIds(List<Integer> ids);

}