package io.sapiens.retail.ui.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.retail.backend.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Login")
@Route(value = "login")
@CssImport("./styles/components/view-frame.css")
public class LoginView extends Composite<Div> implements HasStyle {

  private final SecurityService securityService;

  public LoginView(@Autowired SecurityService securityService) {
    super();
    this.securityService = securityService;
    setId("login");
    getContent().add(createContent());
  }

  public LoginForm createContent() {
    LoginForm component = new LoginForm();
    component.addLoginListener(
        e -> {
          String identifier = e.getUsername();
          String password = e.getPassword();
          boolean isAuthenticated = true;
          // securityService.checkAuthenticated(identifier, password);
          if (isAuthenticated) {
            UI.getCurrent().navigate(Home.class);
          } else {
            component.setError(true);
          }
        });

    return component;
  }
}
