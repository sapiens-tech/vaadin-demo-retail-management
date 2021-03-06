package io.sapiens.awesome.ui.components;

import io.sapiens.awesome.ui.views.SelectCallback;

public interface ISelectListener<L> {
  void addSelectListener(SelectCallback<L> event);
}
