package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.ProductSize;

import java.util.List;

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
  public List<String> onValidate(ProductSize.Edit entity) {
    return null;
  }
}
