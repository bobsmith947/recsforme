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
package me.recsfor.search;

/**
 * Interface for all queries.
 * @author lkitaev
 */
public interface GenericQuery {

  /**
   * @return the query
   */
  String getQuery();  
  
  /**
   * @param query the query to set
   */
  void setQuery(String query);

  /**
   * Compiles search results as a string array.
   * @return an array either containing the results or null
   */
  String[] printResults();
  
  /**
   * Checks if the current query is different from the last query.
   * @param newQuery the new query
   * @return true if the query has changed, false otherwise
   */
  boolean changed(String newQuery);
}
