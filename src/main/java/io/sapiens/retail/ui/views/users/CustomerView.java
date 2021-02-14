package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.services.CustomerService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Route(value = "customers", layout = BaseLayout.class)
@PageTitle("Customers")
@Component
public class CustomerView extends CrudView<Person> {

  CustomerService customerService;

  @Autowired
  public CustomerView(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
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
