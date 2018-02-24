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
import java.util.Arrays;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for album/EP/single search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends AbstractQuery {
  private ReleaseGroupWs2 group;
  private List<ReleaseWs2> albums;
  private final ReleaseGroupIncludesWs2 G_INC;
  private final ReleaseIncludesWs2 A_INC;

  public AlbumQuery() {
    super();
    group = null;
    albums = null;
    G_INC = null;
    A_INC = null;
  }
  
  public AlbumQuery(String query) {
    super(query);
    group = new ReleaseGroupWs2();
    G_INC = null;
    A_INC = null;
    String replace = query.replace("/", "");
    new ReleaseGroupSearchbyTitle(replace).getFirstPage().forEach(r -> {
      results.put(r.getReleaseGroup().getId(), r.getReleaseGroup().getTitle() + " - " + r.getReleaseGroup().getArtistCreditString());
    });
    len = results.size();
  }
  
  public AlbumQuery(String id, boolean info) {
    super();
    G_INC = new ReleaseGroupIncludesWs2();
    A_INC = new ReleaseIncludesWs2();
    G_INC.setArtists(info);
    G_INC.setReleases(info);
    A_INC.setMedia(info);
    A_INC.setRecordings(info);
    //A_INC.setReleaseGroups(info);
    try {
      group = new LookUpWs2().getReleaseGroupById(id, G_INC);
      albums = group.getReleases();
      query = group.getTitle();
    } catch (MBWS2Exception e) {
      query = e.getMessage();
      group = null;
      albums = null;
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
  
  public String listType() {
    String type;
    try {
      type = group.getTypeString();
    } catch (NullPointerException e) {
      type = e.getMessage();
    }
    return type;
  }
  
  public String listArtist() {
    return group.getArtistCreditString();
  }
}
