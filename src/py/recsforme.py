#!/usr/bin/python3

from database import *
import numpy as np
from fancyimpute import SimilarityWeightedAveraging

users = getUsers()
artists = getArtists()
albums = getAlbums()

artistLikes = np.full((len(users), len(artists)), np.nan, np.half)
albumLikes = np.full((len(users), len(albums)), np.nan, np.half)

# fill in album likes/dislikes
for i in range(len(users)):
	for j in range(len(albums)):
		liked = users[i].groups.get(albums[j].id)
		if liked is not None:
			if liked is True:
				albumLikes[i][j] = 1.0
			else:
				albumLikes[i][j] = -1.0

# create missing mask
albumMask = np.ma.make_mask(~np.isnan(albumLikes))

# fill in missing entries
swa = SimilarityWeightedAveraging(verbose=True)
albumRecs = np.ma.MaskedArray(swa.fit_transform(albumLikes),
				albumMask, fill_value=np.NINF)

# find top picks
numPicks = 10
for i in range(len(users)):
	users[i].recs = {}
	topAlbums = albumRecs[i].argsort(endwith=False)[-numPicks:]
	for j in topAlbums:
		if albumRecs[i][j] > 0:
			users[i].recs[albums[j]] = albumRecs[i][j]

updateUserRecs(users)

