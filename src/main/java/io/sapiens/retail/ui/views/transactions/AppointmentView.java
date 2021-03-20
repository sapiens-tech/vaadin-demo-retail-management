package io.sapiens.retail.ui.views.transactions;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Appointment;

@PageTitle("Appointments")
@Route(value = "appointments", layout = BaseLayout.class)
public class AppointmentView
    extends CrudView<Appointment.List, Appointment.Edit, Appointment.Mapper> {

  public AppointmentView() {
    super(Appointment.List.class, Appointment.Edit.class, new Appointment.Mapper());
  }

  @Override
  public void onInit() {}

  @Override
  public void onSave(Appointment.Edit entity) {}

  @Override
  public void onDelete(Appointment.Edit entity) {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  @Override
  public void onValidate(Appointment.Edit entity) {}
}
