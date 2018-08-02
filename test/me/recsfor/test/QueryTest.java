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
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
/**
 * Provides tests for query classes.
 * @author lkitaev
 */
public class QueryTest {
  private Context movie, artist, album;
  private BasicQuery query;
  
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
    this.movie = Context.MOVIE;
    this.artist = Context.ARTIST;
    this.album = Context.ALBUM;
  }
  
  @After
  public void tearDown() {
  }
  
  /**
   * Tests that the movie context can be properly obtained.
   */
  @Test
  public void testMovieContext() {
    System.out.println("Testing movie context.");
    String conOne = movie.getContext();
    String conTwo = "MovieInfo?id=";
    assertEquals(conOne, conTwo);
  }
  /**
   * Tests that the artist context can be properly obtained.
   */
  @Test
  public void testArtistContext() {
    System.out.println("Testing artist context.");
    String conOne = artist.getContext();
    String conTwo = "ArtistInfo?id=";
    assertEquals(conOne, conTwo);
  }
  /**
   * Tests that the album context can be properly obtained.
   */
  @Test
  public void testAlbumContext() {
    System.out.println("Testing album context.");
    String conOne = album.getContext();
    String conTwo = "AlbumInfo?id=";
    assertEquals(conOne, conTwo);
  }
  /**
   * Tests that movie query result arrays are the same length.
   */
  @Test
  public void testMovieResLength() {
    System.out.println("Testing movie result length.");
    query = movie.createQuery("blade runner");
    try {
      query = MovieQuery.class.cast(query);
    } catch (ClassCastException e) {
      fail(e.getMessage());
    }
    Integer lenOne = query.listNames().length;
    assertThat(lenOne, is(not(0)));
    Integer lenTwo = query.listIds().length;
    assertThat(lenTwo, is(not(0)));
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests that artist query result arrays are the same length.
   */
  @Test
  public void testArtistResLength() {
    System.out.println("Testing artist result length.");
    query = artist.createQuery("daft punk");
    try {
      query = ArtistQuery.class.cast(query);
    } catch (ClassCastException e) {
      fail(e.getMessage());
    }
    Integer lenOne = query.listNames().length;
    assertThat(lenOne, is(not(0)));
    Integer lenTwo = query.listIds().length;
    assertThat(lenTwo, is(not(0)));
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests that album query result arrays are the same length.
   */
  @Test
  public void testAlbumResLength() {
    System.out.println("Testing album result length.");
    query = album.createQuery("homework");
    try {
      query = AlbumQuery.class.cast(query);
    } catch (ClassCastException e) {
      fail(e.getMessage());
    }
    Integer lenOne = query.listNames().length;
    assertThat(lenOne, is(not(0)));
    Integer lenTwo = query.listIds().length;
    assertThat(lenTwo, is(not(0)));
    assertEquals(lenOne, lenTwo);
  }
  /**
   * Tests if similar queries can be recognized.
   */
  @Test
  public void testDifferent() {
    System.out.println("Testing query differences.");
    query = Context.BASIC.createQuery("Fate/Stay Night: Heaven's Feel - I. Presage Flower");
    String otherQuery = "fate stay night heaven's feel i presage flower";
    assertFalse(query.different(otherQuery));
    otherQuery = "fate/stay night heaven's feel";
    assertTrue(query.different(otherQuery));
    query = Context.BASIC.createQuery("The Best Movie, a Story About an Apple");
    otherQuery = "best movie story about an apple";
    assertFalse(query.different(otherQuery));
    otherQuery = "movie story about an apple";
    assertTrue(query.different(otherQuery));
  }
}
