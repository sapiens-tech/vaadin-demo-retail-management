package io.sapiens.app.ui.models;

import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormElementType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;

public class Product {
  @Setter
  @Getter
  public static class List {
    @GridColumn(header = "Product Sku")
    @FormElement(type = FormElementType.TextField, label = "Product Sku")
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
    @FormElement(type = FormElementType.TextField, label = "Product Sku")
    private String productSku;

    @FormElement(type = FormElementType.TextField, label = "Name")
    private String name;

    @FormElement(type = FormElementType.TextField, label = "Short Description")
    private String shortDescription;

    @FormElement(type = FormElementType.TextField, label = "Long Description")
    private String longDescription;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public Edit fromListToEdit(List l) {
      return null;
    }
  }
}
