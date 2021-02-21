package io.sapiens.awesome.ui.components;

import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.util.css.lumo.BadgeColor;
import io.sapiens.awesome.ui.util.css.lumo.BadgeShape;
import io.sapiens.awesome.ui.util.css.lumo.BadgeSize;
import com.vaadin.flow.component.html.Span;

import java.util.StringJoiner;

import static io.sapiens.awesome.ui.util.css.lumo.BadgeShape.PILL;

public class Badge extends Span {

  public Badge(String text) {
    this(text, BadgeColor.NORMAL);
  }

  public Badge(String text, BadgeColor color) {
    super(text);
    UIUtil.setTheme(color.getThemeName(), this);
  }

  public Badge(String text, BadgeColor color, BadgeSize size, BadgeShape shape) {
    super(text);
    StringJoiner joiner = new StringJoiner(" ");
    joiner.add(color.getThemeName());
    if (shape.equals(PILL)) {
      joiner.add(shape.getThemeName());
    }
    if (size.equals(BadgeSize.S)) {
      joiner.add(size.getThemeName());
    }
    UIUtil.setTheme(joiner.toString(), this);
  }
}
