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
//import static com.omertron.omdbapi.emumerations.PlotType.SHORT;
//import static com.omertron.omdbapi.emumerations.PlotType.FULL;
import com.omertron.omdbapi.emumerations.PlotType;
import java.util.Arrays;
import static org.apache.commons.lang3.text.WordUtils.capitalize;
/**
 * Uses the OMDb API (https://omdbapi.com) to gather data for movie/TV show search result and group pages.
 * @author lkitaev
 */
public class MovieQuery extends AbstractQuery {
  private OmdbVideoFull info;
  private final OmdbParameters PARAMS;
  private static final OmdbApi CLIENT = new OmdbApi("357b2b79"); //please don't use this
  protected static final String CONTEXT = "MovieInfo?id=";

  // <editor-fold desc="Constructors.">
  /**
   * Default constructor for if you didn't actually want to query anything.
   */
  public MovieQuery() {
    super();
    info = null;
    PARAMS = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public MovieQuery(String query) {
    super(query);
    info = null;
    PARAMS = null;
    try {
      CLIENT.search(query).getResults().forEach(res ->
              results.put(res.getImdbID(), res.getTitle()));
      len = results.size();
    } catch (OMDBException | NullPointerException e) {
      this.query = e.getMessage();
      len = 0;
    }
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether you actually want the info or not
   * @param plot the desired plot return type (short or full)
   */
  public MovieQuery(String id, boolean info, String plot) {
    super();
    if (!info) {
      this.info = null;
      PARAMS = null;
    } else {
      PARAMS = new OmdbParameters();
      PARAMS.add(Param.IMDB, id);
      PARAMS.add(Param.PLOT, switchPlot(plot));
      try {
        this.info = CLIENT.getInfo(PARAMS);
        query = this.info.getTitle();
      } catch (OMDBException | NullPointerException e) {
        query = e.getMessage();
        this.info = null;
      }
    }
  }
  // <editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Get/set methods.">
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
  /**
   * @return the PARAMS
   */
  public final OmdbParameters getPARAMS() {
    return PARAMS;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="List methods.">
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
    type = capitalize(type); //give it title case because it gets returned lower case
    return type;
  }
  /**
   * Gets the plot synopsis (short or full) using the generated info.
   * @return the plot
   */
  public String listPlot() {
    return getInfo().getPlot();
  }
  // </editor-fold>

  /**
   * Switches the plot type to the desired enum.
   * @param type the desired plot type
   * @return either PlotType.SHORT or PlotType.FULL
   */
  private PlotType switchPlot(String type) {
    //TODO use this method
    switch (type.toLowerCase()) {
      case "full":
        return PlotType.FULL;
      case "short":
        return PlotType.SHORT;
      default:
        return PlotType.FULL;
    }
  }
}
