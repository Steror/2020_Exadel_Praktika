package practice.guestRegistry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestRegistry.dao.PersonDao;
import practice.guestRegistry.dao.SequenceDao;
import practice.guestRegistry.models.Person;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonDao dao;
//    private SequenceDao sequenceDao;

    @Autowired
    public PersonService (PersonDao dao) {
        this.dao = dao;
    }

    public void deleteAll () {
        dao.deleteAll();
    }

    public Optional<Person> getPersonById (long id) {
        return dao.findById(id);
    }

    public List<Person> getAllPersons () {
        return dao.findAll();
    }

    public void addPerson (Person newPerson) {
        dao.save(newPerson);
    }

    public void updatePerson (Long id, Person newPerson) {
        newPerson.setId(id);
        dao.save(newPerson);
    }

    public void deletePersonById (Long id) {
        dao.deleteById(id);
    }

}
