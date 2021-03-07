package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.ProductCategory;

import java.util.List;

@Route(value = "product-categories", layout = BaseLayout.class)
@PageTitle("Product Categories")
public class ProductCategoryView
    extends CrudView<ProductCategory.List, ProductCategory.Edit, ProductCategory.Mapper> {

  public ProductCategoryView() {
    super(ProductCategory.List.class, ProductCategory.Edit.class, new ProductCategory.Mapper());
  }

  @Override
  public void onInit() {}

  @Override
  public void onSave(ProductCategory.Edit entity) {}

  @Override
  public void onDelete(ProductCategory.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(ProductCategory.Edit entity) {
    return null;
  }
}
