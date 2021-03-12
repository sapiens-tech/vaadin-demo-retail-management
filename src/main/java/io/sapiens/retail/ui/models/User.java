package io.sapiens.retail.ui.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.Component;
import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.Badge;
import io.sapiens.awesome.ui.enums.FormElementType;
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

    @FormElement(
        type = FormElementType.TextField,
        label = "Username",
        formSectionHeader = "Authentication")
    private String userName;

    @FormElement(type = FormElementType.PasswordField, label = "Password")
    private String password;

    @FormElement(type = FormElementType.SelectField, label = "Role")
    private Role role;

    @FormElement(
        type = FormElementType.TextField,
        label = "First name",
        formSectionHeader = "Basic Information")
    private String firstName;

    @FormElement(type = FormElementType.TextField, label = "Last name")
    private String lastName;

    @FormElement(type = FormElementType.TextField, label = "Email")
    private String emailAddress;

    @FormElement(type = FormElementType.TextField, label = "Phone")
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
