package io.sapiens.retail.ui.components.detailsdrawer;

import io.sapiens.retail.ui.components.FlexBoxLayout;
import io.sapiens.retail.ui.layout.size.Horizontal;
import io.sapiens.retail.ui.layout.size.Right;
import io.sapiens.retail.ui.layout.size.Vertical;
import io.sapiens.retail.ui.util.LumoStyles;
import io.sapiens.retail.ui.util.UIUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;

public class DetailsDrawerFooter extends FlexBoxLayout {

  private Button save;
  private Button cancel;

  public DetailsDrawerFooter() {
    setBackgroundColor(LumoStyles.Color.Contrast._5);
    setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
    setSpacing(Right.S);
    setWidthFull();

    save = UIUtils.createPrimaryButton("Save");
    cancel = UIUtils.createTertiaryButton("Cancel");
    add(save, cancel);
  }

  public Registration addSaveListener(ComponentEventListener<ClickEvent<Button>> listener) {
    return save.addClickListener(listener);
  }

  public Registration addCancelListener(ComponentEventListener<ClickEvent<Button>> listener) {
    return cancel.addClickListener(listener);
  }
}
