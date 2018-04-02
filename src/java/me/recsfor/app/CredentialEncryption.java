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
package me.recsfor.app;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CredentialEncryption() {
  private final String PASS;
  private final byte[] SALT;
  private String hash;
  private boolean valid;
  private static final String HASH_ALGO = "PBKDF2WithHmacSHA1";
  private static final String SALT_ALGO = "SHA1PRNG";
  private static final short ITERATIONS = 20000;
  
  public CredentialEncryption() {
    PASS = "";
    SALT = null;
    hash = "";
    valid = false;
  }
  
  public CredentialEncryption(String pass) {
    PASS = pass;
    SALT = generateSalt();
    hash = generateHash();
    valid = true;
  }
  
  public CredentialEncryption(String pass, String salt, String hash) {
    PASS = pass;
    SALT = salt.getBytes();
    this.hash = generateHash();
    valid = validateHash(hash);
  }
  
  private byte[] generateSalt() {
    SecureRandom rand = SecureRandom.getInstance(SALT_ALGO);
    byte[] salt = new byte[16];
    rand.nextBytes(salt);
    return salt;
  }
  
  private String generateHash() {
    char[] pass = PASS.toCharArray();
    PBEKeySpec spec = new PBEKeySpec(pass, SALT, ITERATIONS);
    SecretKeyFactory key = SecretKeyFactory.getInstance(HASH_ALGO);
    byte[] enc = key.generateSecret(spec).getEncoded();
    return ITERATIONS + ":" + SALT + ":" + enc;
  }
}
