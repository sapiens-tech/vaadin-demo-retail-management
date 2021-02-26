package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.components.Form;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "staffs", layout = BaseLayout.class)
@PageTitle("Staffs")
public class StaffView extends CrudView<User> {

  private final UserService userService;

  public StaffView(@Autowired UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onInit() {
    setDataSet(userService.retrieveUser());
    setDetailTitle("Staff Details");
  }

  @Override
  public List<String> onValidate(User entity) {
    List<String> errors = new ArrayList<>();
    if (entity.getPassword() == null || entity.getPassword().isEmpty()) {
      errors.add("Password cannot be blank");
    }

    return errors;
  }

  @Override
  public void onSave(User user) {
    userService.saveUser(user);
    UI.getCurrent().getPage().reload();
  }

  @Override
  public void onDelete(User user) {
    userService.delete(user.getId());
  }

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}
}
