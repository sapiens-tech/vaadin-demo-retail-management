package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.*;
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
import com.vaadin.flow.server.*;
import com.vaadin.flow.shared.Registration;
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
import org.rapidpm.frp.model.Pair;
import org.rapidpm.frp.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Tool;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

class DemoComponentRegistry extends Registry<DemoComponentRegistry.ValueEvent> {

  public static class ValueEvent extends Pair<String, String> {

    public ValueEvent(String id, String value) {
      super(id, value);
    }

    public String id() {
      return getT1();
    }

    public String value() {
      return getT2();
    }
  }
}

class RegistryServiceInitListener implements VaadinServiceInitListener, UIInitListener {

  @Override
  public void serviceInit(ServiceInitEvent serviceInitEvent) {
    serviceInitEvent.getSource().addUIInitListener(this);
  }

  @Override
  public void uiInit(UIInitEvent uiInitEvent) {
    final VaadinSession session = uiInitEvent.getUI().getSession();
    session.setAttribute(DemoComponentRegistry.class, null);
    session.setAttribute(DemoComponentRegistry.class, new DemoComponentRegistry());
  }
}

class Registry<VALUE> {

  private final Set<Consumer<VALUE>> listeners = ConcurrentHashMap.newKeySet();

  public Registration register(Consumer<VALUE> listener) {
    listeners.add(listener);
    return () -> listeners.remove(listener);
  }

  public void sentEvent(VALUE event) {
    listeners.forEach(listener -> listener.accept(event));
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

  public void setData(Collection<L> data) {
    setItems(data);
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

  public Registration addChangeListener(
          ComponentEventListener<ToolbarEvent> listener) {
    return addListener(ToolbarEvent.class, listener);
  }

  public Toolbar() {
    Button newButton = new Button("New");
    newButton.addClickListener(event -> {
    });

    add(newButton);
    setBoxSizing(BoxSizing.BORDER_BOX);
    setHeight("80");
    setFlexDirection(FlexLayout.FlexDirection.ROW_REVERSE);
    setPadding(Horizontal.RESPONSIVE_X, Top.M);
    newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    UIUtil.setColSpan(2, this);
  }
}

class ToolbarEvent extends ComponentEvent<Component> {

  public ToolbarEvent(Component source, boolean fromClient) {
    super(source, fromClient);
  }
}

public abstract class CrudView<L, E, M extends CrudMapper<L, E>> extends SplitViewFrame {
  private static final Logger log = LoggerFactory.getLogger(CrudView.class);
  private DetailsDrawer detailsDrawer;
  @Getter @Setter private FormLayout createOrUpdateForm;
  @Getter @Setter private String detailTitle;

  private final Class<L> listEntity;
  private final Class<E> editEntity;
  private final M mapper;

  private GridView<L> grid;

  public CrudView(Class<L> listEntity, Class<E> editEntity, M mapper) {
    this.mapper = mapper;
    this.listEntity = listEntity;
    this.editEntity = editEntity;
    this.grid = new GridView<>(listEntity);
  }

  protected void setGridData(Collection<L> data) {
    grid.setData(data);
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
    Toolbar<E> toolbar = new Toolbar<>();
    Registration reg = toolbar.addChangeListener(e -> {
      System.out.println("event on toolbar" + e.getSource());
    });

    System.out.println();
    return toolbar;
  }

  private Component createContent() {
    FlexBoxLayout content = new FlexBoxLayout(grid);
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
