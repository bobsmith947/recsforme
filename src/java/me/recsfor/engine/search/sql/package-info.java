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

/**
 * This package is used by servlets to query the recsforme database for information about the groups for which
 * they are generating a page for.
 * <br>
 * Classes in this package do not provide the same functionality as those in the
 * <code>me.recsfor.engine.search</code> package do. The <code>me.recsfor.engine.search.sql</code> package does not
 * provide interfaces for searching the database for a user query, but rather provides interfaces for querying
 * data about a specific entity for which the ID is already known via search results.
 * <br>
 * The public interface for classes in this package is a single <code>static</code> method
 * <code>query(id)</code> for which the underlying entity with the specified ID will be generated,
 * as specified in the <code>me.recsfor.group.model</code> package.
 * Query objects are not instantiated directly, and thus the constructor for each class must not be public.
 */

package me.recsfor.engine.search.sql;