package io.sapiens.app.ui.models;

import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormElementType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Order {

  @GridColumn(header = "Ordered Date")
  @FormElement(type = FormElementType.DateField, label = "Ordered Date")
  private LocalDate orderedDate;

  @GridColumn(header = "Status")
  @FormElement(type = FormElementType.TextField, label = "Status")
  private String status;
}
