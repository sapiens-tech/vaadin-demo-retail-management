package io.sapiens.awesome.ui.util.css.lumo;

public enum BadgeShape {
  NORMAL("normal"),
  PILL("pill");

  private final String style;

  BadgeShape(String style) {
    this.style = style;
  }

  public String getThemeName() {
    return style;
  }
}
