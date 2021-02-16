package io.sapiens.retail.backend.dao;

import io.sapiens.awesome.dao.AbstractDao;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.model.User;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@ToString
public class CustomerDao extends AbstractDao<User> {
  public Collection<User> retrieveByRole(Role role) {
    return from(User.class).where().eq("role", role).execute();
  }
}
