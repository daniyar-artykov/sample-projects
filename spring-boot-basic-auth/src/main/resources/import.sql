INSERT INTO users (username, password) VALUES ('admin', 'admin12');
INSERT INTO users (username, password) VALUES ('john', 'john12');

INSERT INTO role (role) VALUES ('ROLE_ADMIN');
INSERT INTO role (role) VALUES ('ROLE_USER');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);