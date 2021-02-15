package io.sapiens.retail.backend.services;

import io.sapiens.retail.backend.dummy.DummyData;
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
    {"Thai", "Tran", "jason.tran@octagonsolution.com"},
    {"Xuan", "Nguyen", "xavier.nguyen@octagonsolution.com"},
    {"Tri", "Pham", "tristan.pham@octagonsolution.com"},
    {"Nhan", "Ngo", "nathan.ngo.octagonsolution.com"},
    {"Hung", "Vo", "hector.vo@octagonsolution.com"},
    {"Khiem", "Le", "khiem.le@octagonsolution.com"},
  };

  public void save(Person person) {
    System.out.println(person);
  }

  public Collection<Person> retrieve() {
    List<Person> persons = new ArrayList<>();

    for (int i = 0; i < meta.length; i++) {
      Person person = new Person();

      person.setId((long) i + 1);
      person.setFirstName(meta[i][0]);
      person.setLastName(meta[i][1]);
      person.setEmail(meta[i][2]);
      person.setRole(DummyData.getRole());
      person.setLastModified(DummyData.getDate());
      person.setRandomBoolean(random.nextBoolean());
      person.setRandomInteger(random.nextInt());
      person.setDateOfBirth(DummyData.getDate());
      persons.add(person);
    }

    return persons;
  }
}
