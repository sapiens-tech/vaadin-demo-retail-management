package io.sapiens.app.backend.dao;

import io.sapiens.awesome.dao.AbstractDao;
import io.sapiens.app.backend.enums.Role;
import io.sapiens.app.backend.model.User;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@ToString
public class UserDao extends AbstractDao<User> {
  public List<User> retrieveByRole(Role... roles) {
    QueryStatement<User> statement = from(User.class);
    Root<User> user = statement.getRoot();

    statement.select(user).where(user.get("role").in(roles));

    return execute(statement.getQuery());
  }
}
