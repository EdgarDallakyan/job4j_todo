package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore simpleTaskStore;

    @Override
    public Task save(TaskDto taskDto) {
        return simpleTaskStore.save(toEntity(taskDto));
    }

    @Override
    public boolean update(TaskDto taskDto) {
        return simpleTaskStore.update(toEntity(taskDto));
    }

    @Override
    public void delete(int id) {
        simpleTaskStore.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return simpleTaskStore.findAll();
    }

    @Override
    public List<Task> findByStatus(boolean done) {
        return simpleTaskStore.findByStatus(done);
    }

    @Override
    public TaskDto findById(int id, boolean editing) {
        Task task = simpleTaskStore.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Задача с id " + id + " не найдена"));
        return toDto(task, editing);
    }

    @Override
    public void markDone(int id) {
        simpleTaskStore.markDone(id);
    }

    private Task toEntity(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCreated(dto.getCreated());
        task.setDone(dto.isDone());
        return task;
    }

    private TaskDto toDto(Task task, boolean editing) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCreated(task.getCreated());
        dto.setDone(task.isDone());
        dto.setEditing(editing);
        return dto;
    }
}