package io.sapiens.awesome.service;

import io.sapiens.awesome.dao.AbstractDao;
import io.sapiens.awesome.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractService<M extends AbstractModel> {
  protected AbstractDao<M> dao;

  public void setDao(@Autowired AbstractDao<M> dao) {
    this.dao = dao;
  }

  public M saveOrUpdate(M entity) {
    return dao.saveOrUpdate(entity);
  }

  public void delete(String id) {
    dao.deleteById(id);
  }
}
