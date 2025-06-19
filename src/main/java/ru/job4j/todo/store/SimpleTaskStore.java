package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskStore implements TaskStore {

    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.persist(task);
            tx.commit();
            return task;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean update(Task task) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            String hql = """
                        UPDATE Task SET title=:title, description=:description, created=:created, done=:done
                        WHERE id=:id
                        """;
            int updateCount = session.createMutationQuery(hql)
                    .setParameter("title", task.getTitle())
                    .setParameter("description", task.getDescription())
                    .setParameter("created", task.getCreated())
                    .setParameter("done", task.isDone())
                    .setParameter("id", task.getId())
                    .executeUpdate();
            tx.commit();
            return updateCount > 0;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createMutationQuery("DELETE Task WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Task> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Task ORDER BY created", Task.class)
                    .getResultList();
        }
    }

    @Override
    public List<Task> findByStatus(boolean done) {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM Task WHERE done=:done ORDER BY created", Task.class)
                    .setParameter("done", done)
                    .getResultList();
        }
    }

    @Override
    public Optional<Task> findById(int id) {
        try (Session session = sf.openSession()) {
            Task task = session.createQuery("FROM Task WHERE id=:id", Task.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
            return Optional.ofNullable(task);
        }
    }

    @Override
    public void markDone(int id) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createMutationQuery("UPDATE Task SET done=:done WHERE id=:id")
                    .setParameter("done", true)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}