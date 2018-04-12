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
import org.apache.commons.codec.binary.Hex;
/**
 * Provides tests for the <code>CredentialEncryption</code> class.
 * @author lkitaev
 */
public class CredentialEncryptionTest {
  private static final String PASS = "Th1sIs@T3stPassw0rd!";
  private static final byte[] SALT = {-11, 66, -125, 125, 83, -72, -10, 36, -72, -52, 98, 110, 26, -9, 7, -27};
  private static final byte[] HASH = {13, 61, 92, 119, -95, 85, 68, 109, 12, 0, 32, -24, -32, 52, -56, 72, -19, 49, -120, 17, 22, 27, -76, 121, 79, -107, 3, -21, -109, 70, 90, 97};
  
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
   * Tests that hashes for the same password/salt are the same.
   */
  @Test
  public void testHash() {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS);
    result = instance.getHash();
    String salt = instance.getSalt();
    assertNotNull(result);
    instance = new CredentialEncryption(PASS, salt);
    String otherResult = instance.getHash();
    assertEquals(result, otherResult);
    instance = new CredentialEncryption(PASS, Hex.encodeHexString(SALT));
    otherResult = instance.getHash();
    assertNotEquals(result, otherResult);
    instance = new CredentialEncryption("alsoatestpassword");
    otherResult = instance.getHash();
    assertNotEquals(result, otherResult);
  }
  /**
   * Tests that a password can be validated by providing a known salt and hash.
   */
  @Test
  public void testValidatePassword_3args() {
    System.out.println("validatePassword");
    boolean result = CredentialEncryption.validatePassword(PASS, Hex.encodeHexString(HASH), Hex.encodeHexString(SALT));
    assertTrue(result);
    result = CredentialEncryption.validatePassword("anothertestpassword", Hex.encodeHexString(HASH), Hex.encodeHexString(SALT));
    assertFalse(result);
  }
  /**
   * Tests that a password can be validated against an instance.
   * @throws Exception if something goes wrong
   */
  @Test
  public void testValidatePassword_String() throws Exception {
    System.out.println("validatePassword");
    CredentialEncryption instance = new CredentialEncryption(PASS, Hex.encodeHexString(SALT));
    boolean result = instance.validatePassword(Hex.encodeHexString(HASH));
    assertTrue(result);
    instance = new CredentialEncryption(PASS);
    result = instance.validatePassword(Hex.encodeHexString(HASH));
    assertFalse(result);
  }
}
