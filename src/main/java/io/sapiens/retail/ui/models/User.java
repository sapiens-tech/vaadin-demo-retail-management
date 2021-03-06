package io.sapiens.retail.ui.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.Component;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.Badge;
import io.sapiens.awesome.ui.enums.FormFieldType;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.views.CrudMapper;
import io.sapiens.retail.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class User {

  @Setter
  @Getter
  public static class Edit {
    private String id;

    @FormField(type = FormFieldType.TextField, label = "Username")
    private String userName;

    @FormField(type = FormFieldType.PasswordField, label = "Password")
    @JsonIgnore
    private String password;

    @FormField(type = FormFieldType.TextField, label = "First name")
    private String firstName;

    @FormField(type = FormFieldType.TextField, label = "Last name")
    private String lastName;

    @FormField(type = FormFieldType.TextField, label = "Email")
    private String emailAddress;

    @FormField(type = FormFieldType.TextField, label = "Phone")
    private String phone;
  }

  @Setter
  @Getter
  public static class List {
    private String id;

    @GridColumn(header = "Username")
    private String userName;

    @GridColumn(header = "First Name")
    private String firstName;

    @GridColumn(header = "Last Name")
    private String lastName;

    @GridColumn(header = "Email", flexGrow = 1)
    private String emailAddress;

    private String hashSalt;

    @GridColumn(header = "Phone")
    private String phone;

    private Role role;

    @JsonIgnore
    @GridColumn(header = "Role")
    private Component roleComponent;

    @JsonIgnore
    public Component getRoleComponent() {
      String value = getRole().toString();
      Badge badge = new Badge(value);
      UIUtil.setTooltip(value, badge);
      return badge;
    }
  }

  public static class Mapper extends CrudMapper<List, Edit> {
    @Override
    public User.Edit fromListToEdit(List l) {
      Edit edit = new Edit();
      BeanUtils.copyProperties(l, edit);
      return edit;
    }
  }
}
