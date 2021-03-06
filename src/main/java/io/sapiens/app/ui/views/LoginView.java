package io.sapiens.app.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.app.backend.service.SecurityService;
import io.sapiens.app.config.PropertyHelper;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
  private final SecurityService securityService;
  private final PropertyHelper propertyHelper;
  private final LoginOverlay loginForm = new LoginOverlay();

  public LoginView(
      @Autowired SecurityService securityService, @Autowired PropertyHelper propertyHelper) {
    this.securityService = securityService;
    this.propertyHelper = propertyHelper;
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);
    add(createContent());
  }

  public Component createContent() {
    H1 title = new H1();
    title.getStyle().set("color", "var(--lumo-base-color)");
    Icon icon = VaadinIcon.VAADIN_H.create();
    icon.setSize("30px");
    icon.getStyle().set("top", "-4px");
    title.add(icon);
    title.add(new Text(propertyHelper.loginTitle));
    loginForm.setDescription(propertyHelper.loginDescription);
    loginForm.setTitle(title);
    loginForm.addLoginListener(e -> loginForm.close());

    LoginI18n i18n = LoginI18n.createDefault();
    i18n.setAdditionalInformation("To close the login form submit non-empty username and password");
    loginForm.setI18n(i18n);
    loginForm.setOpened(true);
    loginForm.setAction("login");

    return loginForm;
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      loginForm.setError(true);
    }
  }
}
