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

//import org.musicbrainz.Controller.Artist;
import java.util.Arrays;
//import org.musicbrainz.modelWs2.SearchResult.ArtistResultWs2;
import org.musicbrainz.modelWs2.Entity.*;
import org.musicbrainz.IncludesWs2.ArtistIncludesWs2;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.QueryWs2.LookUp.LookUpWs2;
import org.musicbrainz.QueryWs2.Search.ReadySearches.ArtistSearchbyName;
import java.util.List;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather <code>artist</code> data for search results and group pages.
 * @author lkitaev
 */
public class ArtistQuery extends AbstractQuery {
  private ArtistWs2 artist;
  private final ArtistIncludesWs2 inc;
  public static final String CONTEXT = "ArtistInfo?id=";

  public ArtistQuery() {
    super();
    artist = null;
    inc = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public ArtistQuery(String query) {
    super(query);
    artist = new ArtistWs2();
    inc = null;
    new ArtistSearchbyName(this.query).getFirstPage().forEach(r -> {
      results.put(r.getArtist().getId(), r.getArtist().getUniqueName());
    });
    len = results.size();
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether extra info is to be gathered or not
   */
  public ArtistQuery(String id, boolean info) {
    super();
    inc = new ArtistIncludesWs2();
    inc.setReleaseGroups(info);
    inc.setReleases(info);
    inc.setVariousArtists(info);
    try {
      artist = new LookUpWs2().getArtistById(id, inc);
      query = artist.getUniqueName();
    } catch (MBWS2Exception e) {
      query = e.getMessage();
      System.err.println(Arrays.toString(e.getStackTrace()));
      artist = null;
    }
  }
  /**
   * @return the artist
   */
  public ArtistWs2 getArtist() {
    return artist;
  }
  /**
   * @param artist the artist to set
   */
  public void setArtist(ArtistWs2 artist) {
    this.artist = artist;
  }

  //TODO get sort names too
  
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
   * Gets the type (person or group) of the artist (if available).
   * @return the type
   */
  public String listType() {
    String type;
    try {
      type = artist.getType();
      type = type.substring(type.indexOf("#")+1);
    } catch (NullPointerException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      type = "Unknown type";
    }
    return type;
  }
  /**
   * Gets the active years of the artist (if available).
   * @return a length 2 array containing the start and end years
   */
  public String[] listYears() {
    String[] years = new String[2];
    try {
      years[0] = artist.getLifeSpan().getBegin();
    } catch (NullPointerException e) {
      years[0] = e.getMessage();
      System.err.println(Arrays.toString(e.getStackTrace()));
    } finally {
      try {
        years[1] = artist.getLifeSpan().getEnd();
      } catch (NullPointerException e) {
        years[1] = e.getMessage();
        System.err.println(Arrays.toString(e.getStackTrace()));
      }
    }
    return years;
  }
  /**
   * Gets the available release groups associated with the artist.
   * @return the release groups
   */
  public List<ReleaseGroupWs2> listAlbums() {
    return artist.getReleaseGroups();
  }
  /**
   * Gets the available release contributions associated with the artist.
   * @return the releases
   */
  public List<ReleaseWs2> listContrib() {
    return artist.getReleases();
  }
}
