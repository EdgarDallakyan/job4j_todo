ALTER TABLE tasks
ADD COLUMN user_id INT;

ALTER TABLE tasks
ADD CONSTRAINT fk_task_user
FOREIGN KEY (user_id)
REFERENCES todo_user(id);