package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.PersonDao;
import practice.guestregistry.models.Person;

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

    public Optional<Person> getPersonById (ObjectId id) {
        return dao.findById(id);
    }

    public List<Person> getAllPersons () {
        return dao.findAll();
    }

    public void addPerson (Person newPerson) {
        dao.save(newPerson);
    }

    public void updatePerson (ObjectId id, Person newPerson) {
        newPerson.setId(id);
        dao.save(newPerson);
    }

    public void deletePersonById (ObjectId id) {
        dao.deleteById(id);
    }

}
