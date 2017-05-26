INSERT INTO users (user_id, name, username, password) VALUES
(null, 'Oscar Mike', 'omike', '12345'),
(null, 'Michelle Smith', 'mismi', 'oiblin');

INSERT INTO groups (group_id, name) VALUES
(null, 'best coding team');

INSERT INTO user_group (user_id, group_id) VALUES
(1, 1),
(2, 1);

INSERT INTO task (task_id, name, group_id, done) VALUES
(null, 'main method', 1, 1),
(null, 'client interface', 1, 0),
(null, 'database connection', 1, 0),
(null, 'REST server', 1, 1);

INSERT INTO message (message_id, content, user_id, group_id) VALUES
(null, 'Eu vou começar a desenvolver a client interface', 2, 1),
(null, 'OK! Eu acabei agora o server, vou começar a database connection', 1, 1);
