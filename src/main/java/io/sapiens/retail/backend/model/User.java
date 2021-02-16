package io.sapiens.retail.backend.model;

import io.sapiens.awesome.model.AbstractModel;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class User extends AbstractModel {
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String hashSalt;
  private String phone;
  private Role role;
  private LocalDate lastLoggedInRead;
  private LocalDate lastLoggedInWrite;
  private LocalDate lastLoggedInIpAddress;
  private LocalDate lastLoggedInUserAgent;
}
