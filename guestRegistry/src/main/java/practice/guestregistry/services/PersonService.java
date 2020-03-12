package practice.guestregistry.services;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import practice.guestregistry.dao.PersonDao;
import practice.guestregistry.models.Person;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonDao dao;
    //    private SequenceDao sequenceDao;
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

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

    public Person addPerson (Person newPerson) {
        return dao.save(newPerson);
    }

    public void updatePerson (ObjectId id, Person newPerson) {
        newPerson.setId(id);
        dao.save(newPerson);
    }

    public void deletePersonById (ObjectId id) {
        dao.deleteById(id);
    }

    public boolean personExist(Person person) {
        if (person == null) {
            return false;
        }
//        log.trace("person Exist " + person + " " +  dao.exists(Example.of(person)));
//        return dao.exists(Example.of(person));
        return dao.exists(Example.of(person));
    }

}
