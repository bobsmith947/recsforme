#!/usr/bin/python3

from sys import argv
from database import *
import numpy as np
from fancyimpute import SimilarityWeightedAveraging

# the number of recommendations for each type can optionally be specified
numPicks = 10
if len(argv) > 1:
	numPicks = int(argv[1])

users = getUsers()
swa = SimilarityWeightedAveraging(verbose=True)

def generateRecs(groupType):
	if groupType == "artist":
		ids = [x.id for x in getArtists()]
	elif groupType == "album":
		ids = [x.id for x in getAlbums()]
	likes = np.full((len(users), len(ids)), np.nan, np.half)

# fill in likes/dislikes for each user
	for i in range(len(users)):
		for gid, liked in users[i].groups.items():
			j = indexOf(ids, gid)
			if j != -1:
				if liked is True:
					likes.itemset((i, j), 1.0)
				else:
					likes.itemset((i, j), -1.0)

# fill in missing entries using matrix completion model
# mask out entries that have already been filled in
# delete likes after running model to free memory
	recs = np.ma.MaskedArray(swa.fit_transform(likes), ~np.isnan(likes))
	del likes

# find top picks and add them to each user's recommendations
	for i in range(len(users)):
		top = recs[i].argsort(endwith=False)[-numPicks:].tolist()
		for j in top:
			score = recs.item((i, j))
			if score > 0:
				users[i].recs[ids[j]] = score

# free memory
	ids.clear()
	del recs

generateRecs("artist")
generateRecs("album")
updateUserRecs(users)
close()
