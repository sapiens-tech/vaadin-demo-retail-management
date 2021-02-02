package com.kiroule.vaadin.businessapp.ui.util.css.lumo;

public enum BadgeSize {
  S("small"),
  M("medium");

  private final String style;

  BadgeSize(String style) {
    this.style = style;
  }

  public String getThemeName() {
    return style;
  }
}
