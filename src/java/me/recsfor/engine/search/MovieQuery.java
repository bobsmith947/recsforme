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

import com.omertron.omdbapi.*;
import com.omertron.omdbapi.model.*;
import com.omertron.omdbapi.tools.OmdbParameters;
import com.omertron.omdbapi.tools.Param;
import static com.omertron.omdbapi.emumerations.PlotType.FULL;
import java.util.Arrays;
import static org.apache.commons.lang3.text.WordUtils.capitalize;
/**
 * Uses the OMDb API (https://omdbapi.com) to gather <code>movie</code>, <code>series</code>, and <code>game</code> data for search results and group pages.
 * @author lkitaev
 */
public class MovieQuery extends BasicQuery {
  private OmdbVideoFull info;
  private final OmdbParameters params;
  private static final OmdbApi CLIENT = new OmdbApi(System.getenv("OMDB_KEY"));
  static final String CONTEXT = "MovieInfo?id=";

  protected MovieQuery() {
    super();
    info = null;
    params = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  protected MovieQuery(String query) {
    super(query);
    info = null;
    params = null;
    try {
      CLIENT.search(this.query).getResults().forEach(res -> {
        results.put(res.getImdbID(), res.getTitle() + " ("
                + res.getYear() + ")");
      });
    } catch (OMDBException | NullPointerException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param full whether to get full info or not
   */
  public MovieQuery(String id, boolean full) {
    super();
    if (!full) {
      info = null;
      params = null;
    } else {
      params = new OmdbParameters();
      params.add(Param.IMDB, id);
      params.add(Param.PLOT, FULL);
      try {
        info = CLIENT.getInfo(params);
      } catch (OMDBException | NullPointerException e) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        info = null;
      }
    }
  }
  
  /**
   * Gets the title of the movie.
   * @return the title
   */
  public String listTitle() {
    return info.getTitle();
  }
  /**
   * Gets the year of the movie.
   * @return the year
   */
  public String listYear() {
    return info.getYear();
  }
  /**
   * Gets the type (movie, series, or game) of the movie.
   * @return the type
   */
  public String listType() {
    String type = info.getType();
    type = capitalize(type); //give it title case because it gets returned lower case
    return type;
  }
  /**
   * Gets the plot synopsis of the movie, based on the specified plot type.
   * @return the plot
   */
  public String listPlot() {
    return info.getPlot();
  }
}
