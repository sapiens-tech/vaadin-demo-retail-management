package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.awesome.ui.views.SplitViewFrame;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Product;

import java.util.List;

@PageTitle("Products")
@Route(value = "products", layout = BaseLayout.class)
public class ProductView extends CrudView<Product.List, Product.Edit, Product.Mapper> {
    public ProductView() {
        super(Product.List.class, Product.Edit.class, new Product.Mapper());
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onSave(Product.Edit entity) {

    }

    @Override
    public void onDelete(Product.Edit entity) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void filter() {

    }

    @Override
    public List<String> onValidate(Product.Edit entity) {
        return null;
    }
}
