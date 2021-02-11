package infra.ui;

import io.sapiens.retail.ui.views.ViewFrame;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

public class ListView<T> extends ViewFrame {
  private Grid<T> grid;
  private ListDataProvider<T> dataProvider;

  private Grid<T> createGrid() {
    return null;
  }
}
