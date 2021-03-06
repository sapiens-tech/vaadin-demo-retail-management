package io.sapiens.app.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.app.ui.BaseLayout;
import io.sapiens.app.ui.models.Product;

@PageTitle("Products")
@Route(value = "products", layout = BaseLayout.class)
public class ProductView extends CrudView<Product.List, Product.Edit, Product.Mapper> {
  public ProductView() {
    super(Product.List.class, Product.Edit.class, new Product.Mapper());
  }

  @Override
  public void onInit() {}

  @Override
  public void onSave(Product.Edit entity) {}

  @Override
  public void onDelete(Product.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(Product.Edit entity) {}
}
