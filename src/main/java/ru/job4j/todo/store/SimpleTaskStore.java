package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private final CrudStore crudStore;

    @Override
    public Task save(Task task) {
        crudStore.run(session -> session.persist(task));
        return task;
    }

    @Override
    public void update(Task task) {
        crudStore.run(session -> session.merge(task));
    }

    @Override
    public void delete(int id) {
        crudStore.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<Task> findAll() {
        return crudStore.query("FROM Task t JOIN FETCH t.priority ORDER BY t.id", Task.class);
    }

    @Override
    public List<Task> findByStatus(boolean done) {
        return crudStore.query(
                "FROM Task t JOIN FETCH t.priority WHERE t.done=:done ORDER BY t.id", Task.class,
                Map.of("done", done)
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudStore.optional(
                "FROM Task t JOIN FETCH t.priority WHERE t.id=:id", Task.class,
                Map.of("id", id)
        );
    }

    @Override
    public void markDone(int id) {
        crudStore.run(
                "UPDATE Task SET done=:done WHERE id=:id",
                Map.of("done", true, "id", id)
        );
    }
}