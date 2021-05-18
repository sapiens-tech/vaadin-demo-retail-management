package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
public class CustomerView
    extends CrudView<Customer.List, Customer.Edit, Customer.Mapper> {

  private final UserService userService;

  public CustomerView(@Autowired UserService userService) {
    super(Customer.List.class, Customer.Edit.class, new Customer.Mapper());
    this.userService = userService;
  }

  @Override
  public void onInit() {
    setGridData(userService.retrieveCustomer());
    setDetailTitle("Customer Information");
  }

  @Override
  public void onSave(Customer.Edit entity) {}

  @Override
  public void onDelete(Customer.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(Customer.Edit entity) {
  }
}
