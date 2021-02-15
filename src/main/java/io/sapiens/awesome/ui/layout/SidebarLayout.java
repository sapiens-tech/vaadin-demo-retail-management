package io.sapiens.awesome.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.navigation.bar.AppBar;
import io.sapiens.awesome.ui.components.navigation.bar.TabBar;
import io.sapiens.awesome.ui.components.navigation.drawer.NaviDrawer;
import io.sapiens.awesome.ui.components.navigation.drawer.NaviItem;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.util.css.Display;
import io.sapiens.awesome.ui.util.css.Overflow;
import io.sapiens.retail.ui.views.Home;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CssImport(
    value = "./styles/components/charts.css",
    themeFor = "vaadin-chart",
    include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
public abstract class SidebarLayout extends FlexBoxLayout
    implements RouterLayout, AfterNavigationObserver {

  private static final Logger log = LoggerFactory.getLogger(SidebarLayout.class);
  private static final String CLASS_NAME = "root";

  private Div appHeaderOuter;

  private FlexBoxLayout row;
  private NaviDrawer naviDrawer;
  private FlexBoxLayout column;

  private Div appHeaderInner;
  private Main viewContainer;
  private Div appFooterInner;

  private Div appFooterOuter;

  private TabBar tabBar;
  private final boolean navigationTabs = false;
  private AppBar appBar;

  public SidebarLayout() {
    VaadinSession.getCurrent()
        .setErrorHandler(
            (ErrorHandler)
                errorEvent -> {
                  log.error("Uncaught UI exception", errorEvent.getThrowable());
                  Notification.show("We are sorry, but an internal error occurred");
                });

    addClassName(CLASS_NAME);
    setFlexDirection(FlexDirection.COLUMN);
    setSizeFull();
    initStructure();
    // Configure the headers and footers (optional)
    initHeadersAndFooters();
    setupSidebar();
  }

  protected abstract void setupSidebar();

  /** Initialise the required components and containers. */
  private void initStructure() {
    naviDrawer = new NaviDrawer();

    viewContainer = new Main();
    viewContainer.addClassName(CLASS_NAME + "__view-container");
    UIUtils.setDisplay(Display.FLEX, viewContainer);
    UIUtils.setFlexGrow(1, viewContainer);
    UIUtils.setOverflow(Overflow.HIDDEN, viewContainer);

    column = new FlexBoxLayout(viewContainer);
    column.addClassName(CLASS_NAME + "__column");
    column.setFlexDirection(FlexDirection.COLUMN);
    column.setFlexGrow(1, viewContainer);
    column.setOverflow(Overflow.HIDDEN);

    row = new FlexBoxLayout(naviDrawer, column);
    row.addClassName(CLASS_NAME + "__row");
    row.setFlexGrow(1, column);
    row.setOverflow(Overflow.HIDDEN);
    add(row);
    setFlexGrow(1, row);
  }

  /** Configure the app's inner and outer headers and footers. */
  private void initHeadersAndFooters() {
    setAppHeaderOuter();
    setAppFooterInner();
    setAppFooterOuter();

    // Default inner header setup:
    // - When using tabbed navigation the view title, user avatar and main menu button will appear
    // in the TabBar.
    // - When tabbed navigation is turned off they appear in the AppBar.

    appBar = new AppBar("");

    // Tabbed navigation
    if (navigationTabs) {
      tabBar = new TabBar();
      UIUtils.setTheme(Lumo.DARK, tabBar);

      // Shift-click to add a new tab
      for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
        item.addClickListener(
            e -> {
              if (e.getButton() == 0 && e.isShiftKey()) {
                tabBar.setSelectedTab(
                    tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
              }
            });
      }
      appBar.getAvatar().setVisible(false);
      setAppHeaderInner(tabBar, appBar);

      // Default navigation
    } else {
      UIUtils.setTheme(Lumo.DARK, appBar);
      setAppHeaderInner(appBar);
    }
  }

  private void setAppHeaderOuter(Component... components) {
    if (appHeaderOuter == null) {
      appHeaderOuter = new Div();
      appHeaderOuter.addClassName("app-header-outer");
      getElement().insertChild(0, appHeaderOuter.getElement());
    }
    appHeaderOuter.removeAll();
    appHeaderOuter.add(components);
  }

  private void setAppHeaderInner(Component... components) {
    if (appHeaderInner == null) {
      appHeaderInner = new Div();
      appHeaderInner.addClassName("app-header-inner");
      column.getElement().insertChild(0, appHeaderInner.getElement());
    }
    appHeaderInner.removeAll();
    appHeaderInner.add(components);
  }

  private void setAppFooterInner(Component... components) {
    if (appFooterInner == null) {
      appFooterInner = new Div();
      appFooterInner.addClassName("app-footer-inner");
      column
          .getElement()
          .insertChild(column.getElement().getChildCount(), appFooterInner.getElement());
    }
    appFooterInner.removeAll();
    appFooterInner.add(components);
  }

  private void setAppFooterOuter(Component... components) {
    if (appFooterOuter == null) {
      appFooterOuter = new Div();
      appFooterOuter.addClassName("app-footer-outer");
      getElement().insertChild(getElement().getChildCount(), appFooterOuter.getElement());
    }
    appFooterOuter.removeAll();
    appFooterOuter.add(components);
  }

  @Override
  public void showRouterLayoutContent(HasElement content) {
    this.viewContainer.getElement().appendChild(content.getElement());
  }

  public NaviDrawer getNaviDrawer() {
    return naviDrawer;
  }

  public static SidebarLayout get() {
    return (SidebarLayout)
        UI.getCurrent()
            .getChildren()
            .filter(
                component ->
                    component.getClass() == SidebarLayout.class
                        || SidebarLayout.class.isAssignableFrom(component.getClass()))
            .findFirst()
            .get();
  }

  public AppBar getAppBar() {
    return appBar;
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
    if (navigationTabs) {
      afterNavigationWithTabs(event);
    } else {
      afterNavigationWithoutTabs(event);
    }
  }

  private void afterNavigationWithTabs(AfterNavigationEvent e) {
    NaviItem active = getActiveItem(e);
    if (active == null) {
      if (tabBar.getTabCount() == 0) {
        tabBar.addClosableTab("", Home.class);
      }
    } else {
      if (tabBar.getTabCount() > 0) {
        tabBar.updateSelectedTab(active.getText(), active.getNavigationTarget());
      } else {
        tabBar.addClosableTab(active.getText(), active.getNavigationTarget());
      }
    }
    appBar.getMenuIcon().setVisible(false);
  }

  private NaviItem getActiveItem(AfterNavigationEvent e) {
    for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
      if (item.isHighlighted(e)) {
        return item;
      }
    }
    return null;
  }

  private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
    NaviItem active = getActiveItem(e);
    if (active != null) {
      getAppBar().setTitle(active.getText());
    }
  }
}
