package io.sapiens.retail.ui.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.StaticPage;

@PageTitle("Static Page")
@Route(value = "static-page", layout = BaseLayout.class)
public class StaticPageView extends CrudView<StaticPage> {

    @Override
    public void onInit() {

    }

    @Override
    public void onSave(StaticPage entity) {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void filter() {

    }
}
