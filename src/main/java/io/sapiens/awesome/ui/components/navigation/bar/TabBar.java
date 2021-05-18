package io.sapiens.awesome.ui.components.navigation.bar;

import io.sapiens.awesome.ui.layout.SidebarLayout;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.navigation.tab.NaviTabs;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.app.ui.views.Home;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import static io.sapiens.awesome.ui.util.UIUtil.IMG_PATH;

@CssImport("./styles/components/tab-bar.css")
public class TabBar extends FlexBoxLayout {

  private String CLASS_NAME = "tab-bar";

  private Button menuIcon;
  private NaviTabs tabs;
  private Button addTab;
  private Image avatar;

  public TabBar() {
    setClassName(CLASS_NAME);

    menuIcon = UIUtil.createTertiaryInlineButton(VaadinIcon.MENU);
    menuIcon.addClassName(CLASS_NAME + "__navi-icon");
    menuIcon.addClickListener(e -> SidebarLayout.get().getNaviDrawer().toggle());

    avatar = new Image();
    avatar.setClassName(CLASS_NAME + "__avatar");
    avatar.setSrc(IMG_PATH + "avatar.png");

    ContextMenu contextMenu = new ContextMenu(avatar);
    contextMenu.setOpenOnClick(true);
    contextMenu.addItem(
        "Settings",
        e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));
    contextMenu.addItem(
        "Log Out",
        e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

    addTab = UIUtil.createSmallButton(VaadinIcon.PLUS);
    addTab.addClickListener(e -> tabs.setSelectedTab(addClosableTab("New Tab", Home.class)));
    addTab.setClassName(CLASS_NAME + "__add-tab");

    tabs = new NaviTabs();
    tabs.setClassName(CLASS_NAME + "__tabs");

    add(menuIcon, tabs, addTab, avatar);
  }

  /* === MENU ICON === */

  public Button getMenuIcon() {
    return menuIcon;
  }

  /* === TABS === */

  public void centerTabs() {
    tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
  }

  private void configureTab(Tab tab) {
    tab.addClassName(CLASS_NAME + "__tab");
  }

  public Tab addTab(String text) {
    Tab tab = tabs.addTab(text);
    configureTab(tab);
    return tab;
  }

  public Tab addTab(String text, Class<? extends Component> navigationTarget) {
    Tab tab = tabs.addTab(text, navigationTarget);
    configureTab(tab);
    return tab;
  }

  public Tab addClosableTab(String text, Class<? extends Component> navigationTarget) {
    Tab tab = tabs.addClosableTab(text, navigationTarget);
    configureTab(tab);
    return tab;
  }

  public Tab getSelectedTab() {
    return tabs.getSelectedTab();
  }

  public void setSelectedTab(Tab selectedTab) {
    tabs.setSelectedTab(selectedTab);
  }

  public void updateSelectedTab(String text, Class<? extends Component> navigationTarget) {
    tabs.updateSelectedTab(text, navigationTarget);
  }

  public void addTabSelectionListener(ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
    tabs.addSelectedChangeListener(listener);
  }

  public int getTabCount() {
    return tabs.getTabCount();
  }

  public void removeAllTabs() {
    tabs.removeAll();
  }

  /* === ADD TAB BUTTON === */

  public void setAddTabVisible(boolean visible) {
    addTab.setVisible(visible);
  }
}
