package io.sapiens.retail.ui.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.SplitViewFrame;
import io.sapiens.retail.ui.BaseLayout;

@PageTitle("Static Page")
@Route(value = "static-page", layout = BaseLayout.class)
public class StaticPageView extends SplitViewFrame {}
