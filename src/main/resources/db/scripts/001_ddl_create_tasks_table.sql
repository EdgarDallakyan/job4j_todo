CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title VARCHAR(255),
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);