package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.retail.backend.services.CustomerService;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.retail.ui.models.Person;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import lombok.Getter;
import lombok.Setter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CrudView<T> extends SplitViewFrame {

  private final Class<T> beanType;

  @Getter @Setter private ListDataProvider<T> dataProvider;
  private DetailsDrawer detailsDrawer;

  @Getter @Setter private Collection<T> dataSet;

  @Getter @Setter private Grid<T> grid;

  @Getter @Setter private FormLayout createOrUpdateForm;

  @Getter @Setter private String detailTitle;

  public CrudView() {
    beanType =
        (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    dataProvider = DataProvider.ofCollection(getDataSet());
    setViewContent(createContent());
    setViewDetails(createDetailsDrawer());
    filter();
  }

  private DetailsDrawer createDetailsDrawer() {
    detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

    DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader(getDetailTitle(), new Div());
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

  private void showDetails(T person) {
    detailsDrawer.setContent(createDetails(person));
    detailsDrawer.show();
  }

  private Component createDetails(T entity) {
    return createEditor(entity);
  }

  private FormLayout createEditor(T entity) {

    return new Form<>(entity);
  }

  public Object invokeGetter(Object obj, String variableName) {
    try {
      PropertyDescriptor pd = new PropertyDescriptor(variableName, obj.getClass());
      Method getter = pd.getReadMethod();
      return getter.invoke(obj);
    } catch (IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | IntrospectionException e) {
      e.printStackTrace();
    }

    return null;
  }

  private Grid<T> createGrid() {
    grid = new Grid<>();
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.setDataProvider(dataProvider);
    grid.setHeightFull();
    grid.setWidthFull();
    grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));

    for (Field field : beanType.getDeclaredFields()) {
      if (field.isAnnotationPresent(GridColumn.class)) {
        GridColumn annotation = field.getAnnotation(GridColumn.class);
        grid.addColumn(t -> invokeGetter(t, field.getName()))
            .setAutoWidth(annotation.autoWidth())
            .setFlexGrow(annotation.flexGrow())
            .setFrozen(annotation.frozen())
            .setHeader(annotation.header())
            .setSortable(annotation.sortable())
            .setTextAlign(annotation.textAlign());
      }
    }

    return grid;
  }

  public abstract void onSave();

  public abstract void onDelete();

  public abstract void onCancel();

  public abstract void filter();

  static class Form<T> extends FormLayout {
    @Getter @Setter private T entity;

    public Form(T entity) {
      super();
      this.entity = entity;

      addClassNames(
          LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
      setResponsiveSteps(
          new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
          new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

      List<FormItem> items = new ArrayList<>();
      for (Field field : getEntity().getClass().getDeclaredFields()) {
        if (field.isAnnotationPresent(FormField.class)) {
          FormField annotation = field.getAnnotation(FormField.class);

          switch (annotation.type()) {
            case DateField:
              DatePicker dateField = new DatePicker();
              dateField.setWidthFull();
              FormLayout.FormItem dateFieldItem = addFormItem(dateField, annotation.label());
              items.add(dateFieldItem);
              break;
            case PhoneField:
              FlexLayout phone = UIUtils.createPhoneLayout();
              FormLayout.FormItem phoneItem = addFormItem(phone, annotation.label());
              items.add(phoneItem);
              break;
            case FileField:
              FormLayout.FormItem uploadItem = addFormItem(new Upload(), annotation.label());
              items.add(uploadItem);
              break;
            default:
              TextField textField = new TextField();
              FormLayout.FormItem textFieldItem = addFormItem(textField, annotation.label());
              textField.setWidthFull();
              items.add(textFieldItem);
          }
        }
      }

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

      UIUtils.setColSpan(2, items.toArray(new Component[0]));
      UIUtils.setColSpan(2, buttons);

      add(buttons);
    }
  }
}
