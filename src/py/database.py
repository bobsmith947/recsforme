#!/usr/bin/python3

import psycopg2, os, uuid, random

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

def getUsers():
	cur.execute("SELECT id, username FROM users")
	return [User(*x, getUserGroups(x[0])) for x in cur.fetchall()]

def getUserGroups(userId):
	cur.execute("SELECT group_gid, liked FROM user_groups WHERE user_id = %s", (userId,))
	return {uuid.UUID(x[0]): x[1] for x in cur.fetchall()}

def getArtists():
	cur.execute("SELECT * FROM groups WHERE type LIKE 'artist'")
	return [Group(*x) for x in cur.fetchall()]

def getAlbums():
	cur.execute("SELECT * FROM groups WHERE type LIKE 'album'")
	return [Group(*x) for x in cur.fetchall()]

def createTestUsers(numUsers=100, numGroups=10):
	artists = getArtists()
	albums = getAlbums()
	random.seed()
	for i in range(numUsers):
		cur.execute("INSERT INTO users VALUES (%s, %s, '', '')",
			(i, f"test{i}"))
		groups = random.sample(artists, numGroups // 2) + random.sample(albums, numGroups // 2)
		for group in groups:
			cur.execute("INSERT INTO user_groups VALUES (%s, %s, %s)",
				(i, str(group.id), bool(random.getrandbits(1))))

def updateUserRecs(users):
	for user in users:
		for group, score in user.recs.items():
			cur.execute("""INSERT INTO user_recommendations VALUES (%s, %s, %s)
					ON CONFLICT (user_id, group_gid) DO UPDATE
					SET score = %s, time_updated = DEFAULT""",
					(user.id, str(group.id), float(score), float(score)))

