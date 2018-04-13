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

import java.util.Arrays;
import java.util.LinkedHashMap;
/**
 * Contains basic functionality for how a query should be modeled.
 * Extending classes must implement methods to list the names and ID's of query results.
 * @author lkitaev
 */
public class BasicQuery {
  protected final String query;
  protected LinkedHashMap<String, String> results;
  /**
   * Number of results for the query.
   */
  public int len;

  protected BasicQuery() {
    query = "";
    results = null;
    len = 0;
  }

  protected BasicQuery(String query) {
    String replace = query.replaceAll("[;/?:@=&]", " ");
    this.query = replace;
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
   * @param results the results to set
   */
  public void setResults(LinkedHashMap<String, String> results) {
    this.results = results;
  }
  /**
   * @return the len
   */
  public int getLen() {
    return len;
  }
  /**
   * @param len the len to set
   */
  public void setLen(int len) {
    this.len = len;
  }
  
  /**
   * Gets the associated names of search results as a string array.
   * @return either the names or null
   */
  public String[] listNames() {
    String[] names = new String[0];
    names = len >= 1 ? Arrays.copyOf(results.values().toArray(names), len) : null;
    return names;
  }
  /**
   * Gets the associated ID's of search results as a string array.
   * @return either the ID's or null
   */
  public String[] listIds() {
    String[] ids = new String[0];
    ids = len >= 1 ? Arrays.copyOf(results.keySet().toArray(ids), len) : null;
    return ids;
  }
}
