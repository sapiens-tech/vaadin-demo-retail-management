package io.sapiens.awesome.ui.views;

import com.vaadin.flow.component.ComponentEvent;

public abstract class Callback {
  public abstract void trigger(ComponentEvent<?> event);
}
