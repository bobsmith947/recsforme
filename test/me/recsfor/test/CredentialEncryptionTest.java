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
 * Provides tests for the <code>CredentialEncryption</code> class.
 * @author lkitaev
 */
public class CredentialEncryptionTest {
  private static final String PASS = "Th1sIs@T3stPassw0rd!";
  private static final String SALT = "f542837d53b8f624b8cc626e1af707e5";
  private static final String HASH = "0d3d5c77a155446d0c0020e8e034c848ed318811161bb4794f9503eb93465a61";
  
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
   * Tests to ensure that salts aren't the same.
   */
  @Test
  public void testSalt() {
    System.out.println("Testing salt generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getSalt();
    assertNull(result);
    instance = new CredentialEncryption(PASS);
    result = instance.getSalt();
    assertNotNull(result);
    instance = new CredentialEncryption(PASS);
    String otherResult = instance.getSalt();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for the same password using the same salt are the same.
   */
  @Test
  public void testHashSame() {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS, SALT);
    result = instance.getHash();
    assertNotNull(result);
    instance = new CredentialEncryption(PASS, SALT);
    String otherResult = instance.getHash();
    assertEquals(result, otherResult);
  }
  /**
   * Tests that hashes for the same password using different salts aren't the same.
   */
  @Test
  public void testHashNotSameSalt() {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS);
    result = instance.getHash();
    assertNotNull(result);
    instance = new CredentialEncryption(PASS, SALT);
    String otherResult = instance.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for different passwords using the same salt aren't the same.
   */
  @Test
  public void testHashNotSamePass() {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS, SALT);
    result = instance.getHash();
    assertNotNull(result);
    instance = new CredentialEncryption("alsoatestpassword", SALT);
    String otherResult = instance.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for different passwords using different salts aren't the same.
   */
  @Test
  public void testHashNotSame() {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS);
    result = instance.getHash();
    assertNotNull(result);
    instance = new CredentialEncryption("alsoatestpassword", SALT);
    String otherResult = instance.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that a password can be validated against an instance.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testValidatePassword() throws Exception {
    System.out.println("validatePassword");
    CredentialEncryption instance = new CredentialEncryption(PASS, SALT);
    boolean result = instance.validatePassword(HASH);
    assertTrue(result);
    instance = new CredentialEncryption(PASS);
    result = instance.validatePassword(HASH);
    assertFalse(result);
  }
}
