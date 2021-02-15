package io.sapiens.retail.backend.services;

import io.sapiens.retail.backend.dummy.DummyData;
import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.ui.models.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class CustomerService {
  private static final Random random = new Random(1);
  String[][] meta = {
    {"Thai", "Tran", "jason.tran@octagonsolution.com", Role.MANAGER.name()},
    {"Xuan", "Nguyen", "xavier.nguyen@octagonsolution.com", Role.MANAGER.name()},
    {"Tri", "Pham", "tristan.pham@octagonsolution.com", Role.CUSTOMER.name()},
    {"Nhan", "Ngo", "nathan.ngo.octagonsolution.com", Role.CUSTOMER.name()},
    {"Hung", "Vo", "hector.vo@octagonsolution.com", Role.CUSTOMER.name()},
    {"Khiem", "Le", "khiem.le@octagonsolution.com", Role.CUSTOMER.name()},
  };

  public void save(Person person) {
    System.out.println(person);
  }

  private void setPerson(Person person, int i) {
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

  public Collection<Person> retrieveByRole(Role role) {
    List<Person> persons = new ArrayList<>();
    for (int i = 0; i < meta.length; i++) {
      Person person = new Person();
      if (meta[i][3].equals(role.name())) {
        setPerson(person, i);
        persons.add(person);
      }
    }
    return persons;
  }

  public Collection<Person> retrieve() {
    List<Person> persons = new ArrayList<>();

    for (int i = 0; i < meta.length; i++) {
      Person person = new Person();
      setPerson(person, i);
      persons.add(person);
    }

    return persons;
  }
}
