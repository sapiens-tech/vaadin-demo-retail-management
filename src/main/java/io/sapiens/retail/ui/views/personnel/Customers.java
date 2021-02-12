package io.sapiens.retail.ui.views.personnel;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import infra.ui.views.CrudView;
import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.ui.MainLayout;
import io.sapiens.retail.ui.models.Person;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers")
public class Customers extends CrudView<Person> {
  public Customers() {
    super(DummyData.getPersons());
    setDetailTitle("Customer Details");
  }

  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {
    getDataProvider().setFilterByValue(Person::getRole, Role.ACCOUNTANT);
  }
}
