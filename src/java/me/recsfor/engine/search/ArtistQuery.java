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
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for artist search result and group pages.
 * @author lkitaev
 */
public class ArtistQuery extends AbstractQuery {
  private ArtistWs2 artist;
  private final ArtistIncludesWs2 INC;
  protected static final String CONTEXT = "ArtistInfo?";

  // <editor-fold desc="Constructors.">
  /**
   * Default constructor for if you didn't actually want to query anything.
   */
  public ArtistQuery() {
    super();
    artist = null;
    INC = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public ArtistQuery(String query) {
    super(query);
    artist = new ArtistWs2();
    INC = null;
    String replace = query.replace("/", "");
    new ArtistSearchbyName(replace).getFirstPage().forEach(r ->
            results.put(r.getArtist().getId(), r.getArtist().getUniqueName()));
    len = results.size();
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether you actually want the info or not
   */
  public ArtistQuery(String id, boolean info) {
    super();
    INC = new ArtistIncludesWs2();
    INC.setReleaseGroups(info);
    INC.setReleases(info);
    INC.setVariousArtists(info);
    try {
      artist = new LookUpWs2().getArtistById(id, INC);
      query = artist.getUniqueName();
    } catch (MBWS2Exception e) {
      query = e.getMessage();
      artist = null;
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Get/set methods.">
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
   * Gets the type (person or group) using the generated artist.
   * @return the type, potentially null
   */
  public String listType() {
    String type;
    try {
      type = artist.getType();
      type = type.substring(type.indexOf("#")+1);
    } catch (NullPointerException e) {
      System.err.print(Arrays.toString(e.getStackTrace()));
      type = "Unknown type";
    }
    return type;
  }
  /**
   * Gets the start and end years (if available) using the generated artist.
   * @return the years, potentially with one or both null
   */
  public String[] listYears() {
    String[] years = new String[2];
    try {
      years[0] = artist.getLifeSpan().getBegin();
    } catch (NullPointerException e) {
      years[0] = e.getMessage();
    } finally {
      try {
        years[1] = artist.getLifeSpan().getEnd();
      } catch (NullPointerException e) {
        years[1] = e.getMessage();
      }
    }
    return years;
  }
  /**
   * Gets the available release groups using the generated artist.
   * @return the release groups
   */
  public List<ReleaseGroupWs2> listAlbums() {
    return artist.getReleaseGroups();
  }
  /**
   * Gets the available release contributions using the generated artist.
   * @return the releases
   */
  public List<ReleaseWs2> listContrib() {
    return artist.getReleases();
  }
  // </editor-fold>
}
