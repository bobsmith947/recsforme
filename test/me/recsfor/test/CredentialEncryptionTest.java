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
  private CredentialEncryption encryptor;
  
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
    encryptor = new CredentialEncryption();
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
    String result = encryptor.getSalt();
    assertNull(result);
    encryptor = new CredentialEncryption(PASS);
    result = encryptor.getSalt();
    assertNotNull(result);
    encryptor = new CredentialEncryption(PASS);
    String otherResult = encryptor.getSalt();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for the same password using the same salt are the same.
   */
  @Test
  public void testHashSame() {
    System.out.println("Testing hash generation.");
    String result = encryptor.getHash();
    assertNull(result);
    encryptor = new CredentialEncryption(PASS, SALT);
    result = encryptor.getHash();
    assertNotNull(result);
    encryptor = new CredentialEncryption(PASS, SALT);
    String otherResult = encryptor.getHash();
    assertEquals(result, otherResult);
  }
  /**
   * Tests that hashes for the same password using different salts aren't the same.
   */
  @Test
  public void testHashNotSameSalt() {
    System.out.println("Testing hash generation.");
    String result = encryptor.getHash();
    assertNull(result);
    encryptor = new CredentialEncryption(PASS);
    result = encryptor.getHash();
    assertNotNull(result);
    encryptor = new CredentialEncryption(PASS, SALT);
    String otherResult = encryptor.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for different passwords using the same salt aren't the same.
   */
  @Test
  public void testHashNotSamePass() {
    System.out.println("Testing hash generation.");
    String result = encryptor.getHash();
    assertNull(result);
    encryptor = new CredentialEncryption(PASS, SALT);
    result = encryptor.getHash();
    assertNotNull(result);
    encryptor = new CredentialEncryption("alsoatestpassword", SALT);
    String otherResult = encryptor.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that hashes for different passwords using different salts aren't the same.
   */
  @Test
  public void testHashNotSame() {
    System.out.println("Testing hash generation.");
    String result = encryptor.getHash();
    assertNull(result);
    encryptor = new CredentialEncryption(PASS);
    result = encryptor.getHash();
    assertNotNull(result);
    encryptor = new CredentialEncryption("alsoatestpassword", SALT);
    String otherResult = encryptor.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that a password can be validated against an instance.
   */
  @Test
  public void testValidatePassword() {
    System.out.println("validatePassword");
    encryptor = new CredentialEncryption(PASS, SALT);
    boolean result = encryptor.validatePassword(HASH);
    assertTrue(result);
    encryptor = new CredentialEncryption(PASS);
    result = encryptor.validatePassword(HASH);
    assertFalse(result);
  }
}
