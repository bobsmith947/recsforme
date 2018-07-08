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

//import org.musicbrainz.Controller.*;
//import org.musicbrainz.modelWs2.SearchResult.*;
import org.musicbrainz.modelWs2.Entity.*;
import org.musicbrainz.IncludesWs2.*;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.QueryWs2.LookUp.LookUpWs2;
import org.musicbrainz.QueryWs2.Search.ReadySearches.*;
import java.util.LinkedList;
import java.util.Arrays;
import org.musicbrainz.modelWs2.MediumListWs2;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather <code>release</code> and <code>release group</code> data for search results and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends BasicQuery {
  private ReleaseGroupWs2 group;
  private MediumListWs2 info;
  private final ReleaseGroupIncludesWs2 groupInc;
  private final ReleaseIncludesWs2 releaseInc;
  static final String CONTEXT = "AlbumInfo?id=";

  protected AlbumQuery() {
    super();
    group = null;
    info = null;
    groupInc = null;
    releaseInc = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  protected AlbumQuery(String query) {
    super(query);
    group = new ReleaseGroupWs2();
    new ReleaseGroupSearchbyTitle(this.query).getFirstPage().forEach(r -> {
      ReleaseGroupWs2 rg = r.getReleaseGroup();
      results.put(rg.getId(), rg.getTitle() + " - "
              + rg.getArtistCreditString());
    });
    groupInc = null;
    releaseInc = null;
    
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param full whether extra info is to be gathered or not
   */
  public AlbumQuery(String id, boolean full) {
    super();
    groupInc = new ReleaseGroupIncludesWs2();
    releaseInc = new ReleaseIncludesWs2();
    groupInc.setArtists(full);
    groupInc.setReleases(full);
    releaseInc.setRecordings(full);
    try {
      group = new LookUpWs2().getReleaseGroupById(id, groupInc);
      info = new LookUpWs2().getReleaseById(group.getReleases().get(0).getId(), releaseInc).getMediumList();
    } catch (MBWS2Exception | IndexOutOfBoundsException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      group = null;
      info = null;
    }
  }
  
  /**
   * Gets the title of the album.
   * @return the title
   */
  public String listTitle() {
    return group.getTitle();
  }
  /**
   * Gets the type (Album, EP, Single, Compilation, Soundtrack, or others) of the album.
   * @return the type
   */
  public String listType() {
    String type;
    try {
      type = group.getTypeString();
    } catch (NullPointerException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      type = "Unknown type";
    }
    //check if it's null again just in case
    return type != null ? type : "Unknown type";
  }
  /**
   * Gets the name and ID of the main artist of the album.
   * @return a length 2 array containing the name and id
   */
  public String[] listArtist() {
    //TODO add support for multiple artists
    String[] artist = new String[2];
    try {
      //get the first credit if possible
      artist[0] = group.getArtistCreditString();
      artist[1] = group.getArtistCredit().getNameCredits().get(0).getArtist().getId();
    } catch (NullPointerException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      //get the reserved artist for unknown releases
      artist[0] = "[unknown]";
      artist[1] = "125ec42a-7229-4250-afc5-e057484327fe";
    }
    return artist;
  }
  /**
   * Gets the first release date of the album.
   * @return a string representing the date
   */
  public String listDate() {
    String date;
    try {
      date = group.getFirstReleaseDateStr();
    } catch (NullPointerException e) {
      date = e.getMessage();
      System.err.println(Arrays.toString(e.getStackTrace()));
    }
    return date;
  }
  /**
   * Gets the tracks in the album.
   * @return the tracks
   */
  public String[] listTracks() {
    String[] tracks = new String[info.getTracksCount()];
    LinkedList<String> list = new LinkedList<>();
    info.getCompleteTrackList().forEach(track -> list.add(track.getRecording().getTitle()));
    return list.toArray(tracks);
  }
}
