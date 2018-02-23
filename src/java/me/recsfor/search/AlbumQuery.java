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

//import org.musicbrainz.Controller.*;
import org.musicbrainz.modelWs2.SearchResult.*;
import org.musicbrainz.modelWs2.Entity.*;
//import org.musicbrainz.IncludesWs2.*;
//import org.musicbrainz.MBWS2Exception;
//import org.musicbrainz.QueryWs2.LookUp.LookUpWs2;
import org.musicbrainz.QueryWs2.Search.ReadySearches.*;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for album/EP/single search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends MusicQuery {
  //private final List<ReleaseGroupResultWs2> results;
  private HashMap<String, String> results;

  public AlbumQuery() {
    super();
    results = null;
  }
  public AlbumQuery(String query) {
    super(query);
    String replace = query.replace("/", "");
    //results = new ReleaseGroupSearchbyTitle(replace).getFirstPage();
    results = new HashMap<>();
    new ReleaseGroupSearchbyTitle(replace).getFirstPage().forEach(r -> {
      results.put(r.getReleaseGroup().getId(), r.getReleaseGroup().getTitle());
    });
  }
  /**
   * @return the results
   */
  public HashMap<String, String> getResults() {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(HashMap<String, String> results) {
    this.results = results;
  }

  @Override
  public String[] printResults() {
    String[] res = new String[0];
    if (getResults() != null && !results.isEmpty()) {
      res = Arrays.copyOf(results.values().toArray(res), getResults().size());
    } else {
      res = null;
    }
    return res;
  }
  
  public String[] printIds() {
    String[] res = new String[0];
    if (getResults() != null && !getResults().isEmpty()) {
      res = Arrays.copyOf(getResults().keySet().toArray(res), getResults().size());
    } else {
      res = null;
    }
    return res;
  }
  
  //TODO fix the below methods
  //TODO optimize the below methods
  
  public String printType() {
    String type;
    try {
      //type = results.get(0).getReleaseGroup().getTypeString();
    } catch (NullPointerException e) {
      type = e.getMessage();
    }
    type = "";
    return type;
  }
  
  public String printArtist() {
    //return results.get(0).getReleaseGroup().getArtistCreditString();
    return "";
  }
  
  //TODO print out the available releases
}
