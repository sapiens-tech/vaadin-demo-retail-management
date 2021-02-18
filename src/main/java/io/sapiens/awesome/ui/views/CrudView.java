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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.awesome.ui.layout.size.Bottom;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.util.SystemUtil;
import io.sapiens.retail.backend.dummy.DummyData;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CrudView<T> extends SplitViewFrame {

  private static final Logger log = LoggerFactory.getLogger(CrudView.class);
  private final Class<T> beanType;
  private DetailsDrawer detailsDrawer;
  @Getter private Binder<T> binder;

  @Getter @Setter private ListDataProvider<T> dataProvider;
  @Getter @Setter private Collection<T> dataSet = new ArrayList<>();
  @Getter @Setter private Grid<T> grid;
  @Getter @Setter private FormLayout createOrUpdateForm;
  @Getter @Setter private String detailTitle;

  public CrudView() {
    this.beanType =
        (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    this.binder = new Binder<>(this.beanType);
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    onInit();
    setViewContent(createUtilButton(), createContent());
    setViewDetails(createDetailsDrawer());
    filter();
  }

  public Component createUtilButton() {
    Button save = new Button("New");
    save.addClickListener(
        buttonClickEvent -> {
          showDetails(null);
        });

    FlexBoxLayout content = new FlexBoxLayout(save);
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeight("80");
    content.setFlexDirection(FlexLayout.FlexDirection.ROW_REVERSE);
    content.setPadding(Horizontal.RESPONSIVE_X, Top.M);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    UIUtils.setColSpan(2, content);
    return content;
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
    content.setPadding(Horizontal.RESPONSIVE_X, Top.S, Bottom.M);
    return content;
  }

  private void showDetails(T person) {
    detailsDrawer.setContent(createDetails(person));
    detailsDrawer.show();
  }

  private Component createDetails(T entity) {
    return createEditor(beanType, entity);
  }

  private FormLayout createEditor(Class<T> clazz, T entity) {
    return new Form<>(clazz, entity, binder, setupButtons(entity));
  }

  private Grid<T> createGrid() {
    dataProvider = DataProvider.ofCollection(getDataSet());
    grid = new Grid<>();
    grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    grid.setItems(dataProvider);
    grid.setSizeFull();
    grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));

    for (Field field : beanType.getDeclaredFields()) {
      if (field.isAnnotationPresent(GridColumn.class)) {
        GridColumn annotation = field.getAnnotation(GridColumn.class);
        // new ComponentRenderer<>(this::createToInfo)
        grid.addColumn(t -> SystemUtil.getInstance().invokeGetter(t, field.getName()))
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

  public abstract void onInit();

  public abstract void onSave(T entity);

  public abstract void onDelete();

  public abstract void onCancel();

  public abstract void filter();

  private Component setupButtons(T entity) {
    Button save = new Button("Save");
    save.addClickListener(
        event -> {
          onSave(binder.getBean());
        });

    HorizontalLayout buttons = new HorizontalLayout(save);
    if (entity != null) {
      Button delete = new Button("Delete");
      buttons.add(delete);
    }

    buttons.setHeight("200");
    buttons.setMargin(true);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    UIUtils.setColSpan(2, buttons);
    return buttons;
  }

  static class Form<T> extends FormLayout {
    @Getter @Setter private T entity;
    @Getter @Setter private Class<T> beanType;
    @Getter private final Binder<T> binder;

    public Form(Class<T> clazz, T entity, Binder<T> binder, Component buttons) {
      super();
      this.entity = entity;
      this.beanType = clazz;
      this.binder = binder;

      addClassNames(
          LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
      setResponsiveSteps(
          new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
          new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

      setupForm(entity);
      add(buttons);
    }

    private void setupForm(T entity) {
      List<FormItem> items = new ArrayList<>();

      for (Field field : getBeanType().getDeclaredFields()) {
        if (field.isAnnotationPresent(FormField.class)) {
          var annotation = field.getAnnotation(FormField.class);
          var fieldName = field.getName();
          var util = SystemUtil.getInstance();

          switch (annotation.type()) {
            case DateField:
              DatePicker dateField = new DatePicker();
              dateField.setWidthFull();
              FormItem dateFieldItem = addFormItem(dateField, annotation.label());
              items.add(dateFieldItem);
              binder
                  .forField(dateField)
                  .bind(
                      (ValueProvider<T, LocalDate>)
                          t -> (LocalDate) util.invokeGetter(t, fieldName),
                      (com.vaadin.flow.data.binder.Setter<T, LocalDate>)
                          (t, localDate) -> util.invokeSetter(t, fieldName, localDate));

              break;
            case PhoneField:
              TextField phonePrefix = new TextField();
              phonePrefix.setValue("+358");
              phonePrefix.setWidth("80px");
              TextField phoneNumber = new TextField();
              phoneNumber.setValue(DummyData.getPhoneNumber());
              FlexBoxLayout layout = new FlexBoxLayout(phonePrefix, phoneNumber);
              layout.setFlexGrow(1, phoneNumber);
              layout.setSpacing(Right.S);

              FormItem phoneItem = addFormItem(layout, annotation.label());
              items.add(phoneItem);
              binder
                  .forField(phonePrefix)
                  .bind(
                      (ValueProvider<T, String>) t -> (String) util.invokeGetter(t, fieldName),
                      (com.vaadin.flow.data.binder.Setter<T, String>)
                          (t, s) -> util.invokeSetter(t, fieldName, s));
              break;
            case FileField:
              FormItem uploadItem = addFormItem(new Upload(), annotation.label());
              items.add(uploadItem);
              break;
            default:
              TextField textField = new TextField();
              FormItem textFieldItem = addFormItem(textField, annotation.label());
              textField.setWidthFull();
              items.add(textFieldItem);
              binder
                  .forField(textField)
                  .bind(
                      (ValueProvider<T, String>) t -> (String) util.invokeGetter(t, fieldName),
                      (com.vaadin.flow.data.binder.Setter<T, String>)
                          (t, s) -> util.invokeSetter(t, fieldName, s));
          }
        }
      }
      UIUtils.setColSpan(2, items.toArray(new Component[0]));
    }
  }
}
