package io.sapiens.retail.ui.models;

import io.sapiens.retail.backend.enums.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
  private Long id;
  private String firstName;
  private String lastName;
  private Role role;
  private String email;
  private boolean randomBoolean;
  private int randomInteger;
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

  public String getInitials() {
    return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
  }

  public String getName() {
    return firstName + " " + lastName;
  }
}
