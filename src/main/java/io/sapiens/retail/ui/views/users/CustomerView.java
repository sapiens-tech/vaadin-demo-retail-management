package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
public class CustomerView extends CrudView<Customer> {

  private final UserService userService;

  public CustomerView(@Autowired UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onInit() {
    setDataSet(userService.retrieveCustomer());
    setDetailTitle("Customer Details");
  }

  @Override
  public void onSave(Customer customer) {
    userService.saveCustomer(customer);
    UI.getCurrent().getPage().reload();
  }

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
