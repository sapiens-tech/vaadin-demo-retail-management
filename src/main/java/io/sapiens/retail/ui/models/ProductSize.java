package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class ProductSize {
  @Setter
  @Getter
  public static class List {
    @GridColumn(header = "Code")
    private String code;

    @GridColumn(header = "Name")
    private String name;

    @GridColumn(header = "Description", flexGrow = 1)
    private String description;
  }

  @Setter
  @Getter
  public static class Edit {
    @FormField(type = FormFieldType.TextField, label = "Code")
    private String code;

    @FormField(type = FormFieldType.TextField, label = "Name")
    private String name;

    @FormField(type = FormFieldType.TextField, label = "Description")
    private String description;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public Edit fromListToEdit(List l) {
      Edit e = new Edit();
      BeanUtils.copyProperties(l, e);
      return e;
    }
  }
}
