package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class ProductCategory {

  @Getter
  @Setter
  public static class List {
    @GridColumn(header = "Code")
    @FormField(type = FormFieldType.TextField, label = "Code")
    private String code;

    @GridColumn(header = "Name")
    @FormField(type = FormFieldType.TextField, label = "Name")
    private String name;

    @GridColumn(header = "Description", flexGrow = 1)
    @FormField(type = FormFieldType.TextField, label = "Description")
    private String description;
  }

  @Getter
  @Setter
  public static class Edit {
    @FormField(type = FormFieldType.TextField, label = "Code")
    private String code;

    @FormField(type = FormFieldType.TextField, label = "Name")
    private String name;

    @FormField(type = FormFieldType.TextField, label = "Description")
    private String description;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    public Edit fromListToEdit(List l) {
      Edit e = new Edit();
      BeanUtils.copyProperties(l, e);
      return e;
    }
  }
}
