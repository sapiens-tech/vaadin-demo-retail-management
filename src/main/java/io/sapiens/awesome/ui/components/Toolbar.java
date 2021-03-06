package io.sapiens.awesome.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.ui.views.Callback;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Toolbar<E> extends FlexBoxLayout implements IEventListener {
  @Setter @Getter private E entity;
  private static final Logger logger = LoggerFactory.getLogger(Toolbar.class);
  private Callback callback;

  @Override
  public void addEventListener(Callback callback) {
    this.callback = callback;
  }

  public Toolbar() {
    Button newButton = new Button("New");
    newButton.addClickListener(
        event -> {
          System.out.println("new button is clicked");
          this.callback.trigger(event);
        });

    add(newButton);
    setBoxSizing(BoxSizing.BORDER_BOX);
    setHeight("80");
    setFlexDirection(FlexDirection.ROW_REVERSE);
    setPadding(Horizontal.RESPONSIVE_X, Top.M);
    newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    UIUtil.setColSpan(2, this);
  }
}
