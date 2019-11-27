/*
 * Creates the tables related to the user, user queries and likes
 * Uses PostgreSQL syntax.
 */

-- create artist tables

CREATE TABLE IF NOT EXISTS users (
	id                  INTEGER PRIMARY KEY,
	username            VARCHAR(50) NOT NULL,
	password_hash       VARCHAR(128) NOT NULL,
	password_salt       VARCHAR(128) NOT NULL,
	date_registered     DATE NOT NULL,
	email			VARCHAR(50),
	sex				VARCHAR(10),
	date_of_birth		DATE
);

CREATE TABLE IF NOT EXISTS user_query (
	id                  INTEGER PRIMARY KEY,
	contents			VARCHAR NOT NULL,
	type			     VARCHAR(50) NOT NULL,
	time_searched		TIMESTAMP NOT NULL,
	user_id			INTEGER NOT NULL REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS groups (
	gid 				UUID PRIMARY KEY,
	type				VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS adds_xref (
	user_id			INTEGER REFERENCES users (id),
	group_gid			UUID	REFERENCES groups (gid),
	liked			BOOL NOT NULL,
	time_added		TIMESTAMP NOT NULL,
	PRIMARY KEY (user_id, group_gid)
);

