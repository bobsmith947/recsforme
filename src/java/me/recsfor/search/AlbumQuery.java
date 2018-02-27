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
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import org.musicbrainz.modelWs2.MediumListWs2;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for albums, EP's, singles, and other types for search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends AbstractQuery {
  private ReleaseGroupWs2 group;
  private List<ReleaseWs2> albums;
  private MediumListWs2 info;
  private LinkedList<MediumListWs2> fullInfo;
  private final ReleaseGroupIncludesWs2 G_INC;
  private final ReleaseIncludesWs2 A_INC;
  protected static final String CONTEXT = "AlbumInfo?";

  // <editor-fold desc="Constructors.">
  /**
   * Default constructor for if you didn't actually want to query anything.
   */
  public AlbumQuery() {
    super();
    group = null;
    albums = null;
    G_INC = null;
    A_INC = null;
  }
  /**
   * Constructor for generating search results.
   * @param query the query to search for
   */
  public AlbumQuery(String query) {
    super(query);
    group = new ReleaseGroupWs2();
    G_INC = null;
    A_INC = null;
    String replace = query.replace("/", "");
    new ReleaseGroupSearchbyTitle(replace).getFirstPage().forEach(r ->
            results.put(r.getReleaseGroup().getId(), r.getReleaseGroup().getTitle() + " - "
                    + r.getReleaseGroup().getArtistCreditString()));
    len = results.size();
  }
  /**
   * Constructor for generating group info.
   * @param id the id to generate info for
   * @param info whether you actually want the info or not
   * @param full whether you want to get extra info or not
   */
  public AlbumQuery(String id, boolean info, boolean full) {
    super();
    G_INC = new ReleaseGroupIncludesWs2();
    A_INC = new ReleaseIncludesWs2();
    createIncludes(info);
    try {
      group = new LookUpWs2().getReleaseGroupById(id, G_INC);
      albums = group.getReleases();
      query = group.getTitle();
      if (full) {
        fullInfo = new LinkedList<>();
        this.info = null;
        albums.forEach(album -> {
          try {
            fullInfo.add(new LookUpWs2().getReleaseById(album.getId(), A_INC).getMediumList());
          } catch (MBWS2Exception e) {
            query = e.getMessage();
          }
        });
      } else {
        fullInfo = null;
        try {
          this.info = new LookUpWs2().getReleaseById(albums.get(0).getId(), A_INC).getMediumList();
        } catch (MBWS2Exception e) {
          this.info = null;
          query = e.getMessage();
        }
      }
    } catch (MBWS2Exception e) {
      query = e.getMessage();
      group = null;
      albums = null;
      this.info = null;
      fullInfo = null;
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
   * @return the albums
   */
  public List<ReleaseWs2> getAlbums() {
    return albums;
  }
  /**
   * @param albums the albums to set
   */
  public void setAlbums(List<ReleaseWs2> albums) {
    this.albums = albums;
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
   * @return the fullInfo
   */
  public LinkedList<MediumListWs2> getFullInfo() {
    return fullInfo;
  }
  /**
   * @param fullInfo the fullInfo to set
   */
  public void setFullInfo(LinkedList<MediumListWs2> fullInfo) {
    this.fullInfo = fullInfo;
  }
  //</editor-fold>

  // <editor-fold defaultstate="collapses" desc="List methods.">
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
      type = e.getMessage();
    }
    return type;
  }
  /**
   * Gets the artist using the generated group.
   * @return the artist
   */
  public String listArtist() {
    return group.getArtistCreditString();
  }
  /**
   * Gets the artist's ID using the generated group.
   * @return the artist id
   */
  public String listArtistId() {
    //TODO make sure this really works
    return group.getArtistCredit().getNameCredits().get(0).getArtist().getId();
  }
  /**
   * Gets the date of the album (date of the first release) using the generated group.
   * @return a string representing the date
   */
  public String listDate() {
    return group.getFirstReleaseDateStr();
  }
  // </editor-fold>

  /**
   * Sets the relevant include requests for group and release lookup.
   * @param inc whether you want to set the includes or not
   */
  private void createIncludes(boolean inc) {
    G_INC.setArtists(inc);
    G_INC.setReleases(inc);
    A_INC.setMedia(inc);
    A_INC.setRecordings(inc);
    //A_INC.setReleaseGroups(inc);
    //A_INC.setLabel(inc);
    //A_INC.setDiscids(inc);
  }
}
