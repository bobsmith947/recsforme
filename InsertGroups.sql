-- insert artist groups
INSERT INTO groups (gid, type, name)
SELECT gid, 'artist', name || ' (' || sort_name || ')' FROM artist;

-- insert album groups
INSERT INTO groups (gid, type, name)
SELECT gid, 'album', name || ' - ' || 
(SELECT artist_credit.name FROM artist_credit 
WHERE artist_credit.id = release_group.artist_credit) 
FROM release_group;
