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

//import org.musicbrainz.Controller.Artist;
import org.musicbrainz.modelWs2.SearchResult.ArtistResultWs2;
import org.musicbrainz.modelWs2.Entity.*;
import org.musicbrainz.IncludesWs2.ArtistIncludesWs2;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.QueryWs2.LookUp.LookUpWs2;
import org.musicbrainz.QueryWs2.Search.ReadySearches.ArtistSearchbyName;
import java.util.List;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for artist search result and group pages.
 * @author lkitaev
 */
public class ArtistQuery extends MusicQuery {
  private List<ArtistResultWs2> results;

  public ArtistQuery() {
    super();
    results = null;
  }
  
  public ArtistQuery(String query) {
    super(query);
    String replace = query.replace("/", "");
    results = new ArtistSearchbyName(replace).getFirstPage();
  }
  /**
   * @return the results
   */
  public List<ArtistResultWs2> getResults() {
    return results;
  }
  /**
   * @param results the results to set
   */
  public void setResults(List<ArtistResultWs2> results) {
    this.results = results;
  }

  @Override
  public String[] printResults() {
    String[] res;
    if (getResults() != null && !results.isEmpty()) {
      res = new String[getResults().size()];
      for (int i = 0; i < res.length; i++) {
        ArtistWs2 a = getResults().get(i).getArtist();
        res[i] = a.getUniqueName();
      }
      return res;
    } else {
      res = null;
      return res;
      }
  }
  
  public String printType() {
    String type;
    try {
      type = getResults().get(0).getArtist().getType();
      type = type.substring(type.indexOf("#")+1);
    } catch (NullPointerException e) {
      type = e.getMessage();
    }
    return type;
  }

  //TODO optimize the below methods
  
  public String[] printYears() {
    String[] years = new String[2];
    ArtistWs2 a = getResults().get(0).getArtist();
    try {
      years[0] = a.getLifeSpan().getBegin();
    } catch (NullPointerException e) {
      years[0] = e.getMessage();
    } finally {
      try {
        years[1] = a.getLifeSpan().getEnd();
      } catch (NullPointerException e) {
        years[1] = e.getMessage();
      }
    }
    return years;
  }
  public List<ReleaseGroupWs2> printAlbums() {
    id = getResults().get(0).getArtist().getId();
    ArtistIncludesWs2 i = new ArtistIncludesWs2();
    i.setReleaseGroups(true);
    ArtistWs2 a;
    try {
      a = new LookUpWs2().getArtistById(id, i);
    } catch (MBWS2Exception e) {
      a = new ArtistWs2();
      a.setName(e.getMessage());
    }
    return a.getReleaseGroups();
  }
  
  public List<ReleaseWs2> printContrib() {
    id = getResults().get(0).getArtist().getId();
    ArtistIncludesWs2 i = new ArtistIncludesWs2();
    i.setVariousArtists(true);
    i.setReleases(true);
    ArtistWs2 a;
    try {
      a = new LookUpWs2().getArtistById(id, i);
    } catch (MBWS2Exception e) {
      a = new ArtistWs2();
      a.setName(e.getMessage());
    }
    return a.getReleases();
  }
}
