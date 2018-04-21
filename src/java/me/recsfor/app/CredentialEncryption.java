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
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
/**
 * This class is used to encrypt a user's credentials (their password), so it can be securely stored in a database.
 * @author lkitaev
 */
public class CredentialEncryption {
  private final String pass;
  private String salt, hash;
  private static final String HASH_ALGO = "PBKDF2WithHmacSHA1";
  private static final String SALT_ALGO = "SHA1PRNG";
  private static int ITERATIONS = 100000;
  
  public CredentialEncryption() {
    pass = "";
    salt = null;
    hash = null;
  }
  /**
   * Constructor to create a hash for the provided password using a newly generated salt.
   * @param pass the password to hash
   */
  public CredentialEncryption(String pass) {
    this.pass = pass;
    try {
      salt = generateSalt();
      hash = generateHash();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      salt = null;
      hash = null;
    }
  }
  /**
   * Constructor to create a hash for the provided password using a previously generated salt.
   * @param pass the password to hash
   * @param salt the salt to use
   */
  public CredentialEncryption(String pass, String salt) {
    this.pass = pass;
    this.salt = salt;
    try {
      hash = generateHash();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      hash = null;
    }
  }
  
  /**
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }
  /**
   * @param salt the salt to set
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }
  /**
   * @return the hash
   */
  public String getHash() {
    return hash;
  }
  /**
   * @param hash the hash to set
   */
  public void setHash(String hash) {
    this.hash = hash;
  }
  
  /**
   * Creates a random 128-bit (32 character) salt to use for hashing.
   * @return the salt
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
   * @throws InvalidKeySpecException if the key specification is wrong
   * @throws DecoderException if the salt can not be converted
   */
  private String generateHash() throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    char[] charPass = pass.toCharArray();
    PBEKeySpec spec = new PBEKeySpec(charPass, Hex.decodeHex(salt.toCharArray()), ITERATIONS, 256);
    SecretKeyFactory key = SecretKeyFactory.getInstance(HASH_ALGO);
    byte[] enc = key.generateSecret(spec).getEncoded();
    return Hex.encodeHexString(enc);
  }
  /**
   * Instance method to determine whether a password matches its stored hash, using the generated salt for the user.
   * @param storedHash the known correct password hash
   * @return whether or not the password is correct
   * @throws DecoderException if the a hash can not be converted from hexadecimal
   */
  public boolean validatePassword(String storedHash) throws DecoderException {
    byte[] testHash = Hex.decodeHex(hash.toCharArray());
    byte[] knownHash = Hex.decodeHex(storedHash.toCharArray());
    //difference between the two hashes
    //compare the key length of each
    int diff = knownHash.length ^ testHash.length;
    for (int i = 0; i < knownHash.length && i < testHash.length; i++) {
      //check the current difference against the differing bits in each byte
      //the new difference will be 0 if these values are the same
      diff |= knownHash[i] ^ testHash[i];
    }
    return diff == 0;
  }
}
