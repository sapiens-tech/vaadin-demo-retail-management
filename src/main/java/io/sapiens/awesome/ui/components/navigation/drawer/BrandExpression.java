package io.sapiens.awesome.ui.components.navigation.drawer;

import io.sapiens.awesome.ui.util.UIUtil;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;

@CssImport("./styles/components/brand-expression.css")
public class BrandExpression extends Div {

  private String CLASS_NAME = "brand-expression";

  private Image logo;
  private Label title;

  public BrandExpression(String text) {
    setClassName(CLASS_NAME);

    logo = new Image(UIUtil.IMG_PATH + "logos/27.png", "");
    logo.setAlt(text + " logo");
    logo.setClassName(CLASS_NAME + "__logo");

    title = UIUtil.createH3Label(text);
    title.addClassName(CLASS_NAME + "__title");

    add(logo, title);
  }
}
