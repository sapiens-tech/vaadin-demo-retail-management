package io.sapiens.awesome.ui.components.detailsdrawer;

import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.util.BoxShadowBorders;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.util.css.FlexDirection;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class DetailsDrawerHeader extends FlexBoxLayout {

  private Button close;
  private Label title;

  public DetailsDrawerHeader(String title) {
    addClassName(BoxShadowBorders.BOTTOM);
    setFlexDirection(FlexDirection.COLUMN);
    setWidthFull();

    this.close = UIUtils.createTertiaryInlineButton(VaadinIcon.CLOSE);
    UIUtils.setLineHeight("1", this.close);

    this.title = UIUtils.createH4Label(title);

    FlexBoxLayout wrapper = new FlexBoxLayout(this.close, this.title);
    wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
    wrapper.setPadding(Horizontal.RESPONSIVE_L, Vertical.M);
    wrapper.setSpacing(Right.L);
    add(wrapper);
  }

  public DetailsDrawerHeader(String title, Component component) {
    this(title);
    add(component);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void addCloseListener(ComponentEventListener<ClickEvent<Button>> listener) {
    this.close.addClickListener(listener);
  }
}
