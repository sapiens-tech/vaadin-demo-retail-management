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
  public static class QueryStatement<T> {
    CriteriaQuery<T> criteriaQuery;
    Class<T> clazz;
    CriteriaBuilder builder;
    Root<T> root;
    Session session;

    public QueryStatement(Session session) {
      this.session = session;
    }

    public QueryStatement<T> where() {
      return this;
    }

    public QueryStatement<T> eq(String path, Object value) {
      builder.equal(root.get(path), value);
      return this;
    }

    public List<T> execute() {
      return session.createQuery(this.criteriaQuery).getResultList();
    }
  }

  protected QueryStatement<T> from(Class<T> clazz) {
    var session = getCurrentSession();
    var builder = session.getCriteriaBuilder();
    QueryStatement<T> statement = new QueryStatement<>(session);
    statement.setClazz(clazz);
    statement.setBuilder(builder);
    statement.setCriteriaQuery(builder.createQuery(clazz));
    statement.setRoot(statement.getCriteriaQuery().from(clazz));
    return statement;
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
