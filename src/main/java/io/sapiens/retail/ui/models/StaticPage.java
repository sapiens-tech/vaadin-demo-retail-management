package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class StaticPage {
  @Getter
  @Setter
  public static class List {
    @GridColumn(header = "Title")
    private String title;

    @GridColumn(header = "Content", flexGrow = 1)
    private String content;
  }

  @Getter
  @Setter
  public static class Edit {
    @FormField(type = FormFieldType.TextField, label = "Title")
    private String title;

    @FormField(type = FormFieldType.TextAreaField, label = "Content")
    private String content;
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public Edit fromListToEdit(List l) {
      Edit edit = new Edit();
      BeanUtils.copyProperties(l, edit);
      return edit;
    }
  }
}
