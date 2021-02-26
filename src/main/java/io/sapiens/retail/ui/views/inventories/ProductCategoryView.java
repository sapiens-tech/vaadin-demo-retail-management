package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.ProductCategory;

import java.util.List;

@Route(value = "product-categories", layout = BaseLayout.class)
@PageTitle("Product Categories")
public class ProductCategoryView extends CrudView<ProductCategory> {

  @Override
  public void onInit() {}

  @Override
  public void onSave(ProductCategory productCategory) {}

  @Override
  public void onDelete(ProductCategory entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(ProductCategory entity) {
    return null;
  }
}
