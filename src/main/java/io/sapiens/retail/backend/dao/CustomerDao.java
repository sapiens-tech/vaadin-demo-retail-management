package io.sapiens.retail.backend.dao;

import io.sapiens.awesome.dao.AbstractDao;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.models.User;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@ToString
public class CustomerDao extends AbstractDao<User> {
  public Collection<User> retrieveByRole(Role role) {
    var query = from(User.class, "user");
    query.where().equal("role", role);

//    var entity= query.from(User.class);
//    query.where(expr().equal(entity.get("role"), role));

//    return execute(query);

    return null;
  }
}
