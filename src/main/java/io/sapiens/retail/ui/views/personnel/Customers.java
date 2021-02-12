package io.sapiens.retail.ui.views.personnel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import infra.ui.views.CrudView;
import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.services.CustomerService;
import io.sapiens.retail.ui.MainLayout;
import io.sapiens.retail.ui.models.Person;
import io.sapiens.retail.ui.util.LumoStyles;
import io.sapiens.retail.ui.util.UIUtils;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers")
public class Customers extends CrudView<Person> {
  public Customers() {
    super(DummyData.getPersons());
    setCreateOrUpdateForm(new CustomerForm());
    setDetailTitle("Customer Details");
  }

  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {
    getDataProvider().setFilterByValue(Person::getRole, Role.ACCOUNTANT);
  }

  static class CustomerForm extends FormLayout {

    public CustomerForm() {
      super();
      DatePicker birthDate = new DatePicker();
      birthDate.setWidthFull();

      TextField firstName = new TextField();
      firstName.setWidthFull();

      TextField lastName = new TextField();
      lastName.setWidthFull();

      RadioButtonGroup<String> status = new RadioButtonGroup<>();
      status.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
      status.setItems("Active", "Inactive");

      FlexLayout phone = UIUtils.createPhoneLayout();

      TextField email = new TextField();
      email.setWidthFull();

      ComboBox<String> company = new ComboBox<>();
      company.setItems(DummyData.getCompanies());
      company.setValue(DummyData.getCompany());
      company.setWidthFull();

      addClassNames(
          LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
      setResponsiveSteps(
          new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
          new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
      addFormItem(firstName, "First Name");
      addFormItem(lastName, "Last Name");

      FormLayout.FormItem dobItem = addFormItem(birthDate, "Birthdate");
      FormLayout.FormItem statusItem = addFormItem(status, "Status");
      FormLayout.FormItem phoneItem = addFormItem(phone, "Phone");
      FormLayout.FormItem emailItem = addFormItem(email, "Email");
      FormLayout.FormItem companyItem = addFormItem(company, "Company");
      FormLayout.FormItem uploadItem = addFormItem(new Upload(), "Image");

      Button save = new Button("Save");
      save.addClickListener(
          buttonClickEvent -> {
            CustomerService customerService = new CustomerService();
            customerService.save(new Person());
          });

      Button delete = new Button("Delete");

      HorizontalLayout buttons = new HorizontalLayout(save, delete);
      buttons.setHeight("200");
      buttons.setMargin(true);
      save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

      UIUtils.setColSpan(
          2, statusItem, phoneItem, emailItem, companyItem, uploadItem, dobItem, buttons);

      add(buttons);
    }
  }
}
