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
CREATE TABLE IF NOT EXISTS release_group_primary_type (
	id                  INTEGER PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    parent              INTEGER, REFERENCES release_group_primary_type (id),
    child_order         INTEGER NOT NULL DEFAULT 0,
    description         TEXT,
    gid                 uuid NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS release_group_primary_type_idx_gid ON release_group_primary_type (gid);

CREATE TABLE IF NOT EXISTS release_group (
	id                  INTEGER PRIMARY KEY,
    gid                 UUID NOT NULL,
    name                VARCHAR NOT NULL,
    artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
    type                INTEGER REFERENCES release_group_primary_type (id),
    comment             VARCHAR(255) NOT NULL DEFAULT '',
    edits_pending       INTEGER NOT NULL DEFAULT 0 CHECK (edits_pending >= 0),
    last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS release_group_idx_gid ON release_group (gid);

CREATE TABLE IF NOT EXISTS release (
	id                  INTEGER PRIMARY KEY,
    gid                 UUID NOT NULL,
    name                VARCHAR NOT NULL,
    artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
    release_group       INTEGER NOT NULL REFERENCES release_group (id),
    barcode             VARCHAR(255),
    comment             VARCHAR(255) NOT NULL DEFAULT '',
    edits_pending       INTEGER NOT NULL DEFAULT 0 CHECK (edits_pending >= 0),
    quality             SMALLINT NOT NULL DEFAULT -1,
    last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS release_idx_gid ON release (gid);

-- create medium/track/recording tables
CREATE TABLE IF NOT EXISTS medium_format (
	id                  INTEGER PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    parent              INTEGER REFERENCES medium_format (id),
    child_order         INTEGER NOT NULL DEFAULT 0,
    year                SMALLINT,
    has_discids         BOOLEAN NOT NULL DEFAULT FALSE,
    description         TEXT,
    gid                 uuid NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS medium_format_idx_gid ON medium_format (gid);

CREATE TABLE IF NOT EXISTS medium (
	id                  INTEGER PRIMARY KEY,
    release             INTEGER NOT NULL REFERENCES release (id),
    position            INTEGER NOT NULL,
    format              INTEGER REFERENCES medium_format (id),
    name                VARCHAR NOT NULL DEFAULT '',
    edits_pending       INTEGER NOT NULL DEFAULT 0 CHECK (edits_pending >= 0),
    last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    track_count         INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS recording (
	id                  INTEGER PRIMARY KEY,
    gid                 UUID NOT NULL,
    name                VARCHAR NOT NULL,
    artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
    length              INTEGER CHECK (length IS NULL OR length > 0),
    comment             VARCHAR(255) NOT NULL DEFAULT '',
    edits_pending       INTEGER NOT NULL DEFAULT 0 CHECK (edits_pending >= 0),
    last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    video               BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX IF NOT EXISTS recording_idx_gid ON recording (gid);

CREATE TABLE IF NOT EXISTS track (
	id                  INTEGER PRIMARY KEY,
    gid                 UUID NOT NULL,
    recording           INTEGER NOT NULL REFERENCES recording (id),
    medium              INTEGER NOT NULL REFERENCES medium (id),
    position            INTEGER NOT NULL,
    number              TEXT NOT NULL,
    name                VARCHAR NOT NULL,
    artist_credit       INTEGER NOT NULL REFERENCES artist_credit (id),
    length              INTEGER CHECK (length IS NULL OR length > 0),
    edits_pending       INTEGER NOT NULL DEFAULT 0 CHECK (edits_pending >= 0),
    last_updated        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    is_data_track       BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX IF NOT EXISTS track_idx_gid ON track (gid);

