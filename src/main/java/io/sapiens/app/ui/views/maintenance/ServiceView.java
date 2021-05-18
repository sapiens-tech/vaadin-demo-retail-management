package io.sapiens.app.ui.views.maintenance;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.app.ui.BaseLayout;
import io.sapiens.app.ui.models.ProductCategory;

import java.util.Collections;
import java.util.List;

@Route(value = "services", layout = BaseLayout.class)
@PageTitle("Services")
public class ServiceView
    extends CrudView<ProductCategory.List, ProductCategory.Edit, ProductCategory.Mapper> {

  public ServiceView() {
    super(ProductCategory.List.class, ProductCategory.Edit.class, new ProductCategory.Mapper());
  }

  @Override
  public void onInit() {
    setDetailTitle("Service Information");
  }

  @Override
  public void onSave(ProductCategory.Edit entity) {}

  @Override
  public void onDelete(ProductCategory.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(ProductCategory.Edit entity) {}

  @Override
  public void onPreEditPageRendering(ProductCategory.Edit editEntity) {
    ProductCategory.Product p1 = new ProductCategory.Product();
    p1.setId("1");
    p1.setName("1");

    List<ProductCategory.Product> productList = Collections.singletonList(p1);
    editEntity.setProductCollection(productList);
  }
}
