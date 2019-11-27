-- insert artist groups
INSERT INTO groups (gid, type, name)
SELECT gid, 'artist', name FROM artist;

-- insert album groups
INSERT INTO groups (gid, type, name)
SELECT gid, 'album', name FROM release_group;
