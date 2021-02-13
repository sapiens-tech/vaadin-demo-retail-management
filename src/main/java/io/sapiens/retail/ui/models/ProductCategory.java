package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategory {
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
