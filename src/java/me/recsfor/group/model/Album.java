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
package me.recsfor.group.model;

import java.time.temporal.Temporal;
import java.util.UUID;

import me.recsfor.engine.search.sql.Queryable;

import static java.util.Objects.compare;

/**
 * Provides the data model for Album entities.
 * @author lkitaev
 */
public class Album extends AbstractModel implements Comparable<Album> {
	private String title;
	private Temporal firstRelease;

	/**
	 * Creates an unknown Album.
	 */
	public Album() {
		super();
		title = "Unknown";
	}
	
	/**
	 * Creates an Album with the specified attributes.
	 * @param id
	 * @param title
	 * @param firstRelease
	 */
	public Album(UUID id, String title, Temporal firstRelease) {
		super(id);
		this.title = title;
		this.firstRelease = firstRelease;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the firstRelease
	 */
	public Temporal getFirstRelease() {
		return firstRelease;
	}

	/**
	 * Compares two Albums by their first release date.
	 * If both of these values are null, or are equal, compares by title instead.
	 * @param o the Album to compare to
	 * @return an integer that is negative, positive, or zero according to the above criteria
	 */
	@Override
	public int compareTo(Album o) {
		int comp = 0;
		try {
			comp = compare(firstRelease, o.firstRelease, Queryable.compareTemporal());
		} catch (NullPointerException e) {
			if (firstRelease == null && o.firstRelease != null) return 1;
			if (firstRelease != null && o.firstRelease == null) return -1;
		}
		return comp != 0 ? comp : title.compareTo(o.title);
	}

	/**
	 * Determines a hash code for this instance based on its title and first release.
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstRelease == null) ? 0 : Queryable.hashTemporal(firstRelease));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Determines if this instance is equal to another object based on the title and first release.
	 * @param obj the object to determine equality with
	 * @return whether or not they're equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Album)) return false;
		Album other = (Album) obj;
		if (firstRelease == null) {
			if (other.firstRelease != null)
				return false;
		} else if (compare(firstRelease, other.firstRelease, Queryable.compareTemporal()) != 0)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
