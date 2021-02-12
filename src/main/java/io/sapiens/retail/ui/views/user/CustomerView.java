package io.sapiens.retail.ui.views.user;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import infra.ui.views.CrudView;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.services.CustomerService;
import io.sapiens.retail.ui.MainLayout;
import io.sapiens.retail.ui.models.Person;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers")
public class CustomerView extends CrudView<Person> {

  public CustomerView() {
    super();
    CustomerService customerService = new CustomerService();
    setDataSet(customerService.retrieve());
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
