package io.sapiens.awesome.dao;

import io.sapiens.awesome.model.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
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

  public QueryStatement<T> from(Class<T> clazz) {
    var builder = expr();
    var query = builder.createQuery(clazz);
    var root= query.from(clazz);

    return new QueryStatement<>(query, root, getCurrentSession());
  }

  @Setter @Getter
  @AllArgsConstructor
  public static class QueryStatement<T> {
    CriteriaQuery<T> query;
    Root<T> root;
    Session session;

    public CriteriaQuery<T> select(Selection<? extends T> selection) {
      return query.select(selection) ;
    }
  }

  protected CriteriaBuilder expr() {
    return getCurrentSession().getCriteriaBuilder();
  }

  @Override
  public void save(final T entity) {
    var session = getCurrentSession();
    session.getTransaction().begin();
    session.saveOrUpdate(entity);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  @SuppressWarnings("unchecked")
  public T update(final T entity) {
    var session = getCurrentSession();
    var result = (T) getCurrentSession().merge(entity);
    session.getTransaction().commit();

    session.close();
    return result;
  }

  @Override
  public void delete(final T entity) {
    Session session = getCurrentSession();
    session.delete(entity);
    session.getTransaction().commit();
    session.close();
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
