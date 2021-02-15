package io.sapiens.retail.backend.dao;

import io.sapiens.retail.backend.dummy.DummyData;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Repository
public class CustomerDao {
  private static final Random random = new Random(1);

  String[][] meta = {
    {"Thai", "Tran", "jason.tran@octagonsolution.com", Role.MANAGER.name()},
    {"Xuan", "Nguyen", "xavier.nguyen@octagonsolution.com", Role.MANAGER.name()},
    {"Tri", "Pham", "tristan.pham@octagonsolution.com", Role.CUSTOMER.name()},
    {"Nhan", "Ngo", "nathan.ngo.octagonsolution.com", Role.CUSTOMER.name()},
    {"Hung", "Vo", "hector.vo@octagonsolution.com", Role.CUSTOMER.name()},
    {"Khiem", "Le", "khiem.le@octagonsolution.com", Role.CUSTOMER.name()},
  };

  private void setPerson(User person, int i) {
    person.setId((long) i + 1);
    person.setFirstName(meta[i][0]);
    person.setLastName(meta[i][1]);
    person.setEmail(meta[i][2]);
    person.setRole(DummyData.getRole());
    person.setLastModified(DummyData.getDate());
    person.setRandomBoolean(random.nextBoolean());
    person.setRandomInteger(random.nextInt());
    person.setDateOfBirth(DummyData.getDate());
  }

  public Collection<User> retrieveByRole(Role role) {
    List<User> persons = new ArrayList<>();
    for (int i = 0; i < meta.length; i++) {
      User person = new User();
      if (meta[i][3].equals(role.name())) {
        setPerson(person, i);
        persons.add(person);
      }
    }
    return persons;
  }
}
