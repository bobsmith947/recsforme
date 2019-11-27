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

import static java.util.Arrays.asList;
import java.util.LinkedHashSet;

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
  private final String json = "{\"list\":[{\"name\":\"The Idolm@ster (2011–)\",\"id\":\"tt2649756\",\"type\":\"Series\"},{\"name\":\"ClariS (j-pop)\",\"id\":\"f3688ad9-cd14-4cee-8fa0-0f4434e762bb\",\"type\":\"Group\"},{\"name\":\"Samurai Champloo Music Record: Playlist - Tsutchie\",\"id\":\"655400ef-8e44-3604-8fe5-86c9565b5aa5\",\"type\":\"Soundtrack\"}]}";;
  private ListData data;
  private ListGroup[] groups;
  
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
    data = new ListData();
    addGroups(new ListGroup("The Idolm@ster (2011–)",
            "tt2649756",
            "Series"),
            new ListGroup("ClariS (j-pop)",
            "f3688ad9-cd14-4cee-8fa0-0f4434e762bb",
            "Group"),
            new ListGroup("Samurai Champloo Music Record: Playlist - Tsutchie",
            "655400ef-8e44-3604-8fe5-86c9565b5aa5",
            "Soundtrack"));
    groups = new ListGroup[3];
    data.getList().toArray(groups);
  }
  
  @After
  public void tearDown() {
  }
  
  /**
   * Convenience method that adds groups to a list.
   * @param groups the groups
   */
  private void addGroups(ListGroup... groups) {
    LinkedHashSet<ListGroup> list = data.getList();
    list.addAll(asList(groups));
  }
  
  /**
   * Tests that JSON data can be mapped to an instance.
   */
  @Test
  public void testMapData() {
    System.out.println("Testing data mapping.");
    ListData expResult = data;
    ListData result = ListData.mapData(json);
    assertEquals(expResult, result);
  }
  /**
   * Tests that an instance can be turned into JSON data.
   */
  @Test
  public void testStringifyData() {
    System.out.println("Testing data stringification.");
    String expResult = json;
    String result = ListData.stringifyData(data);
    assertEquals(expResult, result);
  }

  /**
   * Tests that a Java object can be turned into a JSON object.
   */
  @Test
  public void testGenerateItem() {
    System.out.println("Testing item generation.");
    String name = "ClariS (j-pop)";
    String id = "f3688ad9-cd14-4cee-8fa0-0f4434e762bb";
    String type = "Group";
    String expResult = "{\"name\":\"ClariS (j-pop)\",\"id\":\"f3688ad9-cd14-4cee-8fa0-0f4434e762bb\",\"type\":\"Group\"}";
    String result = ListData.generateItem(name, id, type);
    assertEquals(expResult, result);
  }
  /**
   * Tests that a JSON object can be turned into a Java object.
   */
  public void testGenerateGroup() {
    System.out.println("Testing group generation.");
    ListGroup expResult = groups[0];
    ListGroup result = ListData.generateGroup(json.split("}")[0]);
    assertEquals(expResult, result);
  }
  /**
   * Tests that lists can be merged.
   */
  @Test
  public void testMergeStandard() {
    System.out.println("Testing list merge.");
    LinkedHashSet<ListGroup> list = new LinkedHashSet<>(data.getList());
    LinkedHashSet<ListGroup> otherList = new LinkedHashSet<>(list);
    otherList.remove(groups[0]);
    otherList.remove(groups[1]);
    assertThat(otherList.size(), is(1));
    list.remove(groups[2]);
    assertThat(list.size(), is(2));
    ListData[] listsOne = {new ListData(list), new ListData()};
    ListData[] listsTwo = {new ListData(otherList), new ListData()};
    ListData result = ListData.mergeLists(listsOne, listsTwo)[0];
    assertThat(result.getList().size(), is(3));
    ListData expResult = data;
    assertEquals(expResult, result);
  }
  /**
   * Tests that lists can be merged and conflicts removed.
   */
  @Test
  public void testMergeRemove() {
    System.out.println("Testing list merge and removal.");
    LinkedHashSet<ListGroup> list = new LinkedHashSet<>(data.getList());
    LinkedHashSet<ListGroup> otherList = new LinkedHashSet<>(list);
    otherList.remove(groups[0]);
    otherList.remove(groups[1]);
    assertThat(otherList.size(), is(1));
    ListData[] listsOne = {new ListData(list), new ListData()};
    ListData[] listsTwo = {new ListData(), new ListData(otherList)};
    ListData result = ListData.mergeLists(listsOne, listsTwo)[0];
    assertThat(result.getList().size(), is(2));
    ListData expResult = data;
    list = data.getList();
    list.remove(groups[2]);
    assertThat(list.size(), is(2));
    assertEquals(expResult, result);
  }
}
