package io.sapiens.app.ui.models;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.Initials;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.enums.FormElementType;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.views.CrudMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

public class Customer {

  @Getter
  @Setter
  public static class List {
    private String id;
    private String firstName;
    private String lastName;
    private String avatar;

    @GridColumn(header = "Avatar", flexGrow = 1)
    private Component avatarComponent;

    public Component getAvatarComponent() {
      return new Span(avatar);
    }

    @GridColumn(header = "User info", flexGrow = 1)
    private Component userInfo;

    public Component getUserInfo() {
      ListItem item = new ListItem(new Initials(getInitials()), getName(), getEmailAddress());
      item.setPadding(Vertical.XS);
      item.setSpacing(Right.M);
      return item;
    }

    @GridColumn(header = "Phone")
    private String phone;

    @GridColumn(header = "Email", flexGrow = 1)
    private String emailAddress;

    @GridColumn(header = "Date of birth")
    private LocalDate dateOfBirth;

    public String getInitials() {
      return (firstName.charAt(0) + lastName.charAt(0) + "").toUpperCase();
    }

    public String getName() {
      return firstName + " " + lastName;
    }
  }

  @Getter
  @Setter
  public static class Edit {
    private String id;

    @FormElement(type = FormElementType.TextField, label = "First name")
    private String firstName;

    @FormElement(type = FormElementType.TextField, label = "Last name")
    private String lastName;

    @FormElement(type = FormElementType.TextField, label = "Email")
    private String emailAddress;

    @FormElement(type = FormElementType.PhoneField, label = "Phone")
    private String phone;

    @FormElement(type = FormElementType.DateField, label = "Date of birth")
    private LocalDate dateOfBirth;

    @FormElement(type = FormElementType.FileField, label = "Avatar")
    private String avatar;
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
