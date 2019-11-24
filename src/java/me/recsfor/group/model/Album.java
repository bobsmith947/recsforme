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

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.Temporal;
import java.util.UUID;
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
	 */
	@Override
	public int compareTo(Album o) {
		if (this.firstRelease == null && o.firstRelease != null)
			return 1;
		if (this.firstRelease != null && o.firstRelease == null)
			return -1;
		if (this.firstRelease == null && o.firstRelease == null)
			return this.title.compareTo(o.title);
		// TODO this is awful
		return compare(this.firstRelease, o.firstRelease, (Temporal a, Temporal b) -> {
			if (a instanceof LocalDate) {
				LocalDate ld = (LocalDate) a;
				if (b instanceof LocalDate) return ld.compareTo((LocalDate) b);
				if (b instanceof YearMonth) return YearMonth.of(ld.getYear(), ld.getMonth())
						.compareTo((YearMonth) b);
				if (b instanceof Year) return Year.of(ld.getYear()).compareTo((Year) b);
			}
			if (a instanceof YearMonth) {
				YearMonth ym = (YearMonth) a;
				if (b instanceof LocalDate) return ym.compareTo(YearMonth
						.of(((LocalDate) b).getYear(), ((LocalDate) b).getMonth()));
				if (b instanceof YearMonth) return ym.compareTo((YearMonth) b);
				if (b instanceof Year) return Year.of(ym.getYear()).compareTo((Year) b);
			}
			if (a instanceof Year) {
				Year y = (Year) a;
				if (b instanceof LocalDate) return y.compareTo(Year.of(((LocalDate) b).getYear()));
				if (b instanceof YearMonth) return y.compareTo(Year.of(((YearMonth) b).getYear()));
				if (b instanceof Year) return y.compareTo((Year) b);
			}
			throw new UnsupportedOperationException();
		});
	}

}
