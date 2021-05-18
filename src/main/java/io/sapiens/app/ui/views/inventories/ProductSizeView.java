package io.sapiens.app.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.app.ui.BaseLayout;
import io.sapiens.app.ui.models.ProductSize;

@PageTitle("ProductSizes")
@Route(value = "product-sizes", layout = BaseLayout.class)
public class ProductSizeView
    extends CrudView<ProductSize.List, ProductSize.Edit, ProductSize.Mapper> {

  public ProductSizeView() {
    super(ProductSize.List.class, ProductSize.Edit.class, new ProductSize.Mapper());
  }

  @Override
  public void onInit() {}

  @Override
  public void onSave(ProductSize.Edit entity) {}

  @Override
  public void onDelete(ProductSize.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(ProductSize.Edit entity) {
  }
}
