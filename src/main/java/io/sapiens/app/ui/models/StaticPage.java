package io.sapiens.app.ui.models;

import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormElementType;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class StaticPage {
  @Getter
  @Setter
  public static class List {
    private String id;

    @GridColumn(header = "Title")
    private String title;

    @GridColumn(header = "Content", flexGrow = 1)
    private String content;
  }

  @Getter
  @Setter
  public static class Edit {
    private String id;

    @FormElement(type = FormElementType.TextField, label = "Title")
    private String title;

    @FormElement(type = FormElementType.TextAreaField, label = "Content")
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
