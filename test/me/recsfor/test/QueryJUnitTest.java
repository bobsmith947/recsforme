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

import me.recsfor.engine.search.MovieQuery;
import me.recsfor.engine.search.AlbumQuery;
import me.recsfor.engine.search.ArtistQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author lkitaev
 */
public class QueryJUnitTest {
  private static MovieQuery movie;
  private static ArtistQuery artist;
  private static AlbumQuery album;
  
  public QueryJUnitTest() {
    System.out.println("Test created!");
  }
  
  @BeforeClass
  public static void setUpClass() {
    System.out.println("Test started!");
    movie = new MovieQuery("blade runner");
  }
  
  @AfterClass
  public static void tearDownClass() {
    System.out.println("Test ended!");
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  // TODO add more test methods
  @Test
  public void hello() {
    String string = new String();
    String concat = string.concat("Hello World!");
    assertNotSame(string, concat);
  }
  
  @Test
  public void checkResLength() {
    Integer lenOne = movie.listNames().length;
    Integer lenTwo = movie.listIds().length;
    assertEquals(lenOne, lenTwo);
  }
}
