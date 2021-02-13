package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Order {

  @GridColumn(header = "Ordered Date")
  @FormField(type = FormFieldType.DateField, label = "Ordered Date")
  private LocalDate orderedDate;

  @GridColumn(header = "Status")
  @FormField(type = FormFieldType.TextField, label = "Status")
  private String status;
}
