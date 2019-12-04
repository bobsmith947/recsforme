/*
 * Creates the tables related to the user, user queries and likes
 * Uses PostgreSQL syntax.
 */

CREATE TABLE IF NOT EXISTS users (
	id                  	SERIAL PRIMARY KEY,
	username            	VARCHAR(50) UNIQUE NOT NULL,
	password_hash       	VARCHAR(128) NOT NULL,
	password_salt       	VARCHAR(128) NOT NULL,
	date_registered     	DATE NOT NULL DEFAULT CURRENT_DATE,
	email			VARCHAR(254),
	sex			VARCHAR(10),
	date_of_birth		DATE
);

CREATE TABLE IF NOT EXISTS user_query (
	id                  	SERIAL PRIMARY KEY,
	contents		VARCHAR(100) NOT NULL,
	type			VARCHAR(20) NOT NULL,
	time_searched		TIMESTAMP NOT NULL DEFAULT LOCALTIMESTAMP,
	user_id			INTEGER REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS groups (
	gid 			UUID PRIMARY KEY,
	type			VARCHAR(20) NOT NULL,
	name			VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS user_groups (
	user_id			INTEGER REFERENCES users (id),
	group_gid		UUID	REFERENCES groups (gid),
	liked			BOOLEAN NOT NULL,
	time_added		TIMESTAMP NOT NULL DEFAULT LOCALTIMESTAMP,
	PRIMARY KEY (user_id, group_gid)
);

