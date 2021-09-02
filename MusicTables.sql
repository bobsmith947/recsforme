/*
 * Creates the tables related to music groups: artists, releases, release groups, and recordings.
 * Uses the MusicBrainz schema https://musicbrainz.org/doc/MusicBrainz_Database/Schema
 * Code adpated from https://github.com/metabrainz/musicbrainz-server/tree/master/admin/sql
 * Uses PostgreSQL syntax.
 */

-- create artist tables

CREATE TABLE IF NOT EXISTS artist_type (
	id                  INTEGER PRIMARY KEY,
	name                VARCHAR NOT NULL,
	parent              INTEGER REFERENCES artist_type (id),
	child_order         INTEGER NOT NULL DEFAULT 0,
	description         VARCHAR,
	gid                 UUID NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS artist_type_idx_gid ON artist_type (gid);

CREATE TABLE IF NOT EXISTS artist_credit (
	id                  INTEGER PRIMARY KEY,
	name                VARCHAR NOT NULL,
	artist_count        SMALLINT NOT NULL,
	ref_count           INTEGER DEFAULT 0,
	created             TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	edits_pending       INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS gender (
	id                  INTEGER PRIMARY KEY,
	name                VARCHAR NOT NULL,
	parent              INTEGER REFERENCES gender (id),
	child_order         INTEGER NOT NULL DEFAULT 0,
	description         VARCHAR,
	gid                 UUID NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS gender_idx_gid ON gender (gid);

CREATE TABLE IF NOT EXISTS artist (
	id                  INTEGER PRIMARY KEY,
	gid                 UUID NOT NULL,
	name                VARCHAR NOT NULL,
	sort_name           VARCHAR NOT NULL,
	begin_date_year     SMALLINT,
	begin_date_month    SMALLINT,
	begin_date_day      SMALLINT,
	end_date_year       SMALLINT,
	end_date_month      SMALLINT,
	end_date_day        SMALLINT,
	type                INTEGER REFERENCES artist_type (id),
	area                INTEGER, -- not used
	gender              INTEGER REFERENCES gender (id),
	comment             VARCHAR,
	edits_pending       INTEGER DEFAULT 0,
	last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	ended               BOOLEAN DEFAULT FALSE,
	begin_area          INTEGER, -- not used
	end_area            INTEGER -- not used
);

CREATE UNIQUE INDEX IF NOT EXISTS artist_idx_gid ON artist (gid);
CREATE INDEX IF NOT EXISTS artist_idx_name ON artist (name);
CREATE INDEX IF NOT EXISTS artist_idx_sort_name ON artist (sort_name);

CREATE TABLE IF NOT EXISTS artist_credit_name (
	artist_credit       INTEGER REFERENCES artist_credit (id) ON DELETE CASCADE,
	position            SMALLINT NOT NULL,
	artist              INTEGER NOT NULL REFERENCES artist (id) ON DELETE CASCADE,
	name                VARCHAR NOT NULL,
	join_phrase		VARCHAR NOT NULL,
	PRIMARY KEY (artist_credit, position)
);

CREATE INDEX IF NOT EXISTS artist_credit_name_idx_artist ON artist_credit_name (artist);

-- create release/release group tables

CREATE TABLE IF NOT EXISTS release_group_primary_type (
	id                  INTEGER PRIMARY KEY,
	name                VARCHAR NOT NULL,
	parent              INTEGER REFERENCES release_group_primary_type (id),
	child_order         INTEGER NOT NULL DEFAULT 0,
	description         VARCHAR,
	gid                 UUID NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS release_group_primary_type_idx_gid ON release_group_primary_type (gid);

CREATE TABLE IF NOT EXISTS release_group_secondary_type (
	id                  INTEGER PRIMARY KEY,
	name                VARCHAR NOT NULL,
	parent              INTEGER REFERENCES release_group_secondary_type (id),
	child_order         INTEGER NOT NULL DEFAULT 0,
	description         VARCHAR,
	gid                 UUID NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS release_group_secondary_type_idx_gid ON release_group_secondary_type (gid);

CREATE TABLE IF NOT EXISTS release_group (
	id                  INTEGER PRIMARY KEY,
	gid                 UUID NOT NULL,
	name                VARCHAR NOT NULL,
	artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
	type                INTEGER REFERENCES release_group_primary_type (id),
	comment             VARCHAR,
	edits_pending       INTEGER NOT NULL DEFAULT 0,
	last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS release_group_idx_gid ON release_group (gid);
CREATE INDEX IF NOT EXISTS release_group_idx_name ON release_group (name);
CREATE INDEX IF NOT EXISTS release_group_idx_artist_credit ON release_group (artist_credit);

CREATE TABLE IF NOT EXISTS release_group_secondary_type_join (
	release_group INTEGER REFERENCES release_group (id),
	secondary_type INTEGER REFERENCES release_group_secondary_type (id),
	created TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	PRIMARY KEY (release_group, secondary_type)
);

CREATE TABLE IF NOT EXISTS release_group_meta (
	id                  INTEGER PRIMARY KEY REFERENCES release_group (id),
	release_count       INTEGER NOT NULL DEFAULT 0,
	first_release_date_year   SMALLINT,
	first_release_date_month  SMALLINT,
	first_release_date_day    SMALLINT,
	rating              SMALLINT,
	rating_count        INTEGER
);

CREATE TABLE IF NOT EXISTS release (
	id                  INTEGER PRIMARY KEY,
	gid                 UUID NOT NULL,
	name                VARCHAR NOT NULL,
	artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
	release_group       INTEGER NOT NULL REFERENCES release_group (id),
	status              INTEGER, -- not used
	packaging           INTEGER, -- not used
	language            INTEGER, -- not used
	script              INTEGER, -- not used
	barcode             VARCHAR,
	comment             VARCHAR,
	edits_pending       INTEGER NOT NULL DEFAULT 0,
	quality             SMALLINT NOT NULL DEFAULT -1,
	last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS release_idx_gid ON release (gid);
CREATE INDEX IF NOT EXISTS release_idx_name ON release (name);
CREATE INDEX IF NOT EXISTS release_idx_release_group ON release (release_group);
CREATE INDEX IF NOT EXISTS release_idx_artist_credit ON release (artist_credit);

CREATE TABLE IF NOT EXISTS release_coverart (
	id                  INTEGER PRIMARY KEY REFERENCES release (id),
	last_updated        TIMESTAMP WITH TIME ZONE,
	cover_art_url       VARCHAR(255)
);

CREATE TYPE cover_art_presence AS ENUM ('absent', 'present', 'darkened');

CREATE TABLE IF NOT EXISTS release_meta (
	id                  INTEGER PRIMARY KEY REFERENCES release (id),
	date_added          TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	info_url            VARCHAR(255),
	amazon_asin         VARCHAR(10),
	amazon_store        VARCHAR(20),
	cover_art_presence  cover_art_presence NOT NULL DEFAULT 'absent'
);

-- create medium/track tables

CREATE TABLE IF NOT EXISTS medium (
	id                  INTEGER PRIMARY KEY,
	release             INTEGER NOT NULL REFERENCES release (id),
	position            INTEGER NOT NULL,
	format              INTEGER, -- not used
	name                VARCHAR NOT NULL,
	edits_pending       INTEGER NOT NULL DEFAULT 0,
	last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	track_count         INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS medium_idx_track_count ON medium (track_count);

CREATE TABLE IF NOT EXISTS track (
	id                  INTEGER PRIMARY KEY,
	gid                 UUID NOT NULL,
	recording           INTEGER NOT NULL, -- not used
	medium              INTEGER NOT NULL REFERENCES medium (id),
	position            INTEGER NOT NULL,
	number              VARCHAR NOT NULL,
	name                VARCHAR NOT NULL,
	artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
	length              INTEGER,
	edits_pending       INTEGER NOT NULL DEFAULT 0,
	last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	is_data_track       BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX IF NOT EXISTS track_idx_gid ON track (gid);
CREATE INDEX IF NOT EXISTS track_idx_recording ON track (recording);
CREATE INDEX IF NOT EXISTS track_idx_name ON track (name);
CREATE INDEX IF NOT EXISTS track_idx_artist_credit ON track (artist_credit);

