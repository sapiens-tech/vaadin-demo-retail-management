package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaticPage {
  @GridColumn(header = "Title")
  @FormField(type = FormFieldType.TextField, label = "Title")
  private String title;

  @GridColumn(header = "Content", flexGrow = 1)
  @FormField(type = FormFieldType.TextAreaField, label = "Content")
  private String content;
}
