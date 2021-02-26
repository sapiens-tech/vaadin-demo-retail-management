package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Product;

import java.util.List;

@PageTitle("Products")
@Route(value = "products", layout = BaseLayout.class)
public class ProductView extends CrudView<Product> {
  @Override
  public void onInit() {}

  @Override
  public void onSave(Product product) {}

  @Override
  public void onDelete(Product product) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(Product entity) {
    return null;
  }
}
