package io.sapiens.retail.ui.models;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Collection;

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

    private Collection<Product> productCollection;

    @FormField(type = FormFieldType.Widget)
    private Component productListComponent;

    public Component getProductListComponent() {
      Span head = new Span();
      head.setText("Product List");
      Div div = new Div();
      div.add(head);
      for (Product product : getProductCollection()) {
        Span span = new Span(product.getName());
        div.add(span);
      }

      return div;
    }
  }

  @Getter
  @Setter
  public static class Product {
    private String id;
    private String name;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    public Edit fromListToEdit(List l) {
      Edit e = new Edit();
      BeanUtils.copyProperties(l, e);
      return e;
    }
  }
}
