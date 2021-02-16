package io.sapiens.awesome.dao;

import io.sapiens.awesome.model.AbstractModel;

import java.util.List;

public interface IOperations<T extends AbstractModel> {

  T findOne(final long id);

  List<T> findAll();

  void create(final T entity);

  T update(final T entity);

  void delete(final T entity);

  void deleteById(final long entityId);
}
