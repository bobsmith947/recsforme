/*
 * Creates the tables related to music groups: artists, releases, release groups, and recordings.
 * Uses the MusicBrainz schema https://musicbrainz.org/doc/MusicBrainz_Database/Schema
 * Code adpated from https://github.com/metabrainz/musicbrainz-server/tree/master/admin/sql
 * Uses PostgreSQL syntax.
*/

-- drop any existing tables
DROP TABLE IF EXISTS
	artist,
	artist_type,
	artist_credit,
	artist_credit_name,
	gender,
	release,
	release_group,
	release_group_primary_type,
	medium,
	medium_format,
	track,
	recording
CASCADE;

-- create artist tables
CREATE TABLE artist (
	-- TODO
);

CREATE TABLE artist_type (
	-- TODO
);

CREATE TABLE artist_credit (
	-- TODO
);

CREATE TABLE artist_credit_name (
	-- TODO
);

CREATE TABLE gender (
	-- TODO
);

-- create release/release group tables
CREATE TABLE release (
	-- TODO
);

CREATE TABLE release_group (
	-- TODO
);

CREATE TABLE release_group_primary_type (
	-- TODO
);

-- create medium/track/recording tables
CREATE TABLE medium (
	-- TODO
);

CREATE TABLE medium_format (
	-- TODO
);

CREATE TABLE track (
	-- TODO
);

CREATE TABLE recording (
	-- TODO
);
