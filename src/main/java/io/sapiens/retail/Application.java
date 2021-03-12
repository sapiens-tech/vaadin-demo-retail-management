package io.sapiens.retail;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.vaadin.artur.helpers.LaunchUtil;

@PWA(
    name = "Sapiens App",
    shortName = "Sapiens App",
    iconPath = "images/logo-18.png",
    backgroundColor = "#233348",
    themeColor = "#233348")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableAsync
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

  @Override
  public void configurePage(AppShellSettings settings) {
    settings.addMetaTag("apple-mobile-web-app-capable", "yes");
    settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    settings.addFavIcon("icon", "frontend/images/favicons/favicon.ico", "256x256");
  }

  public static void main(String[] args) {
    LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
  }
}
