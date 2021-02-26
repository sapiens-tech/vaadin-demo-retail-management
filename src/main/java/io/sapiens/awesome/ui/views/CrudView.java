package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.Form;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.awesome.ui.layout.size.Bottom;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.util.UIUtil;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import io.sapiens.awesome.util.SystemUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CrudView<T> extends SplitViewFrame {

  private static final Logger log = LoggerFactory.getLogger(CrudView.class);
  private final Class<T> beanType;
  private DetailsDrawer detailsDrawer;
  @Getter private final Binder<T> binder;

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
    Button newButton = new Button("New");
    newButton.addClickListener(
        buttonClickEvent -> {
          T entity;
          try {
            entity = this.beanType.getDeclaredConstructor(null).newInstance(null);
            showDetails(entity);
          } catch (InstantiationException
              | IllegalAccessException
              | InvocationTargetException
              | NoSuchMethodException e) {
            e.printStackTrace();
            log.error(e.getMessage());
          }
        });

    FlexBoxLayout content = new FlexBoxLayout(newButton);
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeight("80");
    content.setFlexDirection(FlexLayout.FlexDirection.ROW_REVERSE);
    content.setPadding(Horizontal.RESPONSIVE_X, Top.M);
    newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    UIUtil.setColSpan(2, content);
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
    binder.setBean(entity);
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

        grid.addColumn(
                new ComponentRenderer<>(
                    (t) -> {
                      Object value = SystemUtil.getInstance().invokeGetter(t, field.getName());
                      if (value instanceof Component) {
                        return (Component) value;
                      }
                      return new Span(String.valueOf(value));
                    }))
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

  public abstract List<String> onValidate(T entity);

  private Button createSaveButton(T entity) {
    Button save = new Button("Save");
    save.setWidthFull();
    save.addClickListener(
        event -> {
          try {
            binder.writeBean(entity);
            List<String> errors = onValidate(entity);

            if (errors != null && !errors.isEmpty()) {
              binder.setValidationStatusHandler(binderValidationStatus -> {});
            }
            onSave(entity);
          } catch (ValidationException e) {
            log.error(e.getMessage());
          }
        });

    return save;
  }

  private Button createCancelButton() {
    Button cancel = new Button("Cancel");
    cancel.setWidthFull();
    cancel.addClickListener(buttonClickEvent -> detailsDrawer.hide());
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
                onDelete();
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
  }

  private Component setupButtons(T entity) {
    Button save = createSaveButton(entity);
    Button cancel = createCancelButton();
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
