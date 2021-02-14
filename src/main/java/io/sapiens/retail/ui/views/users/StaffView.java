package io.sapiens.retail.ui.views.users;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.sapiens.awesome.ui.components.Initials;
import io.sapiens.awesome.ui.components.ListItem;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Vertical;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.views.CrudView;
import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.ui.BaseLayout;
import io.sapiens.retail.ui.models.Person;

@Route(value = "staffs", layout = BaseLayout.class)
@PageTitle("Staffs")
@org.springframework.stereotype.Component
public class StaffView extends CrudView<Person> {

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    setDataSet(DummyData.getPersons());
    setDetailTitle("Staff Details");
  }

  @Override
  public void onSave() {}

  @Override
  public void onDelete() {}

  @Override
  public void onCancel() {}

  @Override
  public void filter() {}

  //    @Override
  //    protected Grid<Person> createGrid() {
  //      grid = new Grid<>();
  //      dataProvider = DataProvider.ofCollection(DummyData.getPersons());
  //      grid.setItems(dataProvider);
  //      grid.setSizeFull();
  //
  //      grid.addColumn(Person::getId)
  //          .setAutoWidth(true)
  //          .setFlexGrow(0)
  //          .setFrozen(true)
  //          .setHeader("ID")
  //          .setSortable(true);
  //      grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
  //          .setAutoWidth(true)
  //          .setHeader("Name");
  //      grid.addColumn(new ComponentRenderer<>(this::createActive))
  //          .setAutoWidth(true)
  //          .setFlexGrow(0)
  //          .setHeader("Active")
  //          .setTextAlign(ColumnTextAlign.END);
  //      grid.addColumn(new ComponentRenderer<>(this::createRole))
  //          .setAutoWidth(true)
  //          .setFlexGrow(0)
  //          .setHeader("Role")
  //          .setTextAlign(ColumnTextAlign.END);
  //      grid.addColumn(new ComponentRenderer<>(this::createLastLoginDate))
  //          .setAutoWidth(true)
  //          .setFlexGrow(0)
  //          .setHeader("Last Login")
  //          .setTextAlign(ColumnTextAlign.END);
  //
  //      return grid;
  //    }

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

  private Component createLastLoginDate(Person person) {
    return new Span(UIUtils.formatDate(person.getLastModified()));
  }
}
