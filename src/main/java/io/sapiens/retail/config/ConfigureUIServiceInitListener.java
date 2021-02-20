package io.sapiens.retail.config;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import io.sapiens.awesome.util.SecurityUtil;
import io.sapiens.retail.ui.views.LoginView;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

  @Override
  public void serviceInit(ServiceInitEvent event) {
    event
        .getSource()
        .addUIInitListener(
            uiEvent -> {
              final UI ui = uiEvent.getUI();
              ui.addBeforeEnterListener(this::authenticateNavigation);
            });
  }

  private void authenticateNavigation(BeforeEnterEvent event) {
    if (!LoginView.class.equals(event.getNavigationTarget())
        && !SecurityUtil.getInstance().isUserLoggedIn()) {
      event.rerouteTo(LoginView.class);
    }
  }
}
