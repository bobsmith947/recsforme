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
 * An abstract definition for group models.
 * All groups are associated with a universally unique identifier (UUID).
 * @author lkitaev
 */
public abstract class AbstractModel {
	private UUID id;

	/**
	 * Creates a model with a random UUID.
	 */
	protected AbstractModel() {
		id = UUID.randomUUID();
	}
	
	/**
	 * Creates a model with the specified UUID.
	 * @param id
	 */
	protected AbstractModel(UUID id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public final UUID getId() {
		return id;
	}

}
