package io.sapiens.retail.ui.components.navigation.tab;

import io.sapiens.retail.ui.util.FontSize;
import io.sapiens.retail.ui.util.UIUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ClosableNaviTab extends NaviTab {

  private final Button close;

  public ClosableNaviTab(String label, Class<? extends Component> navigationTarget) {
    super(label, navigationTarget);
    getElement().setAttribute("closable", true);

    close = UIUtils.createButton(VaadinIcon.CLOSE, ButtonVariant.LUMO_TERTIARY_INLINE);
    // ButtonVariant.LUMO_SMALL isn't small enough.
    UIUtils.setFontSize(FontSize.XXS, close);
    add(close);
  }

  public Button getCloseButton() {
    return close;
  }
}
