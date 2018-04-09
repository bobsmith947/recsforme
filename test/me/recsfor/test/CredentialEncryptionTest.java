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

import me.recsfor.app.CredentialEncryption;
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
public class CredentialEncryptionTest {
  
  public CredentialEncryptionTest() {
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
   * Test of getSalt method, of class CredentialEncryption.
   */
  @Test
  public void testGetSalt() {
    System.out.println("getSalt");
    CredentialEncryption instance = new CredentialEncryption();
    String expResult = "";
    String result = instance.getSalt();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getHash method, of class CredentialEncryption.
   */
  @Test
  public void testGetHash() {
    System.out.println("getHash");
    CredentialEncryption instance = new CredentialEncryption();
    String expResult = "";
    String result = instance.getHash();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getIterations method, of class CredentialEncryption.
   */
  @Test
  public void testGetIterations() {
    System.out.println("getIterations");
    int expResult = 0;
    int result = CredentialEncryption.getIterations();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setIterations method, of class CredentialEncryption.
   */
  @Test
  public void testSetIterations() {
    System.out.println("setIterations");
    int num = 0;
    CredentialEncryption.setIterations(num);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of validatePassword method, of class CredentialEncryption.
   */
  @Test
  public void testValidatePassword_3args() throws Exception {
    System.out.println("validatePassword");
    String testPass = "";
    String storedHash = "";
    String storedSalt = "";
    boolean expResult = false;
    boolean result = CredentialEncryption.validatePassword(testPass, storedHash, storedSalt);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of validatePassword method, of class CredentialEncryption.
   */
  @Test
  public void testValidatePassword_String() throws Exception {
    System.out.println("validatePassword");
    String storedHash = "";
    CredentialEncryption instance = new CredentialEncryption();
    boolean expResult = false;
    boolean result = instance.validatePassword(storedHash);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
