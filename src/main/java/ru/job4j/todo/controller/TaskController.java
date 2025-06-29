package ru.job4j.todo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.TaskService;

import java.time.ZonedDateTime;

@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService simpleTaskService;
    private final CategoryService simpleCategoryService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", simpleTaskService.getAllForUserTimezone(user));
        return "index";
    }

    @GetMapping("/tasks/done")
    public String getDone(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", simpleTaskService.findByStatusForUserTimezone(true, user));
        return "tasks/done";
    }

    @GetMapping("/tasks/pending")
    public String getPending(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", simpleTaskService.findByStatusForUserTimezone(false, user));
        return "tasks/pending";
    }

    @GetMapping("/tasks/new")
    public String createTask(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        TaskDto taskDto = new TaskDto();
        taskDto.setEditing(true);
        taskDto.setCreated(ZonedDateTime.now());
        taskDto.setUser(user);
        taskDto.setPriorityId(2);
        model.addAttribute("task", taskDto);
        model.addAttribute("categories", simpleCategoryService.findAll());
        return "tasks/view";
    }

    @PostMapping("/tasks")
    public String saveNewTask(@ModelAttribute TaskDto taskDto) {
        simpleTaskService.save(taskDto);
        return "redirect:/index";
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable int id, @ModelAttribute TaskDto taskDto) {
        simpleTaskService.update(taskDto);
        return "redirect:/tasks/" + id;
    }

    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        TaskDto taskDto = simpleTaskService.findById(id, false);
        model.addAttribute("task", taskDto);
        model.addAttribute("categories", simpleCategoryService.findAll());
        return "tasks/view";
    }

    @GetMapping("/tasks/{id}/edit")
    public String editTask(@PathVariable int id, Model model) {
        TaskDto taskDto = simpleTaskService.findById(id, true);
        model.addAttribute("task", taskDto);
        model.addAttribute("categories", simpleCategoryService.findAll());
        return "tasks/view";
    }

    @GetMapping("/tasks/{id}/done")
    public String markDone(@PathVariable int id) {
        simpleTaskService.markDone(id);
        return "redirect:/tasks/" + id;
    }

    @GetMapping("/tasks/{id}/delete")
    public String delete(@PathVariable int id) {
        simpleTaskService.delete(id);
        return "redirect:/index";
    }
}