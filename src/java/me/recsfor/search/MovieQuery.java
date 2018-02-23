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
//import static com.omertron.omdbapi.emumerations.PlotType.SHORT;
import static com.omertron.omdbapi.emumerations.PlotType.FULL;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
/**
 * Uses the OMDb API (https://omdbapi.com) to gather data for movie/TV show search result and group pages.
 * @author lkitaev
 */
public class MovieQuery extends AbstractQuery {
  private SearchResults results;
  private OmdbVideoFull info;
  private static final OmdbApi CLIENT = new OmdbApi("357b2b79"); //please don't use this
  private final OmdbParameters PARAMS;

  public MovieQuery() {
    query = "";
    results = null;
    info = null;
    PARAMS = null;
  }

  public MovieQuery(String query) {
    this.query = query;
    info = null;
    PARAMS = null;
    try {
      results = CLIENT.search(query);
    } catch (OMDBException e) {
      this.query = e.getMessage();
      results = null;
    }
  }
  
  public MovieQuery(String query, boolean info) {
    this.query = query;
    if (!info) {
      this.info = null;
      PARAMS = null;
    } else {
      PARAMS = new OmdbParameters();
      PARAMS.add(Param.TITLE, query);
      PARAMS.add(Param.PLOT, FULL);
      try {
        this.info = CLIENT.getInfo(PARAMS);
      } catch (OMDBException e) {
        this.query = e.getMessage();
        this.info = null;
      }
    }
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
   * @return the info
   */
  public OmdbVideoFull getInfo() {
    return info;
  }
  /**
   * @param info the info to set
   */
  public void setInfo(OmdbVideoFull info) {
    this.info = info;
  }

  @Override
  public String[] printResults() {
    String[] res;
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
    return getInfo().getYear();
  }
  /**
   * Gets the type (movie or series) using the defined client and instance query.
   * @return the type
   */
  public String printType() {
    String type = getInfo().getType();
    type = WordUtils.capitalize(type); //give it title case because it gets returned lower case
    return type;
  }
  /**
   * Gets the short plot synopsis using the defined client and instance query.
   * @return the plot 
   */
  public String printPlot() {
    return getInfo().getPlot();
  }
  /**
   * Gets the corresponding IMDb ID using the defined client and instance query.
   * @return the id
   */
  public String printId() {
    return getInfo().getImdbID();
  }
}
