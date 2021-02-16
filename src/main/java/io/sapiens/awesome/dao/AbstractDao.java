package io.sapiens.awesome.dao;

import io.sapiens.awesome.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<T extends AbstractModel> implements IOperations<T> {

  protected Class<T> clazz;
  private EntityManagerFactory emf;

  public AbstractDao() {
    this.clazz =
        (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Autowired
  public void setEmf(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public T findOne(final long id) {
    var result = getCurrentSession().get(clazz, id);

    getCurrentSession().close();
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<T> findAll() {
    var result = getCurrentSession().createQuery("from " + clazz.getName()).list();

    getCurrentSession().close();
    return result;
  }

  public List<T> execute(CriteriaQuery<T> query) {
    var result = getCurrentSession().createQuery(query).getResultList();
    getCurrentSession().close();
    return result;
  }

  protected CriteriaBuilder expr() {
    return getCurrentSession().getCriteriaBuilder();
  }

  @Getter
  @Setter
  public static class QueryDto<T> {
    CriteriaQuery<T> criteriaQuery;
    Class<T> clazz;
    CriteriaBuilder builder;
    Root<T> root;

    public Predicate where() {
      return builder;
    }

    public Predic
  }

  protected QueryDto<T> from(Class<T> clazz) {
    QueryDto<T> dto = new QueryDto<>();
    dto.setClazz(clazz);
    var builder = getCurrentSession().getCriteriaBuilder();
    dto.setBuilder(builder);
    dto.setCriteriaQuery(builder.createQuery(clazz));
    dto.setRoot(dto.getCriteriaQuery().from(clazz));
    return dto;
  }

  @Override
  public void create(final T entity) {
    getCurrentSession().saveOrUpdate(entity);
    getCurrentSession().close();
  }

  @Override
  @SuppressWarnings("unchecked")
  public T update(final T entity) {
    var result = (T) getCurrentSession().merge(entity);

    getCurrentSession().close();
    return result;
  }

  @Override
  public void delete(final T entity) {
    getCurrentSession().delete(entity);
    getCurrentSession().close();
  }

  @Override
  public void deleteById(final long id) {
    var result = getCurrentSession().get(clazz, id);
    getCurrentSession().delete(result);
    getCurrentSession().close();
  }

  protected Session getCurrentSession() {
    try {
      return emf.unwrap(SessionFactory.class).openSession();
    } catch (HibernateException ex) {
      return emf.unwrap(SessionFactory.class).getCurrentSession();
    }
  }
}
