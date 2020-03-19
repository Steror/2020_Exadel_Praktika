package practice.guestregistry.services.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.api.domain.Person;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.service.PersonService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;
    //    private SequenceDao sequenceDao;
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);


    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public void deleteAll () {
        personDao.deleteAll();
    }

    @Override
    public Optional<Person> getPersonById (String id) {
        Person person = personDao.findById(id);
        if (person != null) {
            return Optional.of(person);
        } else {
            throw new ResourceNotFoundException("Person with this id doesn't exist");
        }
    }

    @Override
    public List<Person> getAllPersons () {
        return personDao.findAll();
    }

    @Override
    public Person savePerson (Person newPerson) {
        return personDao.save(newPerson);
    }

    @Override
    public void updatePerson (Person newPerson) {
        if (personDao.existById(newPerson.getId())) {
            personDao.update(newPerson);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }

    @Override
    public void deletePersonById (String id) {
        if (personDao.existById(id)) {
            personDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }


    @Override
    public boolean personExist(Person person) {
        if (person == null) {
            return false;
        }
//        log.trace("person Exist " + person + " " +  practice.guestregistry.data.impl.personDao.exists(Example.of(person)));
//        return practice.guestregistry.data.impl.personDao.exists(Example.of(person));
        return personDao.exist(person);
    }

}
