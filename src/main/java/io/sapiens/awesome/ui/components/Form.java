package io.sapiens.awesome.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.ValueProvider;
import io.sapiens.awesome.ui.annotations.FormField;
import io.sapiens.awesome.ui.layout.size.Right;
import io.sapiens.awesome.ui.util.LumoStyles;
import io.sapiens.awesome.ui.util.UIUtils;
import io.sapiens.awesome.util.SystemUtil;
import io.sapiens.retail.backend.dummy.DummyData;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Form<T> extends FormLayout {
  private Logger log = LoggerFactory.getLogger(Form.class);
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
    log.debug(String.valueOf(entity));

    for (var field : getBeanType().getDeclaredFields()) {
      if (field.isAnnotationPresent(FormField.class)) {
        var annotation = field.getAnnotation(FormField.class);
        var fieldName = field.getName();
        var util = SystemUtil.getInstance();

        switch (annotation.type()) {
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
          default:
            setupTextField(items, annotation, fieldName, util);
        }
      }
    }
    UIUtils.setColSpan(2, items.toArray(new Component[0]));
  }

  private void setupDateField(
      List<FormItem> items, FormField annotation, String fieldName, SystemUtil util) {
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
      List<FormItem> items, FormField annotation, String fieldName, SystemUtil util) {
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

  private void setupTextField(
      List<FormItem> items, FormField annotation, String fieldName, SystemUtil util) {
    var textField = new TextField();
    var textFieldItem = addFormItem(textField, annotation.label());
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
