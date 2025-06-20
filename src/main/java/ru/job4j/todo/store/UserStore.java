package ru.job4j.todo.store;

import java.util.Optional;
import ru.job4j.todo.model.User;

public interface UserStore {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

}