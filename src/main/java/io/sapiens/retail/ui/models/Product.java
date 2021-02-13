package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {

  @GridColumn(header = "Product Sku")
  @FormField(type = FormFieldType.TextField, label = "Product Sku")
  private String productSku;

  @GridColumn(header = "Name")
  @FormField(type = FormFieldType.TextField, label = "Name")
  private String name;

  @GridColumn(header = "Short Description")
  @FormField(type = FormFieldType.TextField, label = "Short Description")
  private String shortDescription;

  @GridColumn(header = "Long Description", flexGrow = 1)
  @FormField(type = FormFieldType.TextField, label = "Long Description")
  private String longDesceription;
}
