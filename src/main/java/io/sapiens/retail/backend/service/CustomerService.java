package io.sapiens.retail.backend.service;

import io.sapiens.retail.backend.dao.CustomerDao;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.model.User;
import io.sapiens.retail.ui.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerService {
  private final Logger log = LoggerFactory.getLogger(CustomerService.class);
  private CustomerDao customerDao;

  @Autowired
  public void setDao(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public void save(Person person) {
    User user = new User();
    BeanUtils.copyProperties(person, user);
    user.setRole(Role.CUSTOMER);
    log.debug("Saving user: ", user);
    customerDao.save(user);
  }

  public Collection<Person> retrieveByRole(Role role) {
    Collection<User> users = customerDao.retrieveByRole(role);
    List<Person> result = new ArrayList<>();

    for (User user : users) {
      Person person = new Person();
      BeanUtils.copyProperties(user, person);
      result.add(person);
    }

    return result;
  }
}
