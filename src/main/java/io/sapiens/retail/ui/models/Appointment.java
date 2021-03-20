package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormElementType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class Appointment {
  @Getter
  @Setter
  public static class Edit {
    private String id;

    @FormElement(
        type = FormElementType.TextField,
        label = "Date",
        formSectionHeader = "Authentication")
    private String date;

    @FormElement(type = FormElementType.TextField, label = "Time")
    private String time;

    @FormElement(type = FormElementType.TextField, label = "Mechanic")
    private String mechanic;

    @FormElement(type = FormElementType.TextField, label = "User")
    private String user;
  }

  @Setter
  @Getter
  public static class List {
    private String id;

    @GridColumn(header = "Date")
    private String date;

    @GridColumn(header = "Time")
    private String time;

    @GridColumn(header = "Mechanic", flexGrow = 1)
    private String mechanic;

    @GridColumn(header = "User", flexGrow = 1)
    private String user;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public Edit fromListToEdit(List l) {
      Edit edit = new Edit();
      BeanUtils.copyProperties(l, edit);
      return edit;
    }
  }
}
