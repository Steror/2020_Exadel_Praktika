package practice.guestregistry.services.service;


import practice.guestregistry.domain.Person;

import java.util.List;

public interface PersonService {
    Person getPersonById (String id);
    List<Person> getAllPersons ();
    Person addPerson (Person person);
    Person updatePerson (Person person);
    void deletePersonById (String id);
    void deleteAllPersons ();
    boolean personExist(Person person);
    boolean existById(String id);
}
