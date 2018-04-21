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

import me.recsfor.app.ListData;
import me.recsfor.app.ListGroup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
/**
 * Provides tests for the <code>ListData</code> and <code>ListGroup</code> classes.
 * @author lkitaev
 */
public class ListTest {
  private String json;
  private ListData data;
  private ListGroup group;
  
  public ListTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    json = "{\"list\":[{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"},{\"name\":\"ClariS (j-pop)\",\"id\":\"f3688ad9-cd14-4cee-8fa0-0f4434e762bb\",\"type\":\"Group\"},{\"name\":\"Samurai Champloo Music Record: Playlist - Tsutchie\",\"id\":\"655400ef-8e44-3604-8fe5-86c9565b5aa5\",\"type\":\"Soundtrack\"}]}";
    data = new ListData();
    group = new ListGroup("The Idolm@ster (2011–)",
            "tt2649756",
            "Series");
  }
  
  @After
  public void tearDown() {
    json = null;
    data = null;
    group = null;
  }
  
  /**
   * Tests that JSON data can be mapped to an instance.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testMapData() throws Exception {
    System.out.println("mapData");
    ListData expResult = new ListData();
    expResult.getList().add(group);
    group = new ListGroup("ClariS (j-pop)",
            "f3688ad9-cd14-4cee-8fa0-0f4434e762bb",
            "Group");
    expResult.getList().add(group);
    group = new ListGroup("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack");
    expResult.getList().add(group);
    ListData result = ListData.mapData(json);
    assertEquals(expResult, result);
  }
  /**
   * Tests that an instance can be turned into JSON data.
   */
  @Test
  public void testStringifyData() {
    System.out.println("stringifyData");
    data.getList().add(group);
    group = new ListGroup("ClariS (j-pop)",
            "f3688ad9-cd14-4cee-8fa0-0f4434e762bb",
            "Group");
    data.getList().add(group);
    group = new ListGroup("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack");
    data.getList().add(group);
    String expResult = json;
    String result = ListData.stringifyData(data);
    assertEquals(expResult, result);
  }
  /**
   * Tests that the correct servlet context for movies can be generated.
   */
  @Test
  public void testGenerateContextMovie() {
    System.out.println("generateContext");
    String expResult = "MovieInfo?id=";
    String result = ListData.generateContext(group.getType());
    assertEquals(expResult, result);
  }
  /**
   * Tests that the correct servlet context for artists can be generated.
   */
  @Test
  public void testGenerateContextArtist() {
    System.out.println("generateContext");
    String expResult = "ArtistInfo?id=";
    group = new ListGroup("ClariS (j-pop)",
            "f3688ad9-cd14-4cee-8fa0-0f4434e762bb",
            "Group");
    String result = ListData.generateContext(group.getType());
    assertEquals(expResult, result);
  }
  /**
   * Tests that the correct servlet context for albums can be generated.
   */
  @Test
  public void testGenerateContextAlbum() {
    System.out.println("generateContext");
    String expResult = "AlbumInfo?id=";
    group = new ListGroup("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack");
    String result = ListData.generateContext(group.getType());
    assertEquals(expResult, result);
  }
  /**
   * Tests that a Java object can be turned into a JSON object.
   */
  @Test
  public void testGenerateItem() {
    System.out.println("generateItem");
    String name = "ClariS (j-pop)";
    String id = "f3688ad9-cd14-4cee-8fa0-0f4434e762bb";
    String type = "Group";
    String expResult = "{\"name\":\"ClariS (j-pop)\",\"id\":\"f3688ad9-cd14-4cee-8fa0-0f4434e762bb\",\"type\":\"Group\"}";
    String result = ListData.generateItem(name, id, type);
    assertEquals(expResult, result);
  }
  /**
   * Tests that a JSON object can be turned into a Java object.
   * @throws Exception if something goes wrong
   */
  public void testGenerateGroup() throws Exception {
    System.out.println("generateItem");
    json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    ListGroup expResult = group;
    ListGroup result = ListData.generateGroup(json);
    assertEquals(expResult, result);
  }
  /**
   * Tests that an item can be removed from a list if given group data.
   */
  @Test
  public void testRemoveItem_3args() {
    System.out.println("removeItem");
    String name = "ClariS (j-pop)";
    String id = "f3688ad9-cd14-4cee-8fa0-0f4434e762bb";
    String type = "Group";
    group = new ListGroup(name, id, type);
    data.getList().add(group);
    assertThat(data.getList().size(), is(1));
    data.removeItem(name, id, type);
    assertThat(data.getList().size(), is(0));
  }
  /**
   * Tests that an item can be removed from a list if given JSON data.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testRemoveItem_String() throws Exception {
    System.out.println("removeItem");
    json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    data.getList().add(group);
    assertThat(data.getList().size(), is(1));
    data.removeItem(json);
    assertThat(data.getList().size(), is(0));
  }
  /**
   * Tests that a list can be checked if it contains an item based on group data.
   */
  @Test
  public void testContainsItem_3args() {
    System.out.println("containsItem");
    String name = "ClariS (j-pop)";
    String id = "f3688ad9-cd14-4cee-8fa0-0f4434e762bb";
    String type = "Group";
    group = new ListGroup(name, id, type);
    data.getList().add(group);
    assertTrue(data.containsItem(name, id, type));
    assertFalse(data.containsItem("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack"));
  }
  /**
   * Tests that a list can be checked if it contains an item based on JSON data.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testContainsItem_String() throws Exception {
    System.out.println("containsItem");
    json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    data.getList().add(group);
    assertTrue(data.containsItem(json));
    assertFalse(data.containsItem("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack"));
  }
}
