package io.sapiens.retail.backend.service;

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
public class UserService {
  private final Logger log = LoggerFactory.getLogger(UserService.class);
  private UserDao userDao;

  @Autowired
  public void setDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void saveCustomer(Customer person) {
    User user = new User();
    BeanUtils.copyProperties(person, user);
    user.setRole(Role.CUSTOMER);
    userDao.save(user);
  }

  public void saveUser(io.sapiens.retail.ui.models.User user) {
    User u = new User();
    BeanUtils.copyProperties(user, u);
    u.setRole(Role.MANAGER);
    userDao.save(u);
  }

  public Collection<Customer> retrieveCustomer() {
    List<User> users = userDao.retrieveByRole(Role.CUSTOMER);
    List<Customer> result = new ArrayList<>();

    for (User user : users) {
      var person = new Customer();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }

  public Collection<io.sapiens.retail.ui.models.User> retrieveUser() {
    List<User> users = userDao.retrieveByRole(Role.MANAGER);
    List<io.sapiens.retail.ui.models.User> result = new ArrayList<>();

    for (User user : users) {
      var person = new io.sapiens.retail.ui.models.User();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }
}
