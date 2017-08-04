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
  (100000, 'обед', '2007-12-03 12:15:30' , 1520),
  (100000, 'завтрак', '2007-12-03 9:15:30', 500),
  (100000, 'ужин', '2007-12-03 21:15:30', 320),
  (100001, 'обед', '2007-12-04 13:15:30', 1320),
  (100001, 'завтрак', '2007-12-04 7:15:30', 200),
  (100001, 'ужин', '2007-12-04 19:15:30', 220);