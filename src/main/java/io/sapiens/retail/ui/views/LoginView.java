package io.sapiens.retail.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.retail.backend.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
  private SecurityService securityService;
  private final LoginForm login = new LoginForm();

  public LoginView(@Autowired SecurityService securityService) {
    this.securityService = securityService;
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);

    setJustifyContentMode(JustifyContentMode.CENTER);

    login.setAction("login");

    add(new H1("Sapiens App"), login);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      login.setError(true);
    }
  }
}
