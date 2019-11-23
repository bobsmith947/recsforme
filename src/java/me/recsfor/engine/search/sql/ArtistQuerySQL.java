package me.recsfor.engine.search.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.temporal.Temporal;
import static java.time.LocalDate.of;
import static java.time.YearMonth.of;
import static java.time.Year.of;

import javax.sql.DataSource;

/**
 * Queries the recsforme database for data related to Artist entities.
 * @author lkitaev
 */
public class ArtistQuerySQL {
	private Connection con;
	private int id;

	/**
	 * Create a new instance for querying data about a specific artist.
	 * @param id the uuid of the artist
	 * @param db the database to query
	 * @throws SQLException if the query fails or no rows are found
	 */
	public ArtistQuerySQL(String id, DataSource db) throws SQLException {
		con = db.getConnection();
		// TODO find some way without casting to UUID directly
		PreparedStatement ps = con.prepareStatement("SELECT id FROM artist WHERE gid = ?::UUID");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			this.id = rs.getInt(1);
		} else {
			throw new SQLException(id + " was not found in the database.");
		}
	}
	
	/**
	 * @return the name of this artist
	 * @throws SQLException if the query fails
	 */
	public String getName() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT name FROM artist WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}
	
	/**
	 * @return the sort name of this artist
	 * @throws SQLException if the query fails
	 */
	public String getSortName() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT sort_name FROM artist WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}
	
	/**
	 * @return the type of this artist
	 * @throws SQLException if the query fails
	 */
	public String getType() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT name FROM artist_type"
				+ " WHERE id = (SELECT type FROM artist WHERE id = ?)");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}
	
	/**
	 * @return the gender of this artist
	 * @throws SQLException if the query fails
	 */
	public String getGender() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT name FROM gender WHERE id ="
				+ " (SELECT gender FROM artist WHERE id = ?)");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "";
	}
	
	/**
	 * @return the comment about this artist
	 * @throws SQLException if the query fails
	 */
	public String getComment() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT comment FROM artist WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}
	
	/**
	 * Determines the <code>Temporal</code> value of the Artist beginning.
	 * Will be <code>LocalDate</code> if year, month, and day are all known.
	 * Will be <code>YearMonth</code> if the year and month are both known.
	 * Will be <code>Year</code> if only the year is known.
	 * Will be <code>null</code> if none of the above values are known.
	 * @return the point in time at which this artist began
	 * @throws SQLException if the query fails
	 */
	public Temporal getBegin() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT begin_date_year, begin_date_month, begin_date_day"
				+ " FROM artist WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int year = rs.getInt(1);
		int month = rs.getInt(2);
		int day = rs.getInt(3);
		if (year != 0) {
			if (month != 0) {
				if (day != 0) {
					return of(year, month, day);
				}
				return of(year, month);
			}
			return of(year);
		}
		return null;
	}
	
	/**
	 * Determines the <code>Temporal</code> value of the Artist ending.
	 * Will be <code>LocalDate</code> if year, month, and day are all known.
	 * Will be <code>YearMonth</code> if the year and month are both known.
	 * Will be <code>Year</code> if only the year is known.
	 * Will be <code>null</code> if none of the above values are known.
	 * @return the point in time at which this artist ended
	 * @throws SQLException if the query fails
	 */
	public Temporal getEnd() throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT end_date_year, end_date_month, end_date_day"
				+ " FROM artist WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int year = rs.getInt(1);
		int month = rs.getInt(2);
		int day = rs.getInt(3);
		if (year != 0) {
			if (month != 0) {
				if (day != 0) {
					return of(year, month, day);
				}
				return of(year, month);
			}
			return of(year);
		}
		return null;
	}

}
