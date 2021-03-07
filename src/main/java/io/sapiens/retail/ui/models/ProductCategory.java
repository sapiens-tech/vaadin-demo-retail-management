package io.sapiens.retail.ui.models;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
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
    private String code;

    @GridColumn(header = "Name")
    private String name;

    @GridColumn(header = "Description", flexGrow = 1)
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

    @FormField(type = FormFieldType.Widget)
    private Component productList;

    private Component getProductList() {
      Span span = new Span();
      span.setText("Product List");
      return span;
    }
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    public Edit fromListToEdit(List l) {
      Edit e = new Edit();
      BeanUtils.copyProperties(l, e);
      return e;
    }
  }
}
