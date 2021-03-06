package io.sapiens.awesome.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.function.ValueProvider;
import io.sapiens.awesome.ui.annotations.FormElement;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.util.SystemUtil;
import io.sapiens.app.backend.dummy.DummyData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CssImport("./styles/components/form.css")
public class Form<T> extends FormLayout {
  public static final String FORM_HEADER = "form-header";
  @Getter @Setter private T entity;
  @Getter @Setter private Class<T> beanType;
  @Getter private final Binder<T> binder;
  private final IFormAction<T> onValidate;
  private final IFormAction<T> onSave;
  private final IFormAction<T> onCancel;
  private final IFormAction<T> onDelete;

  public Form(
      Class<T> clazz,
      T entity,
      Binder<T> binder,
      IFormAction<T> onValidate,
      IFormAction<T> onSave,
      IFormAction<T> onDelete,
      IFormAction<T> onCancel) {
    super();
    this.entity = entity;
    this.beanType = clazz;
    this.binder = binder;
    this.onValidate = onValidate;
    this.onDelete = onDelete;
    this.onSave = onSave;
    this.onCancel = onCancel;

    addClassNames(LumoStyles.Padding.Horizontal.M);
    setResponsiveSteps(
        new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
        new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

    setupForm(entity);
    add(setupButtons(this.entity));
  }

  private void setupForm(T entity) {
    List<FormItem> items = new ArrayList<>();
    for (var field : getBeanType().getDeclaredFields()) {
      if (field.isAnnotationPresent(FormElement.class)) {
        var annotation = field.getAnnotation(FormElement.class);
        var fieldName = field.getName();
        var util = SystemUtil.getInstance();
        if (!annotation.formSectionHeader().isEmpty()) {
          HorizontalLayout head = UIUtil.createFormSectionTitle(annotation.formSectionHeader());
          head.addClassNames(FORM_HEADER);
          add(head);
        }

        switch (annotation.type()) {
          case SelectField:
            setupSelectField(entity, items, annotation, fieldName, util);
            break;
          case DateField:
            setupDateField(items, annotation, fieldName, util);
            break;
          case PhoneField:
            setupPhoneField(items, annotation, fieldName, util);
            break;
          case FileField:
            var uploadItem = addFormItem(new Upload(), annotation.label());
            items.add(uploadItem);
            break;
          case Widget:
            setupWidgetField(entity, items, annotation, fieldName, util);
            break;
          case PasswordField:
          default:
            setupTextField(items, annotation, fieldName, util);
        }
      }
    }

    UIUtil.setColSpan(2, items.toArray(new Component[0]));
  }

  private void setupWidgetField(
      T entity, List<FormItem> items, FormElement annotation, String fieldName, SystemUtil util) {
    Component field = (Component) util.invokeGetter(entity, fieldName);
    FormItem item = addFormItem(field, annotation.label());
    items.add(item);

    if (field instanceof HasValue) {
      log.info("rendering has value");
    }
  }

  private void setupSelectField(
      T editEntity,
      List<FormItem> items,
      FormElement annotation,
      String fieldName,
      SystemUtil util) {
    Select<SelectDto.SelectItem> select = new Select<>();
    select.setWidthFull();
    SelectDto dto = (SelectDto) util.invokeGetter(editEntity, fieldName);
    List<SelectDto.SelectItem> selected = dto.getSelected();
    binder
        .forField(select)
        .bind(
            (ValueProvider<T, SelectDto.SelectItem>)
                t -> {
                  if (selected != null && !selected.isEmpty()) return selected.get(0);
                  return null;
                },
            (com.vaadin.flow.data.binder.Setter<T, SelectDto.SelectItem>)
                (t, a) -> {
                  selected.clear();
                  selected.add(a);
                  dto.setSelected(selected);
                  util.invokeSetter(editEntity, fieldName, dto);
                });

    select.setItems(((SelectDto) util.invokeGetter(editEntity, fieldName)).getItems());
    select.setItemLabelGenerator(SelectDto.SelectItem::getLabel);
    FormItem selectItem = addFormItem(select, annotation.label());
    items.add(selectItem);
  }

  private void setupDateField(
      List<FormItem> items, FormElement annotation, String fieldName, SystemUtil util) {
    var dateField = new DatePicker();
    dateField.setWidthFull();
    FormItem dateFieldItem = addFormItem(dateField, annotation.label());
    items.add(dateFieldItem);
    binder
        .forField(dateField)
        .bind(
            (ValueProvider<T, LocalDate>) t -> (LocalDate) util.invokeGetter(t, fieldName),
            (com.vaadin.flow.data.binder.Setter<T, LocalDate>)
                (t, localDate) -> util.invokeSetter(t, fieldName, localDate));
  }

  private void setupPhoneField(
      List<FormItem> items, FormElement annotation, String fieldName, SystemUtil util) {
    var phonePrefix = new TextField();
    phonePrefix.setValue("+358");
    phonePrefix.setWidth("80px");
    var phoneNumber = new TextField();
    phoneNumber.setValue(DummyData.getPhoneNumber());
    FlexBoxLayout layout = new FlexBoxLayout(phonePrefix, phoneNumber);
    layout.setFlexGrow(1, phoneNumber);
    layout.setSpacing(Right.S);

    var phoneItem = addFormItem(layout, annotation.label());
    items.add(phoneItem);
    binder
        .forField(phonePrefix)
        .bind(
            (ValueProvider<T, String>) t -> (String) util.invokeGetter(t, fieldName),
            (com.vaadin.flow.data.binder.Setter<T, String>)
                (t, s) -> util.invokeSetter(t, fieldName, s));
  }

  private HasValue getTextComponent(FormElement annotation) {
    switch (annotation.type()) {
      case PasswordField:
        PasswordField passwordField = new PasswordField();
        passwordField.setWidthFull();
        return passwordField;
      case TextAreaField:
        TextArea textArea = new TextArea();
        textArea.setWidthFull();
        return textArea;
      default:
        TextField textField = new TextField();
        textField.setWidthFull();
        return textField;
    }
  }

  private void setupTextField(
      List<FormItem> items, FormElement annotation, String fieldName, SystemUtil util) {

    HasValue formComponent = getTextComponent(annotation);
    var formItem = addFormItem((Component) formComponent, annotation.label());
    items.add(formItem);
    binder
        .forField(formComponent)
        .bind(
            (ValueProvider<T, String>) t -> (String) util.invokeGetter(t, fieldName),
            (com.vaadin.flow.data.binder.Setter<T, String>)
                (t, s) -> util.invokeSetter(t, fieldName, s));
  }

  private Button createSaveButton(T entity) {
    Button save = new Button("Save");
    save.setWidthFull();
    save.addClickListener(
        event -> {
          try {
            binder.writeBean(entity);
            onSave.execute(entity);
          } catch (ValidationException e) {
            log.error(e.getMessage());
          }
        });

    return save;
  }

  private Button createCancelButton(T entity) {
    Button cancel = new Button("Cancel");
    cancel.setWidthFull();
    cancel.addClickListener(buttonClickEvent -> onCancel.execute(entity));
    return cancel;
  }

  private Button createDeleteButton(T entity) {
    Button delete = UIUtil.createErrorPrimaryButton("Delete");
    delete.addClickListener(
        event -> {
          Dialog dialog = new Dialog();
          dialog.setWidth("400px");
          dialog.setCloseOnOutsideClick(false);

          Span message = new Span();
          message.setText("Are you sure you want to delete this record ?");
          message.setSizeFull();

          Button confirmButton = UIUtil.createErrorPrimaryButton("Confirm");
          confirmButton.addClickListener(
              e -> {
                onDelete.execute(entity);
                dialog.close();
              });
          Button cancelButton = new Button("Cancel", e -> dialog.close());
          HorizontalLayout flexLayout = new HorizontalLayout(confirmButton, cancelButton);
          flexLayout.setWidthFull();
          flexLayout.setMargin(true);
          flexLayout.setPadding(true);
          dialog.add(message, flexLayout);
          dialog.open();
        });

    return delete;
  }

  private Component setupButtons(T entity) {
    if (entity == null) {
      try {
        Constructor<T> cons = beanType.getDeclaredConstructor();
        entity = cons.newInstance();
      } catch (NoSuchMethodException
          | IllegalAccessException
          | InstantiationException
          | InvocationTargetException e) {
        log.error(e.getMessage());
        e.printStackTrace();
      }
    }

    Button save = createSaveButton(entity);
    Button cancel = createCancelButton(entity);
    HorizontalLayout actionButtons = new HorizontalLayout(save, cancel);
    actionButtons.setWidth("50%");

    HorizontalLayout buttons = new HorizontalLayout(actionButtons);
    if (entity != null) {
      Button delete = createDeleteButton(entity);
      FlexLayout layout = new FlexLayout(delete);
      layout.setWidthFull();
      layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
      buttons.add(layout);
    }

    buttons.setHeight("200");
    buttons.setMargin(true);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    UIUtil.setColSpan(2, buttons);
    return buttons;
  }
}
