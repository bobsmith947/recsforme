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

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
/**
 * This class is used to encrypt user credentials (their password), so it can be securely stored in a database.
 * @author lkitaev
 */
public class CredentialEncryption {
  private final String pass;
  private final String salt;
  private final String hash;
  private static final String HASH_ALGO = "PBKDF2WithHmacSHA1";
  private static final String SALT_ALGO = "SHA1PRNG";
  private static int iterations = 100000;
  /**
   * Default constructor creating an empty password with no hash or salt.
   */
  public CredentialEncryption() {
    pass = "";
    salt = null;
    hash = null;
  }
  /**
   * Constructor to create a hash for the provided password using a newly generated salt.
   * @param pass the password to hash
   * @throws NoSuchAlgorithmException if the encryption could not be applied
   * @throws InvalidKeySpecException if the key specification was wrong
   * @throws DecoderException if a hex string could not be converted
   */
  public CredentialEncryption(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    this.pass = pass;
    salt = generateSalt();
    hash = generateHash();
  }
  /**
   * Constructor to create a hash for the provided password using a previously generated salt.
   * @param pass the password to hash
   * @param salt the salt to use
   * @throws NoSuchAlgorithmException if the encryption could not be applied
   * @throws InvalidKeySpecException if the key specification was wrong
   * @throws DecoderException if the salt could not be converted
   */
  public CredentialEncryption(String pass, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    this.pass = pass;
    this.salt = salt;
    hash = generateHash();
  }
  /**
   * Creates a random 128-bit (32 character) salt to use for hashing.
   * @return the new salt
   * @throws NoSuchAlgorithmException if there is no suitable random number generator
   */
  private String generateSalt() throws NoSuchAlgorithmException {
    SecureRandom rand = SecureRandom.getInstance(SALT_ALGO);
    byte[] randSalt = new byte[16];
    rand.nextBytes(randSalt);
    return Hex.encodeHexString(randSalt);
  }
  /**
   * Creates a 256-bit (64 character) hash using password-based encryption.
   * @return the hashed password
   * @throws NoSuchAlgorithmException if there is no suitable hashing method
   * @throws InvalidKeySpecException if the key specification was wrong
   * @throws DecoderException if the salt could not be converted
   */
  private String generateHash() throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    char[] charPass = pass.toCharArray();
    PBEKeySpec spec = new PBEKeySpec(charPass, Hex.decodeHex(salt.toCharArray()), iterations, 256);
    SecretKeyFactory key = SecretKeyFactory.getInstance(HASH_ALGO);
    byte[] enc = key.generateSecret(spec).getEncoded();
    return Hex.encodeHexString(enc);
  }
  /**
   * Determines whether a password matches its stored hash, using the generated salt for the user.
   * @param testPass the password to check
   * @param storedHash the known correct password hash
   * @param storedSalt the user's salt
   * @return whether or not the password is correct
   * @throws NoSuchAlgorithmException if the test password could not be encrypted
   * @throws InvalidKeySpecException if the key specification was wrong
   * @throws DecoderException if the hashes could not be converted
   */
  public static boolean validatePassword(String testPass, String storedHash, String storedSalt) 
          throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    CredentialEncryption testCred = new CredentialEncryption(testPass, storedSalt);
    byte[] testHash = Hex.decodeHex(testCred.generateHash().toCharArray());
    byte[] hash = Hex.decodeHex(storedHash.toCharArray());
    int diff = hash.length ^ testHash.length;
    for (int i = 0; i < hash.length && i < testHash.length; i++) {
      diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
  }
  /**
   * @return the pass
   */
  protected String getPass() {
    return pass;
  }
  /**
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }
  /**
   * @return the hash
   */
  public String getHash() {
    return hash;
  }
  /**
   * @return the iterations
   */
  public static int getIterations() {
    return iterations;
  }
}
