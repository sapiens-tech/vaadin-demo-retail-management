package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.SplitViewFrame;
import io.sapiens.retail.ui.BaseLayout;

@Route(value = "product-categories", layout = BaseLayout.class)
@PageTitle("Product Categories")
public class ProductCategoryView extends SplitViewFrame {}
