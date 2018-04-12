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
 * @author lkitaev
 */
public class QueryTest {
  private MovieQuery movie;
  private ArtistQuery artist;
  private AlbumQuery album;
  
  public QueryTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }
  /**
   * Tests that movie query result arrays are the same length.
   */
  @Test
  public void checkMovieResLength() {
    System.out.println("Testing movie result length.");
    movie = new MovieQuery("blade runner");
    Integer lenOne = movie.listNames().length;
    Integer lenTwo = movie.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests that artist query result arrays are the same length.
   */
  @Test
  public void checkArtistResLength() {
    System.out.println("Testing artist result length.");
    artist = new ArtistQuery("daft punk");
    Integer lenOne = artist.listNames().length;
    Integer lenTwo = artist.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests that album query result arrays are the same length.
   */
  @Test
  public void checkAlbumResLength() {
    System.out.println("Testing album result length.");
    album = new AlbumQuery("homework");
    Integer lenOne = album.listNames().length;
    Integer lenTwo = album.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests that reserved URL characters are removed properly in movie queries.
   * Should not affect search results.
   */
  @Test
  public void checkMovieReplace() {
    System.out.println("Testing URL replacement.");
    String query = "idolm@ster";
    String rep = "idolm ster";
    movie = new MovieQuery(query);
    assertNotEquals(query, movie.getQuery());
    assertEquals(rep, movie.getQuery());
    Object[] resOne = movie.getResults().values().toArray();
    movie = new MovieQuery(rep);
    Object[] resTwo = movie.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
  }
  /**
   * Tests that reserved URL characters are removed properly in artist queries.
   * Should not affect search results.
   */
  @Test
  public void checkArtistReplace() {
    System.out.println("Testing URL replacement.");
    String query = "idolm@ster";
    String rep = "idolm ster";
    artist = new ArtistQuery(query);
    assertNotEquals(query, artist.getQuery());
    assertEquals(rep, artist.getQuery());
    Object[] resOne = artist.getResults().values().toArray();
    artist = new ArtistQuery(rep);
    Object[] resTwo = artist.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
  }
  /**
   * Tests that reserved URL characters are removed properly in album queries.
   * Should not affect search results.
   */
  @Test
  public void checkAlbumReplace() {
    System.out.println("Testing URL replacement.");
    String query = "idolm@ster";
    String rep = "idolm ster";
    album = new AlbumQuery(query);
    assertNotEquals(query, album.getQuery());
    assertEquals(rep, album.getQuery());
    Object[] resOne = album.getResults().values().toArray();
    album = new AlbumQuery(rep);
    Object[] resTwo = album.getResults().values().toArray();
    assertArrayEquals(resOne, resTwo);
  }
}
