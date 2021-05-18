package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.service.UserService;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Mechanic;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "mechanics", layout = BaseLayout.class)
@PageTitle("Mechanics")
public class MechanicView extends CrudView<Mechanic.List, Mechanic.Edit, Mechanic.Mapper> {

  private final UserService userService;

  public MechanicView(@Autowired UserService userService) {
    super(Mechanic.List.class, Mechanic.Edit.class, new Mechanic.Mapper());
    this.userService = userService;
  }

  @Override
  public void onInit() {
    setGridData(userService.retrieveMechanic());
    setDetailTitle("Mechanic Information");
  }

  @Override
  public void onSave(Mechanic.Edit entity) {}

  @Override
  public void onDelete(Mechanic.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(Mechanic.Edit entity) {}
}
