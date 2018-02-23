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
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for album/EP/single search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends MusicQuery {
  private final List<ReleaseGroupResultWs2> results;

  public AlbumQuery() {
    super();
    results = null;
  }
  public AlbumQuery(String query) {
    super(query);
    results = new ReleaseGroupSearchbyTitle(query).getFirstPage();
  }

  @Override
  public String[] printResults() {
    String[] res;
    if (results != null && !results.isEmpty()) {
      res = new String[results.size()];
      for (int i = 0; i < res.length; i++) {
        ReleaseGroupWs2 a = results.get(i).getReleaseGroup();
        res[i] = a.getTitle() + " - " + a.getArtistCreditString();
      }
      return res;
    } else {
      res = null;
      return res;
    }
  }
  
  //TODO optimize the below methods
  
  public String printType() {
    String type;
    try {
      type = results.get(0).getReleaseGroup().getTypeString();
    } catch (NullPointerException e) {
      type = e.getMessage();
    }
    return type;
  }
  
  public String printArtist() {
    return results.get(0).getReleaseGroup().getArtistCreditString();
  }
  
  //TODO print out the available releases
}
