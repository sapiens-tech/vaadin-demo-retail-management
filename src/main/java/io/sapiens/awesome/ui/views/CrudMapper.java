package io.sapiens.awesome.ui.views;

public abstract class CrudMapper<ListEntity, EditEntity> {
  public abstract EditEntity fromListToEdit(ListEntity l);
}
