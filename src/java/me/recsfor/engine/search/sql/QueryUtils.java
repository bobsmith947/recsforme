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

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.Temporal;
import java.util.Comparator;

/**
 * Provides utility methods related to database queries.
 */
public class QueryUtils {

	private QueryUtils() {}
	
	/**
	 * Creates a <code>Temporal</code> value based on the given parameters.
	 * Will be <code>LocalDate</code> if year, month, and day are all known.
	 * Will be <code>YearMonth</code> if the year and month are both known.
	 * Will be <code>Year</code> if only the year is known.
	 * Will be <code>null</code> if none of the above values are known.
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @return the corresponding Temporal
	 */
	public static Temporal toTemporal(int year, int month, int day) {
		if (year != 0) {
			if (month != 0) {
				if (day != 0) {
					return LocalDate.of(year, month, day);
				}
				return YearMonth.of(year, month);
			}
			return Year.of(year);
		}
		return null;
	}

	/**
	 * Generates a hash code based on the type of <code>Temporal</code>
	 * @param t the object to hash
	 * @return the hash code
	 */
	public static int hashTemporal(Temporal t) {
		if (t instanceof LocalDate) return ((LocalDate) t).hashCode();
		if (t instanceof YearMonth) return ((YearMonth) t).hashCode();
		if (t instanceof Year) return ((Year) t).hashCode();
		throw new UnsupportedOperationException();
	}

	/**
	 * Generates a <code>Comparator</code> for two <code>Temporal</code> objects
	 * @return the Comparator
	 */
	public static Comparator<Temporal> compareTemporal() {
		return (Temporal a, Temporal b) -> {
			if (a == null || b == null) throw new NullPointerException();
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
		};
	}
}
