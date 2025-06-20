package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class SimpleUserStore implements UserStore {

    private final CrudStore crudStore;

    @Override
    public Optional<User> save(User user) {
        try {
            crudStore.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (ConstraintViolationException e) {
            log.warn("Duplicate login attempt: {}", user.getLogin());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional(
                "FROM User WHERE login=:login AND password=:password", User.class,
                Map.of("login", login, "password", password)
        );
    }
}