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

import com.omertron.omdbapi.*;
import com.omertron.omdbapi.model.*;
import com.omertron.omdbapi.tools.OmdbParameters;
import com.omertron.omdbapi.tools.Param;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
/**
 * Uses the OMDb API (https://omdbapi.com) to gather data for movie/TV show search result and group pages.
 * @author lkitaev
 */
public class MovieQuery extends AbstractQuery {
  private SearchResults results;
  private static final OmdbApi CLIENT = new OmdbApi("357b2b79"); //please don't use this
  private final OmdbParameters PARAMS;

  public MovieQuery() {
    query = null;
    results = null;
    PARAMS = null;
  }

  public MovieQuery(String query) {
    PARAMS = new OmdbParameters();
    //title = WordUtils.capitalize(title);
    PARAMS.add(Param.TITLE, query);
    this.query = query;
    results = null;
  }
  /**
   * @return the results
   */
  public SearchResults getResults() {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(SearchResults results) {
    this.results = results;
  }
  /**
   * Performs a search using the defined client and instance query.
   */
  @Override
  protected void search() {
    if (query == null || query.equals("")) {
      results = null;
    } else {
      try {
        results = CLIENT.search(query);
      } catch (OMDBException e) {
        results = null;
        query = e.getMessage();
      }
    }
  }

  @Override
  public String[] printResults() {
    String[] res;
    search();
    if (results != null && results.getTotalResults() >= 1) {
      List<OmdbVideoBasic> list = results.getResults();
      res = new String[list.size()];
      for (int i = 0; i < res.length; i++) {
        OmdbVideoBasic o = list.get(i);
        res[i] = o.getTitle();
      }
      return res;
    } else {
      res = null;
      return res;
    }
  }
  /**
   * Gets the year using the defined client and instance query.
   * @return the year
   */
  public String printYear() {
    String year;
    try {
      year = CLIENT.getInfo(PARAMS).getYear();
    } catch (OMDBException e) {
      year = e.getMessage();
    }
    return year;
  }
  /**
   * Gets the type (movie or series) using the defined client and instance query.
   * @return the type
   */
  public String printType() {
    String type;
    try {
      type = CLIENT.getInfo(PARAMS).getType();
      type = WordUtils.capitalize(type); //give it title case because it gets returned lower case
    } catch (OMDBException e) {
      type = e.getMessage();
    }
    return type;
  }
  /**
   * Gets the short plot synopsis using the defined client and instance query.
   * @return the plot 
   */
  public String printPlot() {
    String plot;
    try {
      plot = CLIENT.getInfo(PARAMS).getPlot();
    } catch (OMDBException e) {
      plot = e.getMessage();
    }
    return plot;
  }
  /**
   * Gets the corresponding IMDb ID using the defined client and instance query.
   * @return the id
   */
  public String printId() {
    String id;
    try {
      id = CLIENT.getInfo(PARAMS).getImdbID();
    } catch (OMDBException e) {
      id = e.getMessage();
    }
    return id;
  }
}
