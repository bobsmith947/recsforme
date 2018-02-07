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
package me.recsfor.search_engine;

import com.omertron.omdbapi.*;
import com.omertron.omdbapi.model.*;
import java.util.List;
/**
 *
 * @author lkitaev
 */
public class MovieQuery extends AbstractQuery {
    private String query;
    private SearchResults results;
    private static final OmdbApi CLIENT = new OmdbApi("357b2b79"); //please don't use this
    
    public MovieQuery() {
      this.query = null;
      this.results = null;
    }
    /**
    * @return the query
    */
    public String getQuery() {
      return query;
    }
    /**
    * @param query the query to set
    */
    public void setQuery(String query) {
      this.query = query;
    }
    /**
    * @return the results
    */
    public SearchResults getResults() {
      return results;
    }
    /**
    * @param results the results to set
    */
    public void setResults(SearchResults results) {
      this.results = results;
    }
    
    @Override
    protected void search() {
      if (query == null || query.equals("")) {
        results = null;
      } else {
        try {
          results = CLIENT.search(query);
        } catch (OMDBException e) {
          results = null;
          query = e.getMessage();
        }
      }
    }
    
    @Override
    public String[] printSearch() {
      search();
      if (results != null) {
        List<OmdbVideoBasic> list = results.getResults();
        String[] res = new String[list.size()];
        for (int i = 0; i < res.length; i++) {
          OmdbVideoBasic o = list.get(i);
          res[i] = o.getTitle();
        }
        return res;
      }
      else {
        return null;
      }
    }
}
