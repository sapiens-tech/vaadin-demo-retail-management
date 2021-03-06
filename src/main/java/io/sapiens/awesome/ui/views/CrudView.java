package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.Binder;
import io.sapiens.awesome.ui.components.FlexBoxLayout;
import io.sapiens.awesome.ui.components.Form;
import io.sapiens.awesome.ui.components.GridComponent;
import io.sapiens.awesome.ui.components.Toolbar;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawer;
import io.sapiens.awesome.ui.components.detailsdrawer.DetailsDrawerHeader;
import io.sapiens.awesome.ui.layout.size.Bottom;
import io.sapiens.awesome.ui.layout.size.Horizontal;
import io.sapiens.awesome.ui.layout.size.Top;
import io.sapiens.awesome.ui.util.css.BoxSizing;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

public abstract class CrudView<L, E, M extends CrudMapper<L, E>> extends SplitViewFrame {
  private DetailsDrawer detailsDrawer;
  @Getter @Setter private String detailTitle;

  private final Class<L> listEntity;
  private final Class<E> editEntity;
  private final M mapper;

  private GridComponent<L> grid;
  @Getter @Setter private FormLayout createOrUpdateForm;

  private Binder<E> binder;

  public CrudView(Class<L> listEntity, Class<E> editEntity, M mapper) {
    this.mapper = mapper;
    this.listEntity = listEntity;
    this.editEntity = editEntity;
    this.grid = createGrid(this.listEntity);
    this.binder = new Binder<>();
  }

  protected void setGridData(Collection<L> data) {
    grid.setData(data);
  }

  private GridComponent<L> createGrid(Class<L> listEntity) {
    GridComponent<L> grid = new GridComponent<>(listEntity);
    grid.addSelectListener(
        new SelectCallback<L>() {
          @Override
          public void trigger(L entity) {

            // we can query the entity again here
            showDetails(mapper.fromListToEdit(entity));
          }
        });
    return grid;
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

    toolbar.addEventListener(
        new Callback() {
          @Override
          public void trigger(ComponentEvent<?> event) {
            if (event.getSource() instanceof Button) {
              showDetails(null);
            }
          }
        });

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
    binder.setBean(entity);
    return new Form<>(
        this.editEntity,
        entity,
        binder,
        e -> onValidate((E) e),
        e -> onSave((E) e),
        e -> onDelete((E) e),
        e -> {
          onCancel();
          detailsDrawer.hide();
        });
  }

  public abstract void onInit();

  public abstract void onSave(E entity);

  public abstract void onDelete(E entity);

  public abstract void onCancel();

  public abstract void filter();

  public abstract List<String> onValidate(E entity);
}
