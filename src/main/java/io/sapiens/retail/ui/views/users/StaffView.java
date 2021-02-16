package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.service.CustomerService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "staffs", layout = BaseLayout.class)
@PageTitle("Staffs")
public class StaffView extends CrudView<Person> {

  private final CustomerService customerService;

  public StaffView(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void onInit() {
    setDataSet(customerService.retrieveByRole(Role.MANAGER));
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
