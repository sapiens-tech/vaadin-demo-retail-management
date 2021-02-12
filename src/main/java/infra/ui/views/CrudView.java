package infra.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import io.sapiens.retail.ui.components.FlexBoxLayout;
import io.sapiens.retail.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.retail.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.retail.ui.layout.size.Horizontal;
import io.sapiens.retail.ui.layout.size.Top;
import io.sapiens.retail.ui.util.css.BoxSizing;
import lombok.Getter;
import lombok.Setter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class CrudView<T> extends SplitViewFrame {

  private final Class<T> beanType;

  @Getter @Setter private ListDataProvider<T> dataProvider;
  private DetailsDrawer detailsDrawer;

  @Getter @Setter private Collection<T> dataSet;

  @Getter @Setter private Grid<T> grid;

  @Getter @Setter private FormLayout createOrUpdateForm;

  @Getter @Setter private String detailTitle;



  public CrudView(Collection<T> data) {
    beanType =
        (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    dataProvider = DataProvider.ofCollection(data);
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
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

  private FormLayout createEditor(T entity) {
    return createOrUpdateForm;
  }

  private Component createDetails(T entity) {
    return createEditor(entity);
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
}
