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
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
/**
 * Uses the OMDb API (https://omdbapi.com) to gather data for movie/TV show search result and group pages.
 * @author lkitaev
 */
public class MovieQuery extends AbstractQuery {
  private OmdbVideoFull info;
  private static final OmdbApi CLIENT = new OmdbApi("357b2b79"); //please don't use this
  private final OmdbParameters PARAMS;

  public MovieQuery() {
    super();
    info = null;
    PARAMS = null;
  }

  public MovieQuery(String query) {
    super(query);
    info = null;
    PARAMS = null;
    try {
      CLIENT.search(query).getResults().forEach(res -> {
        results.put(res.getImdbID(), res.getTitle());
      });
      len = results.size();
    } catch (OMDBException e) {
      this.query = e.getMessage();
      results = null;
      len = 0;
    }
  }
  
  public MovieQuery(String id, boolean info) {
    super();
    if (!info) {
      this.info = null;
      PARAMS = null;
    } else {
      PARAMS = new OmdbParameters();
      PARAMS.add(Param.IMDB, id);
      PARAMS.add(Param.PLOT, FULL);
      try {
        this.info = CLIENT.getInfo(PARAMS);
        query = this.info.getTitle();
      } catch (OMDBException e) {
        query = e.getMessage();
        this.info = null;
      }
    }
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
  public String[] listNames() {
    String[] res = new String[0];
    res = len >= 1 ? Arrays.copyOf(results.values().toArray(res), len) : null;
    return res;
  }
  
  @Override
  public String[] listIds() {
    String[] ids = new String[0];
    ids = len >= 1 ? Arrays.copyOf(results.keySet().toArray(ids), len) : null;
    return ids;
  }
  /**
   * Gets the year using the generated info.
   * @return the year
   */
  public String listYear() {
    return getInfo().getYear();
  }
  /**
   * Gets the type (movie or series) using the generated info.
   * @return the type
   */
  public String listType() {
    String type = getInfo().getType();
    type = WordUtils.capitalize(type); //give it title case because it gets returned lower case
    return type;
  }
  /**
   * Gets the short plot synopsis using the generated info.
   * @return the plot 
   */
  public String listPlot() {
    return getInfo().getPlot();
  }
}
