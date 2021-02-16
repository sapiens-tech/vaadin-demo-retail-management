package io.sapiens.awesome.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// http://www.owasp.org/index.php/Hashing_Java
public class Base64Utils {

  private static Base64Utils me;
  private Logger logger = LoggerFactory.getLogger(Base64Utils.class);

  public static Base64Utils getInstance() {
    if (me == null) me = new Base64Utils();
    return me;
  }

  /**
   * From a base 64 representation, returns the corresponding byte[]
   *
   * @param data String The base64 representation
   * @return byte[]
   * @throws IOException
   */
  public byte[] decode(String data) throws IOException {
    Base64 decoder = new Base64();
    return decoder.decode(data);
  }

  /**
   * From a byte[] returns a base 64 representation
   *
   * @param data byte[]
   * @return String
   * @throws IOException
   */
  public String encode(byte[] data) {
    Base64 encoder = new Base64();
    String ret = encoder.encodeAsString(data);
    // url safe it
    ret = ret.replace('+', '-');
    ret = ret.replace('/', '_');
    return ret;
  }
}
