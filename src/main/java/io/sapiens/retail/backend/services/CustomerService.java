package io.sapiens.retail.backend.services;

import io.sapiens.retail.backend.DummyData;
import io.sapiens.retail.ui.models.Person;

import java.util.Collection;

public class CustomerService {
    public void save(Person person) {
        System.out.println(person);
    }

    public Collection<Person> retrieve() {
       return DummyData.getPersons();
    }
}
