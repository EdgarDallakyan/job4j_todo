package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class SimpleCategoryStore implements CategoryStore {

    private final CrudStore crudStore;

    @Override
    public List<Category> findAll() {
        return crudStore.query("FROM Category", Category.class);
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return crudStore.query(
                "FROM Category c WHERE c.id IN (:ids)",
                Category.class,
                Map.of("ids", ids)
        );
    }
}