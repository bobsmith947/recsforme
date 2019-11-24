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

import java.sql.SQLException;

import me.recsfor.group.model.AbstractModel;

/**
 * Defines the required <code>query()</code> method of Query classes.
 */
public interface Queryable {
	
	/**
	 * This method queries the recsforme database for data regarding a specific entity
	 * and returns the queried data as an <code>AbstractModel</code>.
	 * Overriding methods should specify a subclass of <code>AbstractModel</code>
	 * that is specific to the implementation.
	 * @return the underlying data model for the query
	 * @throws SQLException if any of the executed queries fail
	 */
	public abstract AbstractModel query() throws SQLException;
}
