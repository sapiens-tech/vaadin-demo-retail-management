package io.sapiens.app.backend.service;

import io.sapiens.awesome.service.AbstractService;
import io.sapiens.app.backend.dao.UserDao;
import io.sapiens.app.backend.enums.Role;
import io.sapiens.app.backend.model.User;
import io.sapiens.app.ui.models.Customer;
import io.sapiens.app.ui.models.Mechanic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService extends AbstractService<User> {
  private final Logger log = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public void setDao(UserDao userDao) {
    super.setDao(userDao);
  }

  public void saveCustomer(Customer person) {
    log.info("Saving customer ...");
    User user = new User();
    BeanUtils.copyProperties(person, user);
    user.setRole(Role.CUSTOMER);
    saveOrUpdate(user);
  }

  public void saveUser(io.sapiens.app.ui.models.User.Edit user) {
    User u = new User();
    if (user.getRoles().getSelected() != null && !user.getRoles().getSelected().isEmpty()) {
      String role = String.valueOf(user.getRoles().getSelected().get(0).getValue());
      u.setRole(Role.valueOf(role));
    }

    BeanUtils.copyProperties(user, u);
    u.setRole(Role.STAFF);
    saveOrUpdate(u);
  }

  public Collection<Mechanic.List> retrieveMechanic() {
    java.util.List<User> users = ((UserDao) dao).retrieveByRole(Role.CUSTOMER);
    java.util.List<Mechanic.List> result = new ArrayList<>();

    for (User user : users) {
      var person = new Mechanic.List();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }

  public Collection<Customer.List> retrieveCustomer() {
    java.util.List<User> users = ((UserDao) dao).retrieveByRole(Role.CUSTOMER);
    java.util.List<Customer.List> result = new ArrayList<>();

    for (User user : users) {
      var person = new Customer.List();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }

  public Collection<io.sapiens.app.ui.models.User.List> retrieveUser() {
    java.util.List<User> users = ((UserDao) dao).retrieveByRole(Role.STAFF);
    java.util.List<io.sapiens.app.ui.models.User.List> result = new ArrayList<>();

    for (User user : users) {
      var person = new io.sapiens.app.ui.models.User.List();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }
}
