package io.sapiens.retail.ui.views.personnel;

import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.backend.Person;
import io.sapiens.retail.ui.MainLayout;
import io.sapiens.retail.ui.components.FlexBoxLayout;
import io.sapiens.retail.ui.components.Initials;
import io.sapiens.retail.ui.components.ListItem;
import io.sapiens.retail.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.retail.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.retail.ui.layout.size.Horizontal;
import io.sapiens.retail.ui.layout.size.Right;
import io.sapiens.retail.ui.layout.size.Top;
import io.sapiens.retail.ui.layout.size.Vertical;
import io.sapiens.retail.ui.util.LumoStyles;
import io.sapiens.retail.ui.util.UIUtils;
import io.sapiens.retail.ui.util.css.BoxSizing;
import io.sapiens.retail.ui.views.SplitViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers")
public class Customers extends SplitViewFrame {

  private Grid<Person> grid;
  private ListDataProvider<Person> dataProvider;
  private DetailsDrawer detailsDrawer;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    setViewContent(createContent());
    setViewDetails(createDetailsDrawer());
    filter();
  }

  private DetailsDrawer createDetailsDrawer() {
    detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    DetailsDrawerHeader detailsDrawerHeader =
        new DetailsDrawerHeader("Customer information", new Div());
    detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
    detailsDrawer.setHeader(detailsDrawerHeader);

    return detailsDrawer;
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(createGrid());
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
    return content;
  }

  private void showDetails(Person person) {
    detailsDrawer.setContent(createDetails(person));
    detailsDrawer.show();
  }

  private Component createDetails(Person person) {
    return createEditor(person);
  }

  private Grid<Person> createGrid() {
    grid = new Grid<>();
    grid.setSelectionMode(SelectionMode.SINGLE);

    dataProvider = DataProvider.ofCollection(DummyData.getPersons());
    grid.setDataProvider(dataProvider);
    grid.setHeightFull();
    grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));

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
    grid.addColumn(new ComponentRenderer<>(this::createLastLoginDate))
        .setFlexGrow(0)
        .setAutoWidth(true)
        .setFlexGrow(0)
        .setHeader("Last Login")
        .setTextAlign(ColumnTextAlign.END);

    return grid;
  }

  private Component createUserInfo(Person person) {
    ListItem item =
        new ListItem(new Initials(person.getInitials()), person.getName(), person.getEmail());
    item.setPadding(Vertical.XS);
    item.setSpacing(Right.M);
    return item;
  }

  private Component createActive(Person person) {
    Icon icon;
    if (person.getRandomBoolean()) {
      icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
    } else {
      icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
    }
    return icon;
  }

  private Component createLastLoginDate(Person person) {
    return new Span(UIUtils.formatDate(person.getLastModified()));
  }

  private FormLayout createEditor(Person person) {
    return new CustomerForm();
  }

  private void filter() {
    dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
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
      Button delete = new Button("Delete");

      HorizontalLayout buttons = new HorizontalLayout(save, delete);
      buttons.setHeight("200");
      buttons.setMargin(true);
      save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

      UIUtils.setColSpan(2, statusItem, phoneItem, emailItem, companyItem, uploadItem, dobItem, buttons);

      add(buttons);
    }
  }
}
