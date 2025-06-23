package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class SimplePriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    @Override
    public Optional<Priority> findById(int id) {
        return crudStore.optional(
                "FROM Priority WHERE id=:id", Priority.class,
                Map.of("id", id)
        );
    }
}