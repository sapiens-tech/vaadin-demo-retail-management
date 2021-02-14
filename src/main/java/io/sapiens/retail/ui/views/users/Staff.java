package io.sapiens.retail.ui.views.users;

import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.ui.models.Person;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.ui.SidebarLayout;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.Initials;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.ui.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "staffs", layout = SidebarLayout.class)
@PageTitle("Staffs")
public class Staff extends ViewFrame {

  private Grid<Person> grid;
  private ListDataProvider<Person> dataProvider;

  public Staff() {
    setViewContent(createContent());
    filter();
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(createGrid());
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
    return content;
  }

  private Grid<Person> createGrid() {
    grid = new Grid<>();
    dataProvider = DataProvider.ofCollection(DummyData.getPersons());
    grid.setDataProvider(dataProvider);
    grid.setSizeFull();

    grid.addColumn(Person::getId)
        .setAutoWidth(true)
        .setFlexGrow(0)
        .setFrozen(true)
        .setHeader("ID")
        .setSortable(true);
    grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
        .setAutoWidth(true)
        .setHeader("Name");
    grid.addColumn(new ComponentRenderer<>(this::createActive))
        .setAutoWidth(true)
        .setFlexGrow(0)
        .setHeader("Active")
        .setTextAlign(ColumnTextAlign.END);
    grid.addColumn(new ComponentRenderer<>(this::createRole))
            .setAutoWidth(true)
            .setFlexGrow(0)
            .setHeader("Role")
            .setTextAlign(ColumnTextAlign.END);
    grid.addColumn(new ComponentRenderer<>(this::createLastLoginDate))
        .setAutoWidth(true)
        .setFlexGrow(0)
        .setHeader("Last Login")
        .setTextAlign(ColumnTextAlign.END);

    return grid;
  }

  private Component createActive(Person person) {
    Icon icon;
    if (person.isRandomBoolean()) {
      icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
    } else {
      icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
    }
    return icon;
  }

  private Component createUserInfo(Person person) {
    ListItem item =
        new ListItem(new Initials(person.getInitials()), person.getName(), person.getEmail());
    item.setPadding(Vertical.XS);
    item.setSpacing(Right.M);
    return item;
  }

  private Component createRole(Person person) {
    return new Span(person.getRole().toString());
  }

  private Component createApprovalLimit(Person person) {
    int amount = person.getRandomInteger() > 0 ? person.getRandomInteger() : 0;
    return UIUtils.createAmountLabel(amount);
  }

  private Component createLastLoginDate(Person person) {
    return new Span(UIUtils.formatDate(person.getLastModified()));
  }

  private BinderCrudEditor<Person> createEditor() {
    Binder<Person> binder = new Binder<>(Person.class);

    TextField firstName = new TextField();
    firstName.setWidthFull();
    binder.bind(firstName, "firstName");

    TextField lastName = new TextField();
    lastName.setWidthFull();
    binder.bind(lastName, "lastName");

    RadioButtonGroup<String> status = new RadioButtonGroup<>();
    status.setItems("Active", "Inactive");
    binder.bind(
        status,
        (person) -> person.isRandomBoolean() ? "Active" : "Inactive",
        (person, value) -> person.setRandomBoolean(value.equals("Active")));

    FlexLayout phone = UIUtils.createPhoneLayout();

    TextField email = new TextField();
    email.setWidthFull();
    binder.bind(email, "email");

    ComboBox<String> company = new ComboBox<>();
    company.setItems(DummyData.getCompanies());
    company.setValue(DummyData.getCompany());
    company.setWidthFull();

    // Form layout
    FormLayout form = new FormLayout();
    form.addClassNames(
        LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
    form.setResponsiveSteps(
        new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
        new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
        new FormLayout.ResponsiveStep("1024px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP));
    form.addFormItem(firstName, "First Name");
    form.addFormItem(lastName, "Last Name");
    form.addFormItem(status, "Status");
    form.addFormItem(phone, "Phone");
    form.addFormItem(email, "Email");
    form.addFormItem(company, "Company");
    form.addFormItem(new Upload(), "Image");
    return new BinderCrudEditor<>(binder, form);
  }

  private void filter() {
    dataProvider.setFilterByValue(Person::getRole, Role.MANAGER);
  }
}
