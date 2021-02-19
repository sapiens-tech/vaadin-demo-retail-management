package io.sapiens.retail.backend.dao;

import io.sapiens.awesome.dao.AbstractDao;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.model.User;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ToString
public class UserDao extends AbstractDao<User> {
  public List<User> retrieveByRole(Role... roles) {
    var statement = from(User.class);
    statement.select(statement.getRoot()).where(statement.getRoot().get("role").in((Object) roles));
    return execute(statement.getQuery());
  }
}
