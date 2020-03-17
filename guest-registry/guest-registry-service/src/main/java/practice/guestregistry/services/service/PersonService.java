package practice.guestregistry.services.service;

import practice.guestregistry.data.api.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    public void deleteAll ();
    public Optional<Person> getPersonById (String id);
    public List<Person> getAllPersons ();
    public Person savePerson (Person newPerson);
    public void updatePerson (Person newPerson);
    public void deletePersonById (String id);
    public boolean personExist(Person personDomain);
}
