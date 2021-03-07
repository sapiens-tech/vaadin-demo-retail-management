package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.views.CrudMapper;
import io.sapiens.awesome.ui.views.CrudView;
import lombok.Getter;
import lombok.Setter;

public class Product {
  @Setter
  @Getter
  public static class List {
    @GridColumn(header = "Product Sku")
    @FormField(type = FormFieldType.TextField, label = "Product Sku")
    private String productSku;

    @GridColumn(header = "Name")
    private String name;

    @GridColumn(header = "Short Description")
    private String shortDescription;

    @GridColumn(header = "Long Description", flexGrow = 1)
    private String longDescription;
  }

  @Setter
  @Getter
  public static class Edit {
    @FormField(type = FormFieldType.TextField, label = "Product Sku")
    private String productSku;

    @FormField(type = FormFieldType.TextField, label = "Name")
    private String name;

    @FormField(type = FormFieldType.TextField, label = "Short Description")
    private String shortDescription;

    @FormField(type = FormFieldType.TextField, label = "Long Description")
    private String longDescription;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public Edit fromListToEdit(List l) {
      return null;
    }
  }
}
