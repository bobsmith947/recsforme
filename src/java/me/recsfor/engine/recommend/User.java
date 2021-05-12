/*
 * Copyright 2018 Lucas Kitaev.
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
package me.recsfor.engine.recommend;

import java.io.Serializable;
import me.recsfor.app.ListData;

/**
 * Represents any user other than the one using the application.
 * @author lkitaev
 */
public class User implements Serializable {

	private static final long serialVersionUID = -4997310154302356037L;
	private String name;
	private ListData likes;
	private ListData dislikes;

	public User(String name) {
		this.name = name;
		likes = new ListData();
		dislikes = new ListData();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the likes
	 */
	public ListData getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(ListData likes) {
		this.likes = likes;
	}

	/**
	 * @return the dislikes
	 */
	public ListData getDislikes() {
		return dislikes;
	}

	/**
	 * @param dislikes the dislikes to set
	 */
	public void setDislikes(ListData dislikes) {
		this.dislikes = dislikes;
	}
}
