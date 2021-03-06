package io.sapiens.awesome.ui.components;

public interface IFormAction<E> {
  void execute(E entity);
}
