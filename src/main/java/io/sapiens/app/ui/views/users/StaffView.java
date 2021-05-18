package io.sapiens.app.ui.views.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.components.SelectDto;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.app.backend.enums.Role;
import io.sapiens.app.backend.service.UserService;
import io.sapiens.app.ui.BaseLayout;
import io.sapiens.app.ui.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
  public void onValidate(User.Edit entity) {}

  @Override
  protected void onPreEditPageRendering(User.Edit editEntity) {
    super.onPreEditPageRendering(editEntity);
    SelectDto roles =
        new SelectDto(
            Arrays.stream(Role.values())
                .map(r -> new SelectDto.SelectItem(r, r.name()))
                .collect(Collectors.toList()),
            new ArrayList<>());

    editEntity.setRoles(roles);
  }
}
