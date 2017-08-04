DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, description, datetime, calories) VALUES
  (100000, 'обед', now() , 1520),
  (100000, 'зактрак', now() , 500),
  (100000, 'ужин', now(), 320),
  (100001, 'обед', now() , 1320),
  (100001, 'зактрак', now() , 200),
  (100001, 'ужин', now(), 220);