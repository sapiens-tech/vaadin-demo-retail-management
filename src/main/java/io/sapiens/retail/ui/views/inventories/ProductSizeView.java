package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.ProductSize;

import java.util.List;

@PageTitle("ProductSizes")
@Route(value = "product-sizes", layout = BaseLayout.class)
public class ProductSizeView extends CrudView<ProductSize> {

  @Override
  public void onInit() {}

  @Override
  public void onSave(ProductSize productSize) {}

  @Override
  public void onDelete(ProductSize productSize) {}

  @Override
  public void onCancel() {}

  @Override
  public List<String> onValidate(ProductSize entity) {
    return null;
  }

  @Override
  public void filter() {}
}
