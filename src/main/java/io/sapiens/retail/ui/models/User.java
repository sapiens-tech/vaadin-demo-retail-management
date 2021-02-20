package io.sapiens.retail.ui.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
  @GridColumn(header = "Username")
  @FormField(type = FormFieldType.TextField, label = "Username")
  private String userName;

  @FormField(type = FormFieldType.PasswordField, label = "Password")
  @JsonIgnore
  private String password;

  @GridColumn(header = "First Name")
  @FormField(type = FormFieldType.TextField, label = "First name")
  private String firstName;

  @GridColumn(header = "Last Name")
  @FormField(type = FormFieldType.TextField, label = "Last name")
  private String lastName;

  @GridColumn(header = "Email", flexGrow = 1)
  @FormField(type = FormFieldType.TextField, label = "Email")
  private String emailAddress;

  private String hashSalt;

  @GridColumn(header = "Phone")
  @FormField(type = FormFieldType.TextField, label = "Phone")
  private String phone;

  private Role role;
}
