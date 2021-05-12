#!/usr/bin/python3

from database import *
import numpy as np
from fancyimpute import SimilarityWeightedAveraging

users = getUsers()
artists = getArtists()
artistIds = [x.id for x in artists]
albums = getAlbums()
albumIds = [x.id for x in albums]

artistLikes = np.full((len(users), len(artists)), np.nan, np.half)
albumLikes = np.full((len(users), len(albums)), np.nan, np.half)

# fill in likes/dislikes for each user
for i in range(len(users)):
	for gid, liked in users[i].groups.items():
		albumIndex = indexOf(albumIds, gid)
		if albumIndex != -1:
			j = albumIndex
			if liked is True:
				albumLikes.itemset((i, j), 1.0)
			else:
				albumLikes.itemset((i, j), -1.0)
			continue
		artistIndex = indexOf(artistIds, gid)
		if artistIndex != -1:
			j = artistIndex
			if liked is True:
				artistLikes.itemset((i, j), 1.0)
			else:
				artistLikes.itemset((i, j), -1.0)

# fill in missing entries using matrix completion model
# mask out entries that have already been filled in
# delete likes after running model to free memory
swa = SimilarityWeightedAveraging(verbose=True)
albumRecs = np.ma.MaskedArray(swa.fit_transform(albumLikes),~np.isnan(albumLikes))
del albumLikes
artistRecs = np.ma.MaskedArray(swa.fit_transform(artistLikes),~np.isnan(artistLikes))
del artistLikes

# find top picks and add them to each user's recommendations
numPicks = 10
for i in range(len(users)):
	users[i].recs = {}
	topAlbums = albumRecs[i].argsort(endwith=False)[-numPicks:].tolist()
	topArtists = artistRecs[i].argsort(endwith=False)[-numPicks:].tolist()
	for j in topAlbums:
		score = albumRecs.item((i, j))
		if score > 0:
			users[i].recs[albums[j]] = score
	for j in topArtists:
		score = artistRecs.item((i, j))
		if score > 0:
			users[i].recs[artists[j]] = score

del albumRecs
del artistRecs
updateUserRecs(users)
close()
