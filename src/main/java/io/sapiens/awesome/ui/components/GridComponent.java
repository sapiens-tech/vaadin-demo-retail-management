package io.sapiens.awesome.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import io.sapiens.awesome.ui.annotations.GridColumn;
import io.sapiens.awesome.ui.views.Callback;
import io.sapiens.awesome.ui.views.SelectCallback;
import io.sapiens.awesome.util.SystemUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class GridComponent<L> extends Grid<L> implements IEventListener, ISelectListener {
  @Getter @Setter private Class<L> beanType;
  @Getter @Setter private Collection<L> dataSet = new ArrayList<>();
  @Getter @Setter private ListDataProvider<L> dataProvider;

  private Callback callback;
  private SelectCallback<L> selectCallback;

  public GridComponent(Class<L> beanType) {
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
        e -> e.getFirstSelectedItem().ifPresent(t -> this.selectCallback.trigger(t)));

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

  @Override
  public void addEventListener(Callback callback) {
    this.callback = callback;
  }

  @Override
  public void addSelectListener(SelectCallback callback) {
    this.selectCallback = callback;
  }
}
