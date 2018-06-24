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
//import java.util.List;
//import java.util.LinkedList;
import java.util.Arrays;
import org.musicbrainz.modelWs2.MediumListWs2;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather <code>release</code> and <code>release group</code> data for search results and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends BasicQuery {
  private ReleaseGroupWs2 group;
  private MediumListWs2 info;
  private boolean isNotGroup;
  private final ReleaseGroupIncludesWs2 groupInc;
  private final ReleaseIncludesWs2 releaseInc;
  static final String CONTEXT = "AlbumInfo?id=";

  public AlbumQuery() {
    super();
    group = null;
    info = null;
    isNotGroup = false;
    groupInc = null;
    releaseInc = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public AlbumQuery(String query) {
    super(query);
    group = new ReleaseGroupWs2();
    new ReleaseGroupSearchbyTitle(this.query).getFirstPage().forEach(r -> {
      results.put(r.getReleaseGroup().getId(), r.getReleaseGroup().getTitle() + " - "
              + r.getReleaseGroup().getArtistCreditString());
    });
    isNotGroup = false;
    groupInc = null;
    releaseInc = null;
    
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether extra info is to be gathered or not
   */
  public AlbumQuery(String id, boolean info) {
    super();
    groupInc = new ReleaseGroupIncludesWs2();
    releaseInc = new ReleaseIncludesWs2();
    createIncludes(info);
    try {
      group = new LookUpWs2().getReleaseGroupById(id, groupInc);
      this.info = new LookUpWs2().getReleaseById(group.getReleases().get(0).getId(), releaseInc).getMediumList();
      isNotGroup = false;
    } catch (MBWS2Exception | IndexOutOfBoundsException e) {
      try {
        //resort to regular release lookup
        ReleaseWs2 rel = new LookUpWs2().getReleaseById(id, releaseInc);
        group = rel.getReleaseGroup();
        this.info = rel.getMediumList();
        isNotGroup = true;
      } catch (MBWS2Exception ex) {
        System.err.println(Arrays.toString(e.getStackTrace()));
        System.err.println(Arrays.toString(ex.getStackTrace()));
        group = null;
        this.info = null;
        isNotGroup = false;
      }
    }
  }
  
  /**
   * @return the group
   */
  public ReleaseGroupWs2 getGroup() {
    return group;
  }
  /**
   * @param group the group to set
   */
  public void setGroup(ReleaseGroupWs2 group) {
    this.group = group;
  }
  /**
   * @return the info
   */
  public MediumListWs2 getInfo() {
    return info;
  }
  /**
   * @param info the info to set
   */
  public void setInfo(MediumListWs2 info) {
    this.info = info;
  }
  /**
   * @return the isNotGroup
   */
  public boolean isIsNotGroup() {
    return isNotGroup;
  }
  /**
   * @param isNotGroup the isNotGroup to set
   */
  public void setIsNotGroup(boolean isNotGroup) {
    this.isNotGroup = isNotGroup;
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
   * Sets the relevant <code>include</code> parameters for <code>release group</code> and <code>release</code> lookup.
   * @param inc whether the includes are to be set or not
   */
  private void createIncludes(boolean inc) {
    groupInc.setArtists(inc);
    groupInc.setReleases(inc);
    releaseInc.setMedia(inc);
    releaseInc.setRecordings(inc);
    releaseInc.setReleaseGroups(inc);
    //R_INC.setLabel(inc);
    //R_INC.setDiscids(inc);
  }
  /**
   * Switches a <code>release</code> ID to the ID of its <code>release-group</code>.
   * @param id the id of a release
   * @return the id of the release-group
   * @throws MBWS2Exception if the lookup fails
   * @throws NullPointerException if a matching id can not be found
   */
  public static String switchId(String id) throws MBWS2Exception, NullPointerException {
    ReleaseIncludesWs2 inc = new ReleaseIncludesWs2();
    inc.setReleaseGroups(true);
    return new LookUpWs2().getReleaseById(id, inc).getReleaseGroup().getId();
  }
}
