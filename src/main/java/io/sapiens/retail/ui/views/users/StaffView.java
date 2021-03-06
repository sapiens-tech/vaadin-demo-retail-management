package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "staffs", layout = BaseLayout.class)
@PageTitle("Staffs")
public class StaffView extends CrudView<User.List, User.Edit, User.Mapper> {

  private final UserService userService;

  public StaffView(@Autowired UserService userService) {
    super(User.List.class, User.Edit.class, new User.Mapper());
    this.userService = userService;
  }

  @Override
  public void onInit() {
    setGridData(userService.retrieveUser());
    setDetailTitle("Staff Details");
  }

  @Override
  public void onSave(User.Edit entity) {
    userService.saveUser(entity);
    UI.getCurrent().getPage().reload();
  }

  @Override
  public void onDelete(User.Edit entity) {
     userService.delete(entity.getId());
  }

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public List<String> onValidate(User.Edit entity) {
    return null;
  }
}
