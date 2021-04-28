#!/usr/bin/python3

from database import *
import numpy as np
from fancyimpute import SimilarityWeightedAveraging

users = getUsers()
artists = getArtists()
albums = getAlbums()

artistLikes = np.full((len(users), len(artists)), np.nan, np.half)
albumLikes = np.full((len(users), len(albums)), np.nan, np.half)

# fill in likes/dislikes for each user
for i in range(len(users)):
	for j in range(len(albums)):
		liked = users[i].groups.get(albums[j].id)
		if liked is not None:
			if liked is True:
				albumLikes[i][j] = 1.0
			else:
				albumLikes[i][j] = -1.0
	for j in range(len(artists)):
		liked = users[i].groups.get(artists[j].id)
		if liked is not None:
			if liked is True:
				artistLikes[i][j] = 1.0
			else:
				artistLikes[i][j] = -1.0

# create mask for likes/dislikes
albumMask = np.ma.make_mask(~np.isnan(albumLikes))
artistMask = np.ma.make_mask(~np.isnan(artistLikes))

# fill in missing entries
# mask out the entries that have already been filled in
swa = SimilarityWeightedAveraging(verbose=True)
albumRecs = np.ma.MaskedArray(swa.fit_transform(albumLikes),
				albumMask, fill_value=np.NINF)
artistRecs = np.ma.MaskedArray(swa.fit_transform(artistLikes),
				artistMask, fill_value=np.NINF)

# find top picks and add them to each user's recommendations
numPicks = 10
for i in range(len(users)):
	users[i].recs = {}
	topAlbums = albumRecs[i].argsort(endwith=False)[-numPicks:]
	topArtists = artistRecs[i].argsort(endwith=False)[-numPicks:]
	for j in topAlbums:
		if albumRecs[i][j] > 0:
			users[i].recs[albums[j]] = albumRecs[i][j]
	for j in topArtists:
		if artistRecs[i][j] > 0:
			users[i].recs[artists[j]] = artistRecs[i][j]

updateUserRecs(users)

