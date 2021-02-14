package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.MainLayout;
import io.sapiens.retail.ui.models.ProductSize;

@PageTitle("ProductSizes")
@Route(value = "product-sizes", layout = MainLayout.class)
public class ProductSizeView extends CrudView<ProductSize> {
  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
