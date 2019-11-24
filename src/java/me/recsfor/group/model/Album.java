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

/**
 * Provides the data model for Album entities.
 * @author lkitaev
 */
public class Album extends AbstractModel {
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

}
