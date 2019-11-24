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
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.UUID;

/**
 * Provides the data model for Artist entites.
 * @author lkitaev
 */
public class Artist extends AbstractModel {
	private String name, sortName, type, gender, comment;
	private Temporal begin, end;
	private List<Album> discog;

	/**
	 * Creates an unknown Artist
	 */
	public Artist() {
		super();
		name = "Unknown";
		sortName = "Unknown";
		type = "Other";
		gender = "Other";
		comment = null;
		begin = null;
		end = null;
		discog = emptyList();
	}
	
	/**
	 * Creates an Artist with the specified attributes
	 * @parm id
	 * @param name
	 * @param sortName
	 * @param type
	 * @param gender
	 * @param comment
	 * @param begin
	 * @param end
	 * @param discog
	 */
	public Artist(UUID id, String name, String sortName, String type, String gender, String comment,
			Temporal begin, Temporal end, List<Album> discog) {
		super(id);
		this.name = name;
		this.sortName = sortName;
		this.type = type;
		this.gender = gender;
		this.comment = comment;
		this.begin = begin;
		this.end = end;
		this.discog = discog;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the begin
	 */
	public Temporal getBegin() {
		return begin;
	}

	/**
	 * @return the end
	 */
	public Temporal getEnd() {
		return end;
	}

	/**
	 * @return the discography
	 */
	public List<Album> getDiscog() {
		return discog;
	}

}
