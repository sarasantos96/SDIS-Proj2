INSERT INTO users (user_id, name, username, password) VALUES
(null, 'Catarina Correia', 'cat', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(null, 'Ana Rita Torres', 'ritz', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(null, 'Daniel Garrido', 'dani', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(null, 'Sara Santos', 'dora', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(null, 'Maria Leal', 'marili', 'm+wjLLPoT0nyLiPYUtroMHhlF8ULfXRLDW74GSogeWU=');




INSERT INTO groups (group_id, name) VALUES
(null, 'SDIS');

INSERT INTO user_group (user_id, group_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);


INSERT INTO task (task_id, name, group_id, done) VALUES
(null, 'User Interface', 1, 1),
(null, 'Populate Database', 1, 0),
(null, 'Gets and Sets', 1, 0),
(null, 'Certificates', 1, 0),
(null, 'Login', 1, 0),
(null, 'Register', 1, 0),
(null, 'Main Menu', 1, 0),
(null, 'Send Message', 1, 0),
(null, 'To Do List', 1, 0),
(null, 'Client', 1, 0),
(null, 'Server', 1, 0),
(null, 'JSON Request', 1, 0),
(null, 'JSON Response', 1, 0),
(null, 'TCP Client', 1, 0),
(null, 'Random', 1, 0),
(null, 'Create Group', 1, 0),
(null, 'Print Message', 1, 0),
(null, 'Database Connection', 1, 0),
(null, 'REST Server', 1, 1),
(null, 'HTTPS', 1, 1),
(null, 'TCP Server', 1, 1),
(null, 'JSSE', 1, 1),
(null, 'Database Creator', 1, 1);

INSERT INTO message (message_id, content, user_id, group_id) VALUES
(null, 'Bom dia, Amigos!!', 2, 1),
(null, 'Boas!', 1, 1),
(null, 'Hello!', 3, 1),
(null, 'Buenos dias!', 4, 1),
(null, 'Oh, oh!', 5, 1),
(null, 'Ja alguem começou o TCP Client?', 1, 1),
(null, 'Nooooo', 2, 1),
(null, 'Quem e que adicionou o JSON?', 3, 1),
(null, 'Fui eu, achei que precisavamos de ajuda.', 4, 1),
(null, 'Mas ja implementei o TCP.', 4, 1),
(null, 'Foi rapido!!', 2, 1),
(null, 'Onze minutos de historia interminavel e sem fim...', 5, 1),
(null, 'Acrescenta isso a to do list!', 1, 1),
(null, 'Ja esta.', 3, 1);

