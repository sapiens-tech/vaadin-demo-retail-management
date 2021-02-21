package io.sapiens.awesome.ui.components.navigation.tab;

import io.sapiens.awesome.ui.util.FontSize;
import io.sapiens.awesome.ui.util.UIUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ClosableNaviTab extends NaviTab {

  private final Button close;

  public ClosableNaviTab(String label, Class<? extends Component> navigationTarget) {
    super(label, navigationTarget);
    getElement().setAttribute("closable", true);

    close = UIUtil.createButton(VaadinIcon.CLOSE, ButtonVariant.LUMO_TERTIARY_INLINE);
    // ButtonVariant.LUMO_SMALL isn't small enough.
    UIUtil.setFontSize(FontSize.XXS, close);
    add(close);
  }

  public Button getCloseButton() {
    return close;
  }
}
