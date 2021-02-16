package io.sapiens.awesome.enum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ApplicationContext implements ByteEnum {
  WebRoot(1);

  private byte val;

  private ApplicationContext(int val) {
    this.val = (byte) val;
  }

  @JsonCreator
  public static ApplicationContext fromValue(String val) {
    if (val != "") return ApplicationContext.valueOf(val);
    return null;
  }

  @Override
  public byte getValue() {
    return val;
  }
}
