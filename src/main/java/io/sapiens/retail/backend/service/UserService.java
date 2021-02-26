package io.sapiens.retail.backend.service;

import io.sapiens.awesome.service.AbstractService;
import io.sapiens.retail.backend.dao.UserDao;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.model.User;
import io.sapiens.retail.ui.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

  public void saveUser(io.sapiens.retail.ui.models.User user) {
    User u = new User();
    BeanUtils.copyProperties(user, u);
    u.setRole(Role.STAFF);
    saveOrUpdate(u);
  }

  public Collection<Customer> retrieveCustomer() {
    List<User> users = ((UserDao) dao).retrieveByRole(Role.CUSTOMER);
    List<Customer> result = new ArrayList<>();

    for (User user : users) {
      var person = new Customer();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }

  public Collection<io.sapiens.retail.ui.models.User> retrieveUser() {
    List<User> users = ((UserDao) dao).retrieveByRole(Role.STAFF);
    List<io.sapiens.retail.ui.models.User> result = new ArrayList<>();

    for (User user : users) {
      var person = new io.sapiens.retail.ui.models.User();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }
}
