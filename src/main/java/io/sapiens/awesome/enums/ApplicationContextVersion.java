package io.sapiens.awesome.enum;

import io.sapiens.awesome.util.ValidationUtils;

public enum ApplicationContextVersion {
  V1_0(1.0);

  private Double value;

  private ApplicationContextVersion(Double value) {
    this.value = value;
  }

  public static ApplicationContextVersion get(String version) {
    if (ValidationUtils.getInstance().isEmptyString(version)) return V1_0;
    else if (version.startsWith("1.0")) return V1_0;
    return null; // will never get here!
  }
  ;

  public Double getValue() {
    return value;
  }
}
