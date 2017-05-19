INSERT INTO user (name, username, password) VALUES
('Oscar Mike', 'omike', '12345'),
('Michelle Smith', 'mismi', 'oiblin');

INSERT INTO groups (name) VALUES
('best coding team');

INSERT INTO user_group (user_id, group_id) VALUES
(1, 1),
(2, 1);

INSERT INTO list (name, group_id) VALUES
('prog proj TODO list', 1);

INSERT INTO task (name, list_id, done) VALUES
('main method', 1, 'true'),
('client interface', 1, 'false'),
('database connection', 1, 'false'),
('REST server', 1, 'true');

INSERT INTO message (content, user_id, group_id) VALUES
('Eu vou começar a desenvolver a client interface', 2, 1),
('OK! Eu acabei agora o server, vou começar a database connection', 1, 1);
