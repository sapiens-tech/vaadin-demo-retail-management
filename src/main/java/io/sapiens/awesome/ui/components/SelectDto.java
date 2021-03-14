package io.sapiens.awesome.ui.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SelectDto {
  private List<SelectItem> items;
  private List<SelectItem> selected;

  @AllArgsConstructor
  @Getter
  public static class SelectItem {
    private Object value;
    private String label;
  }
}
