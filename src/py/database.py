#!/usr/bin/python3

import psycopg2, os, uuid, random
from psycopg2.extras import execute_values
from bisect import bisect_left

class User:
	def __init__(self, userId, userName, userGroups):
		self.id = userId
		self.name = userName
		self.groups = userGroups
	
	def __str__(self):
		return f"{self.name} ({self.id})"

class Group:
	def __init__(self, gid, gtype, gname):
		self.id = uuid.UUID(gid)
		self.type = gtype
		self.name = gname
	
	def __str__(self):
		return f"{self.name} - {self.type} ({self.id})"

conn = psycopg2.connect(
	dbname=os.environ.get("PGDATABASE"),
	user=os.environ.get("PGUSER"),
	password=os.environ.get("PGPASSWORD"),
	host=os.environ.get("PGHOST"),
	port=os.environ.get("PGPORT")
)
conn.autocommit = True
cur = conn.cursor()

def close():
	cur.close()
	conn.close()

def getUsers():
	cur.execute("SELECT id, username FROM users ORDER BY id")
	return [User(*x, getUserGroups(x[0])) for x in cur.fetchall()]

def getUserGroups(userId):
	cur.execute("SELECT group_gid, liked FROM user_groups WHERE user_id = %s", (userId,))
	return {uuid.UUID(x[0]): x[1] for x in cur.fetchall()}

def getArtists():
	cur.execute("SELECT * FROM groups WHERE type = 'artist' ORDER BY gid")
	return [Group(*x) for x in cur.fetchall()]

def getAlbums():
	cur.execute("SELECT * FROM groups WHERE type = 'album' ORDER BY gid")
	return [Group(*x) for x in cur.fetchall()]

def createTestUsers(numUsers=100, numGroups=10):
	artists = getArtists()
	albums = getAlbums()
	random.seed()
	execute_values(cur, "INSERT INTO users VALUES %s", [(i, f"test{i}") for i in range(numUsers)])
	for i in range(numUsers):
		groups = random.sample(artists, numGroups // 2) + random.sample(albums, numGroups // 2)
		execute_values(cur, "INSERT INTO user_groups VALUES %s",
				[(i, str(x.id), bool(random.getrandbits(1))) for x in groups])

def updateUserRecs(users):
	for user in users:
		execute_values(cur, """INSERT INTO user_recommendations VALUES %s
				ON CONFLICT (user_id, group_gid) DO UPDATE
				SET score = EXCLUDED.score, time_updated = DEFAULT""",
				[(user.id, str(x.id), y) for x, y in user.recs.items()])

def indexOf(a, x):
	i = bisect_left(a, x)
	if i != len(a) and a[i] == x:
		return i
	return -1
