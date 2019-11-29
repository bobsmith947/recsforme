/*
 * Copyright 2019 Lucas Kitaev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.recsfor.engine.search.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import me.recsfor.group.model.Album;
import me.recsfor.group.model.Song;

/**
 * Queries the recsforme database for data related to Album entities.
 * @author lkitaev
 */
public class AlbumQuerySQL implements Queryable {
	private Connection con;
	private int id;
	private UUID gid;

	/**
	 * @param id the uuid of the release group
	 * @param db the database to query
	 * @throws SQLException if the query fails or no rows are found
	 */
	public AlbumQuerySQL(String id, DataSource db) throws SQLException {
		gid = UUID.fromString(id);
		con = db.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT id FROM release_group WHERE gid = ?");
		ps.setObject(1, gid);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			this.id = rs.getInt(1);
		else
			throw new SQLException(id + " was not found in the database");
	}

	/**
	 * @return the Album generated by this query
	 * @throws SQLException if a contained query fails
	 */
	@Override
	public Album query() throws SQLException {

		return new Album(gid, queryTitle(), queryFirstRelease(), queryPrimaryType(), querySecondaryType(), queryTrackList());	
	}

	/**
	 * @return the title of the album
	 * @throws SQLException if the query fails
	 */
	public String queryTitle() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT name FROM release_group WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}

	/**
	 * Determines the <code>Temporal</code> value of the album's first release.
	 * @return the point in time at which the album first released
	 * @throws SQLException if the query fails
	 */
	public Temporal queryFirstRelease() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT first_release_date_year, first_release_date_month,"
				+ " first_release_date_day FROM release_group_meta WHERE release_group_meta.id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return Queryable.toTemporal(rs.getInt(1), rs.getInt(2), rs.getInt(3));
	}

	public String queryPrimaryType() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT name FROM release_group_primary_type AS rgpt, release_group AS rg" + ""
				+ "WHERE ? release.release_group = rg.id AND rg.type = rgpt.id");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}

	public String querySecondaryType() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT rgst.name FROM release_group_secondary_type AS rgst, release_group_secondary_type_join AS rgstjoin, release" +
				"WHERE ? = rgstjoin.release_group AND rgst.id = rgstjoin.secondary_type");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}

	public List<Song> queryTrackList() throws SQLException {
		List<Song> trackList = new LinkedList<>();
		PreparedStatement ps =  con.prepareStatement("SELECT track.id, track.title, track.position FROM track, medium, release" + 
				"WHERE release.release_group = ? AND medium.release = release.id AND track.id = medium.id ORDER BY position");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			trackList.add(new Song(gid, rs.getString(1), rs.getInt(2)));
		}
		return trackList;
	}

}
