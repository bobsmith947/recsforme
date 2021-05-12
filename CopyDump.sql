/*
 * Copies data from a MusicBrainz database dump.
 * Downloads are available from: http://ftp.musicbrainz.org/pub/musicbrainz/data/
 * Complete database dump is under fullexport/
 * Sample database dump is under sample/
 * This script is for use with the psql command line utility.
 * Replace the cd command with wherever the dump is saved.
 */

\cd ../mbdump
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
