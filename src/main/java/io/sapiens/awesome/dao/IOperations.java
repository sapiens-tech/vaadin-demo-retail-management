package io.sapiens.awesome.dao;

import io.sapiens.awesome.model.AbstractModel;

import java.util.List;

public interface IOperations<T extends AbstractModel> {

  T findOne(final String id);

  List<T> findAll();

  T save(final T entity);

  T update(final T entity);

  void delete(final T entity);

  void deleteById(final String entityId);
}
