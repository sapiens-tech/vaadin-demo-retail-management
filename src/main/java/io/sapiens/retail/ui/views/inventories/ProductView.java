package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.SidebarLayout;
import io.sapiens.retail.ui.models.Product;

@PageTitle("Products")
@Route(value = "products", layout = SidebarLayout.class)
public class ProductView extends CrudView<Product> {
  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
