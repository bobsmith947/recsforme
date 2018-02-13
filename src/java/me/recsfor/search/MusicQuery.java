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
/**
 * Parent class for music-based queries.
 * @author lkitaev
 */
public abstract class MusicQuery extends AbstractQuery {
  protected static final long MIN_SCORE = 50L;
  protected static final long MAX_RESULTS = 25L;
  
  protected MusicQuery() {
    this.query = "";
  }
  
  protected MusicQuery(String query) {
    this.query = query;
  }
}
