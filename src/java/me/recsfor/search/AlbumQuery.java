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
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for albums, EP's, singles, and other types for search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends AbstractQuery {
  private ReleaseGroupWs2 group;
  private MediumListWs2 info;
  private boolean isNotGroup;
  private static final ReleaseGroupIncludesWs2 G_INC = new ReleaseGroupIncludesWs2();
  private static final ReleaseIncludesWs2 R_INC = new ReleaseIncludesWs2();
  protected static final String CONTEXT = "AlbumInfo?";

  // <editor-fold desc="Constructors.">
  /**
   * Default constructor for if you didn't actually want to query anything.
   */
  public AlbumQuery() {
    super();
    group = null;
    info = null;
    isNotGroup = false;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public AlbumQuery(String query) {
    super(query);
    group = new ReleaseGroupWs2();
    String replace = query.replace("/", "");
    new ReleaseGroupSearchbyTitle(replace).getFirstPage().forEach(r ->
            results.put(r.getReleaseGroup().getId(), r.getReleaseGroup().getTitle() + " - "
                    + r.getReleaseGroup().getArtistCreditString()));
    len = results.size();
    isNotGroup = false;
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether you actually want the info or not
   */
  public AlbumQuery(String id, boolean info) {
    super();
    createIncludes(info);
    try {
      group = new LookUpWs2().getReleaseGroupById(id, G_INC);
      query = group.getTitle();
      this.info = new LookUpWs2().getReleaseById(group.getReleases().get(0).getId(), R_INC).getMediumList();
      isNotGroup = false;
    } catch (MBWS2Exception | IndexOutOfBoundsException e) {
      try {
        //resort to regular release lookup
        ReleaseWs2 rel = new LookUpWs2().getReleaseById(id, R_INC);
        group = rel.getReleaseGroup();
        query = rel.getTitle();
        this.info = rel.getMediumList();
        isNotGroup = true;
      } catch (MBWS2Exception ex) {
        query = e.getMessage().concat(ex.getMessage());
        group = null;
        this.info = null;
        isNotGroup = false;
      }
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Get/set methods.">
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
  //</editor-fold>

  // <editor-fold defaultstate="collapsed" desc="List methods.">
  @Override
  public String[] listNames() {
    String[] res = new String[0];
    res = len >= 1 ? Arrays.copyOf(results.values().toArray(res), len) : null;
    return res;
  }

  @Override
  public String[] listIds() {
    String[] res = new String[0];
    res = len >= 1 ? Arrays.copyOf(getResults().keySet().toArray(res), getResults().size()) : null;
    return res;
  }
  /**
   * Gets the type (Album, EP, Single, and other types) using the generated group.
   * @return the type
   */
  public String listType() {
    String type;
    try {
      type = group.getTypeString();
    } catch (NullPointerException e) {
      System.err.print(Arrays.toString(e.getStackTrace()));
      type = "Unknown type";
    }
    //check if it's null again because this method is weird
    return type != null ? type : "Unknown type";
  }
  /**
   * Gets the artist using the generated group.
   * @return the artist
   */
  public String listArtist() {
    String artist;
    //get the first credit if possible
    try {
      artist = group.getArtistCreditString();
    } catch (NullPointerException e) {
      System.err.print(Arrays.toString(e.getStackTrace()));
      //otherwise return [unknown]
      artist = "[unknown]";
      //or return Various Artists
      //artist = "Various Artists";
    }
    return artist;
  }
  /**
   * Gets the artist's ID using the generated group.
   * @return the artist id
   */
  public String listArtistId() {
    String id;
    //get the first credit if possible
    try {
      id = group.getArtistCredit().getNameCredits().get(0).getArtist().getId();
    } catch (NullPointerException e) {
      System.err.print(Arrays.toString(e.getStackTrace()));
      //otherwise return ID for [unknown] artist
      id = "125ec42a-7229-4250-afc5-e057484327fe";
      //or return ID for Various Artists
      //return "89ad4ac3-39f7-470e-963a-56509c546377";
    }
    return id;
  }
  /**
   * Gets the date of the album (date of the first release) using the generated group.
   * @return a string representing the date
   */
  public String listDate() {
    String date;
    try {
      date = group.getFirstReleaseDateStr();
    } catch (NullPointerException e) {
      date = e.getMessage();
    }
    return date;
  }
  // </editor-fold>
  
  /**
   * Sets the relevant <code>include</code> parameters for <code>release-group</code> and <code>release</code> lookup.
   * @param inc whether you want to set the includes or not
   */
  private void createIncludes(boolean inc) {
    G_INC.setArtists(inc);
    G_INC.setReleases(inc);
    R_INC.setMedia(inc);
    R_INC.setRecordings(inc);
    R_INC.setReleaseGroups(inc);
    //R_INC.setLabel(inc);
    //R_INC.setDiscids(inc);
  }
  /**
   * Switches a <code>release</code> ID to the ID of its <code>release-group</code>.
   * @param id the id of a release
   * @return the id of the release-group
   * @throws MBWS2Exception if the lookup fails
   * @throws NullPointerException if no id could be found
   */
  public static String switchId(String id) throws MBWS2Exception, NullPointerException {
    R_INC.setReleaseGroups(true);
    return new LookUpWs2().getReleaseById(id, R_INC).getReleaseGroup().getId();
  }
}
