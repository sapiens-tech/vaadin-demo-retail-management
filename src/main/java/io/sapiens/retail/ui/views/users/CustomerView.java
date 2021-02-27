package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
public class CustomerView
    extends CrudView<Customer.ListCustomer, Customer.EditCustomer, Customer.Mapper> {

  private final UserService userService;

  public CustomerView(@Autowired UserService userService) {
    super(Customer.ListCustomer.class, Customer.EditCustomer.class, new Customer.Mapper());
    this.userService = userService;
  }

  @Override
  public void onInit() {
    userService.retrieveUser();
  }

  @Override
  public void onSave(Customer.EditCustomer entity) {}

  @Override
  public void onDelete(Customer.EditCustomer entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(Customer.EditCustomer entity) {
    return null;
  }
}
