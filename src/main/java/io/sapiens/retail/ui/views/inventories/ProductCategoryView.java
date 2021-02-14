package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.ProductCategory;
import org.springframework.stereotype.Component;

@Route(value = "product-categories", layout = BaseLayout.class)
@PageTitle("Product Categories")
@Component
public class ProductCategoryView extends CrudView<ProductCategory> {

  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
