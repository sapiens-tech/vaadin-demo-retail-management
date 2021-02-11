package io.sapiens.retail.ui.util.css;

public enum FlexWrap {
  NO_WRAP("nowrap"),
  WRAP("wrap"),
  WRAP_REVERSE("wrap-reverse");

  private final String value;

  FlexWrap(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
