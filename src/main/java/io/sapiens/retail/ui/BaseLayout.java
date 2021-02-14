package io.sapiens.retail.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import io.sapiens.awesome.ui.components.navigation.drawer.NaviItem;
import io.sapiens.awesome.ui.components.navigation.drawer.NaviMenu;
import io.sapiens.awesome.ui.layout.SidebarLayout;
import io.sapiens.retail.ui.views.Home;
import io.sapiens.retail.ui.views.inventories.ProductCategoryView;
import io.sapiens.retail.ui.views.inventories.ProductSizeView;
import io.sapiens.retail.ui.views.inventories.ProductView;
import io.sapiens.retail.ui.views.transactions.OrderView;
import io.sapiens.retail.ui.views.users.CustomerView;
import io.sapiens.retail.ui.views.users.Staff;

public class BaseLayout extends SidebarLayout {
  public BaseLayout() {
    super();
    setupSidebar();
  }

  private void setupSidebar() {
    NaviMenu menu = getNaviDrawer().getMenu();

    menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);

    NaviItem personnel = menu.addNaviItem(VaadinIcon.USERS, "User", null);
    menu.addNaviItem(personnel, "Customers", CustomerView.class);
    menu.addNaviItem(personnel, "Staffs", Staff.class);

    NaviItem product = menu.addNaviItem(VaadinIcon.ARCHIVES, "Inventories", null);
    menu.addNaviItem(product, "Categories", ProductCategoryView.class);
    menu.addNaviItem(product, "Sizes", ProductSizeView.class);
    menu.addNaviItem(product, "Products", ProductView.class);

    NaviItem order = menu.addNaviItem(VaadinIcon.CREDIT_CARD, "Transactions", null);
    menu.addNaviItem(order, "Orders", OrderView.class);
  }
}
