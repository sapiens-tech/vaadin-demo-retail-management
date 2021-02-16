package io.sapiens.awesome.util;

import io.sapiens.awesome.exception.SystemException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// http://www.owasp.org/index.php/Hashing_Java
public class HashUtil {

  private static final int ITERATION_NUMBER = 1;
  private static HashUtil me;

  public static HashUtil getInstance() {
    if (me == null) me = new HashUtil();

    return me;
  }

  public String md5(String anyString, String salt) {
    try {
      // get UTF8 bytes
      byte[] queryStringToSignInBytes = anyString.getBytes("UTF-8");
      // create MD5 hash
      MessageDigest md5Digester = MessageDigest.getInstance("MD5");
      md5Digester.update(salt.getBytes("UTF-8"));
      byte[] hashedQueryString = md5Digester.digest(queryStringToSignInBytes);

      // convert to hex
      String hashedStringInHex = new java.math.BigInteger(1, hashedQueryString).toString(16);
      while (hashedStringInHex.length() < 32) hashedStringInHex = "0" + hashedStringInHex;
      return Base64Util.getInstance().encode(hashedStringInHex.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new SystemException(e);
    }
  }

  public String getHash(String anyString, String salt) {
    byte[] bSalt = new byte[32];
    byte[] proposedDigest = new byte[32];

    try {
      bSalt = Base64Util.getInstance().decode(salt);
      proposedDigest = getHash(ITERATION_NUMBER, anyString, bSalt);

      return Base64Util.getInstance().encode(proposedDigest);
    } catch (Exception e) {
      throw new SystemException(e);
    }
  }

  /**
   * From a password, a number of iterations and a salt, returns the corresponding digest
   *
   * @param iterationNb int The number of iterations of the algorithm
   * @param password String The password to encrypt
   * @param salt byte[] The salt
   * @return byte[] The digested password
   * @throws NoSuchAlgorithmException If the algorithm doesn't exist
   * @throws UnsupportedEncodingException
   */
  private byte[] getHash(int iterationNb, String password, byte[] salt)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    digest.reset();
    digest.update(salt);
    byte[] input = digest.digest(password.getBytes("UTF-8"));
    for (int i = 0; i < iterationNb; i++) {
      digest.reset();
      input = digest.digest(input);
    }
    return input;
  }

  public String getHashForSHA512(String toHash)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return getHashForSHA512(toHash, null);
  }

  public String getHashForSHA512(String toHash, String secret)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    digest.reset();
    if (!ValidationUtil.getInstance().isEmptyString(secret)) {
      digest.update(secret.getBytes(StandardCharsets.UTF_8));
    }
    byte[] resultBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

    return org.apache.commons.codec.binary.Hex.encodeHexString(resultBytes);
  }
}
