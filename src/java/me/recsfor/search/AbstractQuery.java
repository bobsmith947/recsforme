/*
 * Copyright 2018 lkitaev.
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
 * Parent class for all queries.
 * @author lkitaev
 */
public abstract class AbstractQuery {
  protected String query;
  /**
   * @return the query
   */
  public String getQuery() {
    return query;
  }
  /**
   * @param query the query to set
   */
  public void setQuery(String query) {
    this.query = query;
  }
  /**
   * Compiles search results as a string array.
   * @return an array either containing the results or null
   */
  public abstract String[] printResults();
  /**
   * Checks if the current query is different from the last query.
   * @param newQuery the new query
   * @return true if the query has changed, false otherwise
   */
  public boolean changed(String newQuery) {
    if (query != null) {
      return newQuery.equals(query);
    } else {
      return false;
    }
  }
}
