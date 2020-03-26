package practice.guestregistry.services.service;


import practice.guestregistry.domain.Person;

import java.util.List;

public interface PersonService {
    Person getPersonById (String id);
    List<Person> getAllPersons ();
    void addPerson (Person person);
    void updatePerson (Person person);
    void deletePersonById (String id);
    void deleteAllPersons ();
    boolean personExist(Person person);
    boolean existById(String id);
//    Optional<Person> findByFirstName();
//    Optional<Person> findByMiddleName();
//    Optional<Person> findByLastName();
//    Optional<Person> findByPhoneNumber();
//    Optional<Person> findByEmailPhoneNumber();
//    boolean existByFirstName();
//    boolean existByMiddleName();
//    boolean existByLastName();
//    boolean existByPhoneNumber();
//    boolean existByEmailPhoneNumber();
}
