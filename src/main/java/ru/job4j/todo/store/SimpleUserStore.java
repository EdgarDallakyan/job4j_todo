package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class SimpleUserStore implements UserStore {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return Optional.of(user);
        } catch (ConstraintViolationException e) {
            log.warn("Duplicate login attempt: {}", user.getLogin());
            return Optional.empty();
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        try (Session session = sf.openSession()) {
            User user = session.createQuery("FROM User WHERE login=:login AND password=:password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
            return Optional.ofNullable(user);
        }
    }
}