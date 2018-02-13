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
 *
 * @author lkitaev
 */
public class SongQuery extends MusicQuery {
  private List<RecordingResultWs2> results;
  private static Recording song;

    public SongQuery(String query) {
        super(query);
    }
    
    @Override
    protected void search() {
      song = new Recording();
      RecordingSearchFilterWs2 filter = song.getSearchFilter();
      final long minScore = 50L;
      final long maxResults = 25L;
      if (query == null || query.equals("")) {
        results = null;
      } else {
        filter.setMinScore(minScore);
        filter.setLimit(maxResults);
        song.search(query);
        results = song.getFirstSearchResultPage();
      }
    }

    @Override
    public String[] printResults() {
      String[] res;
      search();
      if (results != null && !results.isEmpty()) {
        res = new String[results.size()];
        for (int i = 0; i < res.length; i++) {
          RecordingWs2 s = results.get(i).getRecording();
          res[i] = s.getTitle() + " - " + s.getArtistCreditString();
        }
        return res;
      } else {
        res = null;
        return res;
      }
    }

  @Override
  public boolean changed(String curQuery, String prevQuery) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}