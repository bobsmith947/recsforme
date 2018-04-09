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

import java.util.LinkedList;
import me.recsfor.app.ListData;
import me.recsfor.app.ListModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucas Kitaev
 */
public class ListDataTest {
  
  public ListDataTest() {
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
   * Test of getList method, of class ListData.
   */
  @Test
  public void testGetList() {
    System.out.println("getList");
    ListData instance = new ListData();
    LinkedList<ListModel> expResult = null;
    LinkedList<ListModel> result = instance.getList();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setList method, of class ListData.
   */
  @Test
  public void testSetList() {
    System.out.println("setList");
    LinkedList<ListModel> list = null;
    ListData instance = new ListData();
    instance.setList(list);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of mapData method, of class ListData.
   */
  @Test
  public void testMapData() throws Exception {
    System.out.println("mapData");
    String json = "";
    ListData expResult = null;
    ListData result = ListData.mapData(json);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of generateContext method, of class ListData.
   */
  @Test
  public void testGenerateContext() {
    System.out.println("generateContext");
    String groupType = "";
    String expResult = "";
    String result = ListData.generateContext(groupType);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of generateItem method, of class ListData.
   */
  @Test
  public void testGenerateItem() {
    System.out.println("generateItem");
    String name = "";
    String id = "";
    String type = "";
    String expResult = "";
    String result = ListData.generateItem(name, id, type);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
