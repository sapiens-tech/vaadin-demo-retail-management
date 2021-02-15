package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.services.CustomerService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
public class CustomerView extends CrudView<Person> {

  CustomerService customerService;

  public CustomerView(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void onInit() {
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
  public void filter() {}
}
