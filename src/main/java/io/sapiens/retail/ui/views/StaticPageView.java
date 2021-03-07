package io.sapiens.retail.ui.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.StaticPageService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.StaticPage;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Static Page")
@Route(value = "static-page", layout = BaseLayout.class)
public class StaticPageView extends CrudView<StaticPage.List, StaticPage.Edit, StaticPage.Mapper> {

  private final StaticPageService staticPageService;

  public StaticPageView(@Autowired StaticPageService staticPageService) {
    super(StaticPage.List.class, StaticPage.Edit.class, new StaticPage.Mapper());

    this.staticPageService = staticPageService;
  }

  @Override
  public void onInit() {
    setGridData(staticPageService.retrieveAllPages());
    setDetailTitle("Static Page Information");
  }

  @Override
  public void onSave(StaticPage.Edit entity) {
    staticPageService.save(entity);
  }

  @Override
  public void onDelete(StaticPage.Edit entity) {
    staticPageService.delete(entity);
  }

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(StaticPage.Edit entity) {
  }
}
