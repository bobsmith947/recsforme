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
import org.musicbrainz.IncludesWs2.*;
import org.musicbrainz.FilterWs2.SearchFilter.*;
import java.util.List;
/**
 *
 * @author lkitaev
 */
public class AlbumQuery extends AbstractQuery {

    public AlbumQuery(String query) {
        super(query);
    }
    @Override
    protected void search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] printResults() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
