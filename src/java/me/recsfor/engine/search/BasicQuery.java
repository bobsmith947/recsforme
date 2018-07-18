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
package me.recsfor.engine.search;

import java.util.LinkedHashMap;
import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
/**
 * Contains basic functionality for query classes.
 * @author lkitaev
 */
public class BasicQuery {
  protected final String query;
  /**
   * The results for the query.
   * Keys contain the group ID's.
   * Values contain the group names.
   */
  protected final LinkedHashMap<String, String> results;

  protected BasicQuery() {
    query = "";
    results = null;
  }

  protected BasicQuery(String query) {
    this.query = query;
    results = new LinkedHashMap<>();
  }
  
  /**
   * @return the query
   */
  public String getQuery() {
    return query;
  }
  /**
   * @return the results
   */
  public LinkedHashMap<String, String> getResults() {
    return results;
  }
  
  /**
   * Gets the associated names of search results as a string array.
   * @return either the names or null
   */
  public String[] listNames() {
    String[] names = new String[0];
    if (!results.isEmpty())
      return results.values().toArray(names);
    return names;
  }
  /**
   * Gets the associated ID's of search results as a string array.
   * @return either the ID's or null
   */
  public String[] listIds() {
    String[] ids = new String[0];
    if (!results.isEmpty())
      return results.keySet().toArray(ids);
    return ids;
  }
  /**
   * Determines if the contents of this query are effectively different from another query.
   * @param query the query to check against
   * @return true if the two are different, false otherwise
   */
  public final boolean different(String query) {
    //matches punctuation and articles
    String regex = "(([:\\-.,/\\\\])|(\\bthe\\b)|(\\ba\\b|\\ban\\b))+";
    String oldQuery = this.query.toLowerCase().replaceAll(regex, " ");
    String newQuery = query.toLowerCase().replaceAll(regex, " ");
    oldQuery = normalizeSpace(oldQuery);
    newQuery = normalizeSpace(newQuery);
    //check if the modified queries are still the same
    return !oldQuery.equals(newQuery);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    else if (obj == this)
      return true;
    else if (!(obj instanceof BasicQuery))
      return false;
    else
      if (!this.different(((BasicQuery) (obj)).query))
        return obj.hashCode() == this.hashCode();
      else
        return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + hash(this.query, this.results);
    return hash;
  }
}
