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
package me.recsfor.test;

import me.recsfor.engine.search.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Provides tests for query classes.
 * Checks that name and id arrays are the same length.
 * Also makes sure that reserved URL characters are replaced properly.
 * @author lkitaev
 */
public class QueryJUnitTest {
  private static MovieQuery movie;
  private static ArtistQuery artist;
  private static AlbumQuery album;
  
  public QueryJUnitTest() {
    System.out.println("Query test created.");
  }
  
  @BeforeClass
  public static void setUpClass() {
    System.out.println("Query test started.");
    movie = new MovieQuery();
    artist = new ArtistQuery();
    album = new AlbumQuery();
  }
  
  @AfterClass
  public static void tearDownClass() {
    System.out.println("Query test ended.");
    movie = null;
    artist = null;
    album = null;
  }
  
  @Before
  public void setUp() {
    movie.setQuery("");
    artist.setQuery("");
    album.setQuery("");
  }
  
  @After
  public void tearDown() {
    movie.setQuery("");
    artist.setQuery("");
    album.setQuery("");
  }
  
  @Test
  public void checkMovieResLength() {
    movie.setQuery("blade runner");
    Integer lenOne = movie.listNames().length;
    Integer lenTwo = movie.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  
  @Test
  public void checkArtistResLength() {
    artist.setQuery("daft punk");
    Integer lenOne = artist.listNames().length;
    Integer lenTwo = artist.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  
  @Test
  public void checkAlbumResLength() {
    album.setQuery("homework");
    Integer lenOne = album.listNames().length;
    Integer lenTwo = album.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  
  @Test
  public void checkReplace() {
    String query = "idolm@ster";
    String rep = "idolm ster";
    movie.setQuery(query);
    assertNotEquals(query, movie.getQuery());
    assertEquals(rep, movie.getQuery());
    Object[] resOne = movie.getResults().values().toArray();
    movie.setQuery(rep);
    Object[] resTwo = movie.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
    artist.setQuery(query);
    assertNotEquals(query, artist.getQuery());
    assertEquals(rep, artist.getQuery());
    resOne = artist.getResults().values().toArray();
    artist.setQuery(rep);
    resTwo = artist.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
    album.setQuery(query);
    assertNotEquals(query, album.getQuery());
    assertEquals(rep, album.getQuery());
    resOne = album.getResults().values().toArray();
    album.setQuery(rep);
    resTwo = album.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
  }
}
