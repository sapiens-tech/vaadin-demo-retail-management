package io.sapiens.awesome.ui.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectDto implements ISelectable {
  private Object value;
  private String label;
  private boolean selected;
}
