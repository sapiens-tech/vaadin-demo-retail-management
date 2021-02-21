package io.sapiens.retail.ui.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.Initials;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.util.UIUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Customer {

  @GridColumn(header = "ID")
  private Long id;

  @FormField(type = FormFieldType.TextField, label = "First name")
  private String firstName;

  @FormField(type = FormFieldType.TextField, label = "Last name")
  private String lastName;

  @JsonIgnore
  @GridColumn(header = "Email", flexGrow = 1)
  private Component userInfo;

  @GridColumn(header = "Email", flexGrow = 1)
  @FormField(type = FormFieldType.TextField, label = "Email")
  private String emailAddress;

  @FormField(type = FormFieldType.DateField, label = "Date of birth")
  @GridColumn(header = "Date of birth")
  private LocalDate dateOfBirth;

  private String phonePrefix;

  private String phoneNumber;

  @GridColumn(header = "Phone")
  @FormField(type = FormFieldType.PhoneField, label = "Phone")
  private String phone;

  @FormField(type = FormFieldType.FileField, label = "Avatar")
  private String avatar;

  @GridColumn(header = "Last Modified")
  private LocalDate lastModified;

  @GridColumn(header = "Initials")
  public String getInitials() {
    return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
  }

  @GridColumn(header = "Name")
  public String getName() {
    return firstName + " " + lastName;
  }

  // ----------------------------------- Custom UI for grid -----------------------------------//
  public Component getUserInfo() {
    ListItem item = new ListItem(new Initials(getInitials()), getName(), getEmailAddress());
    item.setPadding(Vertical.XS);
    item.setSpacing(Right.M);
    return item;
  }

  @JsonIgnore
  public Component getRole() {
    return new Span(getRole().toString());
  }

  @JsonIgnore
  public Component getLastLoginDate() {
    return new Span(UIUtil.formatDate(getLastModified()));
  }
  // ----------------------------------/ Custom UI for grid -----------------------------------//
}
