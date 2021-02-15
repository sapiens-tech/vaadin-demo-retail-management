package io.sapiens.retail.backend.models;

import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
  private Long id;
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private LocalDate dateOfBirth;
  private String phone;
  private String avatar;
  private Role role;
  private boolean randomBoolean;
  private int randomInteger;
  private LocalDate lastModified;
}
