package io.sapiens.retail.ui.models;

import infra.ui.views.GridColumn;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Person {

  @GridColumn(header = "ID")
  private Long id;

  private String firstName;

  private String lastName;

  @GridColumn(header = "Email", flexGrow = 1)
  private String email;

  @GridColumn(header = "Role")
  private Role role;

  private boolean randomBoolean;

  private int randomInteger;

  @GridColumn(header = "Last Modified")
  private LocalDate lastModified;

  public Person() {}

  public Person(
      Long id,
      String firstName,
      String lastName,
      Role role,
      String email,
      boolean randomBoolean,
      int randomInteger,
      LocalDate lastModified) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.randomBoolean = randomBoolean;
    this.randomInteger = randomInteger;
    this.lastModified = lastModified;
  }

  @GridColumn(header = "Initials")
  public String getInitials() {
    return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
  }

  @GridColumn(header = "Name")
  public String getName() {
    return firstName + " " + lastName;
  }
}
