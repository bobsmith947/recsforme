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

import java.util.Arrays;

/**
 * Defines the servlet contexts of possible query types.
 * Allows for easier access to the context constants from outside packages.
 * @author lkitaev
 */
public enum Context {
  /**
   * The query is for movies, TV shows, or games.
   */
  MOVIE(MovieQuery.class),
  /**
   * The query is for musical artists (groups or people).
   */
  ARTIST(ArtistQuery.class),
  /**
   * The query is for musical works (like albums, singles, EP's).
   */
  ALBUM(AlbumQuery.class);
  /**
   * The servlet context which the enum type represents.
   */
  private final String context;
  /**
   * Sets the context based on the value of the corresponding field in the given class.
   * @param type the query class type
   */
  Context(Class<? extends BasicQuery> type) {
    String field;
    try {
      field = (String) type.getDeclaredField("CONTEXT").get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      field = null;
    }
    context = field;
  }
  /**
   * @return the context
   */
  public String getContext() {
    return context;
  }
}
