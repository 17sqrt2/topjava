DELETE FROM user_roles;
DELETE
FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2015-05-30 10:00', 'User breakfast', 500),
       (100000, '2015-05-30 13:00', 'User lunch', 1000),
       (100000, '2015-05-30 20:00', 'User dinner', 500),
       (100000, '2015-05-31 10:00', 'User breakfast', 1000),
       (100000, '2015-05-31 13:00', 'User lunch', 500),
       (100000, '2015-05-31 20:00', 'User dinner', 510),
       (100001, '2015-06-01 14:00', 'Admin lunch', 510),
       (100001, '2015-06-01 21:00', 'Admin dinner', 1500);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
