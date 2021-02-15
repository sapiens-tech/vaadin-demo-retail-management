package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.dummy.DummyData;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;

@Route(value = "staffs", layout = BaseLayout.class)
@PageTitle("Staffs")
public class StaffView extends CrudView<Person> {

  @Override
  public void onInit() {
    setDataSet(DummyData.getPersons());
    setDetailTitle("Staff Details");
  }

  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
