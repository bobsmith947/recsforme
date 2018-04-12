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
  private static final String DATA = "{\"list\":[{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"},{\"name\":\"ClariS (j-pop)\",\"id\":\"f3688ad9-cd14-4cee-8fa0-0f4434e762bb\",\"type\":\"Group\"}]}";
  
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
  }
  
  @After
  public void tearDown() {
  }
  /**
   * Tests that JSON data can be mapped to an instance.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testMapData() throws Exception {
    System.out.println("mapData");
    ListData expResult = new ListData();
    ListGroup group = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    expResult.getList().add(group);
    group = new ListGroup("ClariS (j-pop)", "f3688ad9-cd14-4cee-8fa0-0f4434e762bb", "Group");
    expResult.getList().add(group);
    ListData result = ListData.mapData(DATA);
    assertEquals(expResult, result);
  }
  /**
   * Tests that an instance can be turned into JSON data.
   */
  @Test
  public void testStringifyData() {
    System.out.println("stringifyData");
    ListData data = new ListData();
    ListGroup group = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    data.getList().add(group);
    group = new ListGroup("ClariS (j-pop)", "f3688ad9-cd14-4cee-8fa0-0f4434e762bb", "Group");
    data.getList().add(group);
    String expResult = DATA;
    String result = ListData.stringifyData(data);
    assertEquals(expResult, result);
  }
  /**
   * Tests that the correct servlet context for groups can be generated.
   */
  @Test
  public void testGenerateContext() {
    System.out.println("generateContext");
    ListGroup group = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    String expResult = "MovieInfo?id=";
    String result = ListData.generateContext(group.getType());
    assertEquals(expResult, result);
    expResult = "ArtistInfo?id=";
    group = new ListGroup("ClariS (j-pop)", "f3688ad9-cd14-4cee-8fa0-0f4434e762bb", "Group");
    result = ListData.generateContext(group.getType());
    assertEquals(expResult, result);
  }
  /**
   * Tests that a Java object can be turned into a JSON object.
   */
  @Test
  public void testGenerateItem_3args() {
    System.out.println("generateItem");
    String name = "The Idolm@ster (2011–)";
    String id = "tt2649756";
    String type = "Series";
    String expResult = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    String result = ListData.generateItem(name, id, type);
    assertEquals(expResult, result);
  }
  /**
   * Tests that a JSON object can be turned into a Java object.
   * @throws Exception if something goes wrong
   */
  public void testGenerateItem_String() throws Exception {
    System.out.println("generateItem");
    String json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    ListGroup expResult = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    ListGroup result = ListData.generateItem(json);
    assertEquals(expResult, result);
  }
  /**
   * Tests that an item can be removed from a list if given group data.
   */
  @Test
  public void testRemoveItem_3args() {
    System.out.println("removeItem");
    String name = "The Idolm@ster (2011–)";
    String id = "tt2649756";
    String type = "Series";
    ListData instance = new ListData();
    ListGroup group = new ListGroup(name, id, type);
    instance.getList().add(group);
    assertThat(instance.getList().size(), is(1));
    instance.removeItem(name, id, type);
    assertThat(instance.getList().size(), is(0));
    
  }
  /**
   * Tests that an item can be removed from a list if given JSON data.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testRemoveItem_String() throws Exception {
    System.out.println("removeItem");
    String json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    ListData instance = new ListData();
    ListGroup group = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    instance.getList().add(group);
    assertThat(instance.getList().size(), is(1));
    instance.removeItem(json);
    assertThat(instance.getList().size(), is(0));
  }
  /**
   * Tests that a list can be checked if it contains an item based on group data.
   */
  @Test
  public void testContainsItem_3args() {
    System.out.println("containsItem");
    String name = "The Idolm@ster (2011–)";
    String id = "tt2649756";
    String type = "Series";
    ListData instance = new ListData();
    ListGroup group = new ListGroup(name, id, type);
    instance.getList().add(group);
    assertTrue(instance.containsItem(name, id, type));
    assertFalse(instance.containsItem("The Idolmaster Movie: Beyond the Brilliant Future!", "tt3306854", "Movie"));
  }
  /**
   * Tests that a list can be checked if it contains an item based on JSON data.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testContainsItem_String() throws Exception {
    System.out.println("containsItem");
    String json = "{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"}";
    ListData instance = new ListData();
    ListGroup group = new ListGroup("The Idolm@ster (2011–)", "tt2649756", "Series");
    instance.getList().add(group);
    assertTrue(instance.containsItem(json));
    assertFalse(instance.containsItem("The Idolmaster Movie: Beyond the Brilliant Future!", "tt3306854", "Movie"));
  }
}
