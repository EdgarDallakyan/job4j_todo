package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;

@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private final CategoryStore simpleCategoryStore;

    @Override
    public List<Category> findAll() {
        return simpleCategoryStore.findAll();
    }

}