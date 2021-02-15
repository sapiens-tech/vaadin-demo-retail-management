package io.sapiens.retail.backend.models;

import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String username;
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
