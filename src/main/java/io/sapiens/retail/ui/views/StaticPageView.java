package io.sapiens.retail.ui.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.StaticPage;

import java.util.List;

@PageTitle("Static Page")
@Route(value = "static-page", layout = BaseLayout.class)
public class StaticPageView extends CrudView<StaticPage.List, StaticPage.Edit, StaticPage.Mapper> {

  public StaticPageView() {
    super(StaticPage.List.class, StaticPage.Edit.class, new StaticPage.Mapper());
  }

  @Override
  public void onInit() {}

  @Override
  public void onSave(StaticPage.Edit entity) {}

  @Override
  public void onDelete(StaticPage.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(StaticPage.Edit entity) {
    return null;
  }
}
