package io.sapiens.retail.ui.models;

import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
  @GridColumn(header = "Username")
  private String userName;

  private String password;

  @GridColumn(header = "Username")
  private String firstName;

  @GridColumn(header = "Username")
  private String lastName;

  @GridColumn(header = "Username")
  private String emailAddress;

  private String hashSalt;

  @GridColumn(header = "Username")
  private String phone;

  @GridColumn(header = "Username")
  private Role role;
}
