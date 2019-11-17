/*
 * Creates the tables related to music groups: artists, releases, release groups, and recordings.
 * Uses the MusicBrainz schema https://musicbrainz.org/doc/MusicBrainz_Database/Schema
 * Code adpated from https://github.com/metabrainz/musicbrainz-server/tree/master/admin/sql
 * Uses PostgreSQL syntax.
*/

-- create artist tables/indexes

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
