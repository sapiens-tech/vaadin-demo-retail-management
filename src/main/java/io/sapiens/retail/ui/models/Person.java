package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Person {

  @GridColumn(header = "ID")
  private Long id;

  @FormField(type = FormFieldType.TextField, label = "First name")
  private String firstName;

  @FormField(type = FormFieldType.TextField, label = "Last name")
  private String lastName;

  @GridColumn(header = "Email", flexGrow = 1)
  @FormField(type = FormFieldType.TextField, label = "Email")
  private String email;

  @FormField(type = FormFieldType.DateField, label = "Date of birth")
  @GridColumn(header = "Date of birth")
  private LocalDate dateOfBirth;

  @FormField(type = FormFieldType.PhoneField, label = "Phone")
  @GridColumn(header = "Phone")
  private String phone;

  @FormField(type = FormFieldType.FileField, label = "Avatar")
  private String avatar;

  @GridColumn(header = "Role")
  private Role role;

  private boolean randomBoolean;

  private int randomInteger;

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
}
