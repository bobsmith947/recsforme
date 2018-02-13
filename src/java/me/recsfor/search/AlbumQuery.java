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

import org.musicbrainz.Controller.*;
import org.musicbrainz.modelWs2.SearchResult.*;
import org.musicbrainz.modelWs2.Entity.*;
//import org.musicbrainz.IncludesWs2.*;
import org.musicbrainz.FilterWs2.SearchFilter.*;
import java.util.List;
/**
 * Uses MusicBrainz (https://musicbrainz.org/ws/2/) to gather data for album search result and group pages.
 * @author lkitaev
 */
public class AlbumQuery extends MusicQuery {
  private List<ReleaseGroupResultWs2> results;
  private static ReleaseGroup album;

  public AlbumQuery(String query) {
    super(query);
  }

  @Override
  protected void search() {
    album = new ReleaseGroup(); //TODO find something more efficient than making a new ReleaseGroup each time
    ReleaseGroupSearchFilterWs2 filter = album.getSearchFilter();
    if (query == null || query.equals("")) {
      results = null;
    } else {
      filter.setMinScore(MIN_SCORE);
      filter.setLimit(MAX_RESULTS);
      album.search(query);
      results = album.getFirstSearchResultPage();
    }
  }

  @Override
  public String[] printResults() {
    String[] res;
    search();
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
}
