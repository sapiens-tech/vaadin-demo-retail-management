package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.service.CustomerService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
public class CustomerView extends CrudView<Person> {

  private final CustomerService customerService;

  public CustomerView(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void onInit() {
    setDataSet(customerService.retrieveByRole(Role.CUSTOMER));
    setDetailTitle("Customer Details");
  }

  @Override
  public void onSave(Person customer) {
    System.out.print(customer);
  }

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
