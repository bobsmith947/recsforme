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

import java.util.UUID;

/**
 * 
 */
public class Song extends AbstractModel {
	private String title;
	private int position;

	/**
	 * Creates an unknown Song.
	 */
	public Song() {
		super();
		title = "Unknown";
		position = -1;
	}
	
	/**
	 * Creates a Song with the specified attributes.
	 * @param id
	 * @param title
	 * @param position
	 */
	public Song(UUID id, String title, int position) {
		super(id);
		this.title = title;
		this.position = position;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

}
