package io.sapiens.retail.ui.views.inventories;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.SplitViewFrame;
import io.sapiens.retail.ui.BaseLayout;

@PageTitle("Products")
@Route(value = "products", layout = BaseLayout.class)
public class ProductView extends SplitViewFrame {}
