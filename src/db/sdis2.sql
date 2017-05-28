PRAGMA FOREIGN_KEYS=ON;

.mode column
.headers ON

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS user_group;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS message;

CREATE TABLE users(
	user_id		INTEGER PRIMARY KEY AUTOINCREMENT,
	name		VARCHAR(40)		NOT NULL,
	username	VARCHAR(20)		NOT NULL,
	password	VARCHAR(100)		NOT NULL
);

CREATE TABLE groups(
	group_id	INTEGER PRIMARY KEY,
	name		VARCHAR(40)		NOT NULL
);

CREATE TABLE user_group(
	user_id		INTEGER,
	group_id	INT				NOT NULL,
	FOREIGN KEY (user_id)  	REFERENCES users (user_id)	ON DELETE CASCADE,
	FOREIGN KEY (group_id)	REFERENCES groups (group_id)	ON DELETE CASCADE
);

CREATE TABLE task(
	task_id		INTEGER PRIMARY KEY,
	name		VARCHAR(60)		NOT NULL,
	group_id		INT				NOT NULL,
	done		INT NOT NULL,
	FOREIGN KEY (group_id)  	REFERENCES groups (group_id)	ON DELETE CASCADE
);

CREATE TABLE message(
	message_id 	INTEGER PRIMARY KEY,
	content		VARCHAR(140)	NOT NULL,
	user_id		INT				NOT NULL,
	group_id	INT				NOT NULL,
	FOREIGN KEY (user_id)	REFERENCES users (user_id)	ON DELETE CASCADE,
	FOREIGN KEY (group_id)	REFERENCES groups (group_id)	ON DELETE CASCADE
);
