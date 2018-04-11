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
 * Ensures that salts <em>aren't</em> the same.
 * Also ensures that hashes for the same password <em>are</em> the same.
 * @author lkitaev
 */
public class CredentialEncryptionTest {
  private final String PASS = "Th1sIs@T3stPassw0rd!";
  private final byte[] testSalt = CredentialEncryption.newSalt();
  private final byte[] testHash = CredentialEncryption.newHash(PASS, testSalt);
  
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

  @Test
  public void testSalt() throws Exception {
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

  @Test
  public void testHash() throws Exception {
    System.out.println("Testing hash generation.");
    CredentialEncryption instance = new CredentialEncryption();
    String result = instance.getHash();
    assertNull(result);
    instance = new CredentialEncryption(PASS);
    result = instance.getHash();
    assertNotNull(result);
    instance = new CredentialEncryption(PASS);
    String otherResult = instance.getHash();
    assertEquals(result, otherResult);
  }

  @Test
  public void testValidatePassword_3args() {
    System.out.println("validatePassword");
    boolean result = CredentialEncryption.validatePassword(PASS, Hex.encodeHexString(testHash), Hex.encodeHexString(testSalt));
    assertTrue(result);
    result = CredentialEncryption.validatePassword("anothertestpassword", Hex.encodeHexString(testHash), Hex.encodeHexString(testSalt));
    assertFalse(result);
  }

  @Test
  public void testValidatePassword_String() throws Exception {
    System.out.println("validatePassword");
    CredentialEncryption instance = new CredentialEncryption(PASS, Hex.encodeHexString(testSalt));
    boolean result = instance.validatePassword(Hex.encodeHexString(testHash));
    assertTrue(result);
    instance = new CredentialEncryption(PASS);
    result = instance.validatePassword(Hex.encodeHexString(testHash));
    assertFalse(result);
  }
  
}
