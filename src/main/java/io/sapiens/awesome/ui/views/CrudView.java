package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

interface Statable {
  void addChangeListener(PropertyChangeListener newListener);
}

class StateObserver implements PropertyChangeListener {
  public StateObserver(State model) {
    model.addChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    System.out.println(
        "Changed property: "
            + event.getPropertyName()
            + " [old -> "
            + event.getOldValue()
            + "] | [new -> "
            + event.getNewValue()
            + "]");
  }
}

class State implements Statable {
  private List<PropertyChangeListener> listeners = new ArrayList<>();
  @Setter @Getter private boolean showDetails;

  @Override
  public void addChangeListener(PropertyChangeListener newListener) {
    listeners.add(newListener);
  }
}

class GridView<L> extends Grid<L> {
  private static final Logger logger = LoggerFactory.getLogger(GridView.class);
  @Getter @Setter private Class<L> beanType;
  @Getter @Setter private Collection<L> dataSet = new ArrayList<>();
  @Getter @Setter private ListDataProvider<L> dataProvider;

  public GridView(Class<L> beanType) {
    this.beanType = beanType;
    createGrid();
  }

  private void createGrid() {
    dataProvider = DataProvider.ofCollection(getDataSet());
    setSizeFull();
    setSelectionMode(Grid.SelectionMode.SINGLE);
    setItems(dataProvider);
    addSelectionListener(
        e -> e.getFirstSelectedItem().ifPresent(t -> logger.debug(String.valueOf(t))));

    for (Field field : beanType.getDeclaredFields()) {
      if (field.isAnnotationPresent(GridColumn.class)) {
        GridColumn annotation = field.getAnnotation(GridColumn.class);
        ComponentRenderer<Component, L> componentRenderer =
            new ComponentRenderer<>(
                t -> {
                  Object value = SystemUtil.getInstance().invokeGetter(t, field.getName());
                  if (value instanceof Component) {
                    return (Component) value;
                  }
                  return new Span(String.valueOf(value));
                });

        addColumn(componentRenderer)
            .setAutoWidth(annotation.autoWidth())
            .setFlexGrow(annotation.flexGrow())
            .setFrozen(annotation.frozen())
            .setHeader(annotation.header())
            .setSortable(annotation.sortable())
            .setTextAlign(annotation.textAlign());
      }
    }
  }
}

class Toolbar<E> extends FlexBoxLayout {
  @Setter @Getter private E entity;
  private static final Logger logger = LoggerFactory.getLogger(Toolbar.class);

  public Toolbar(State state) {
    Button newButton = new Button("New");
    newButton.addClickListener(event -> state.setShowDetails(true));

    add(newButton);
    setBoxSizing(BoxSizing.BORDER_BOX);
    setHeight("80");
    setFlexDirection(FlexLayout.FlexDirection.ROW_REVERSE);
    setPadding(Horizontal.RESPONSIVE_X, Top.M);
    newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    UIUtil.setColSpan(2, this);
  }
}

public abstract class CrudView<L, E, M extends CrudMapper<L, E>> extends SplitViewFrame {
  private State state = new State();
  private StateObserver observer = new StateObserver(state);
  private static final Logger log = LoggerFactory.getLogger(CrudView.class);
  private DetailsDrawer detailsDrawer;
  @Getter @Setter private FormLayout createOrUpdateForm;
  @Getter @Setter private String detailTitle;

  private final Class<L> listEntity;
  private final Class<E> editEntity;
  private final M mapper;

  public CrudView(Class<L> listEntity, Class<E> editEntity, M mapper) {
    this.mapper = mapper;
    this.listEntity = listEntity;
    this.editEntity = editEntity;
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    onInit();
    setViewContent(createToolbar(), createContent());
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

  private Component createToolbar() {
    return new Toolbar<E>(state);
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(new GridView<L>(listEntity));
    content.setBoxSizing(BoxSizing.BORDER_BOX);
    content.setHeightFull();
    content.setPadding(Horizontal.RESPONSIVE_X, Top.S, Bottom.M);
    return content;
  }

  private void showDetails(E person) {
    detailsDrawer.setContent(createDetails(person));
    detailsDrawer.show();
  }

  private Component createDetails(E entity) {
    return createEditor(entity);
  }

  private FormLayout createEditor(E entity) {
    // binder.setBean(entity);
    // return new Form<>(clazz, entity, binder, setupButtons(entity));
    return new FormLayout();
  }

  public abstract void onInit();

  public abstract void onSave(E entity);

  public abstract void onDelete(E entity);

  public abstract void onCancel();

  public abstract void filter();

  public abstract List<String> onValidate(E entity);
}
