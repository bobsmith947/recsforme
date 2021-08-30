/*
 * Copies data from a sample MusicBrainz database dump.
 * Downloads are available from: http://ftp.musicbrainz.org/pub/musicbrainz/data/
 */

\cd
\! curl "ftp.musicbrainz.org/pub/musicbrainz/data/sample/LATEST" | curl -O "ftp.musicbrainz.org/pub/musicbrainz/data/sample/`cat`/mbdump-sample.tar.xz"
\! tar -xf mbdump-sample.tar.xz mbdump/artist mbdump/artist_credit mbdump/artist_credit_name mbdump/artist_type mbdump/gender mbdump/medium mbdump/release mbdump/release_coverart mbdump/release_group mbdump/release_group_meta mbdump/release_group_primary_type mbdump/release_group_secondary_type mbdump/release_group_secondary_type_join mbdump/track
\cd mbdump
\copy artist_type from 'artist_type'
\copy artist_credit from 'artist_credit'
\copy gender from 'gender'
\copy artist from 'artist'
\copy artist_credit_name from 'artist_credit_name'
\copy release_group_primary_type from 'release_group_primary_type'
\copy release_group_secondary_type from 'release_group_secondary_type'
\copy release_group from 'release_group'
\copy release_group_secondary_type_join from 'release_group_secondary_type_join'
\copy release_group_meta from 'release_group_meta'
\copy release from 'release'
\copy release_coverart from 'release_coverart'
\copy medium from 'medium'
\copy track from 'track'
