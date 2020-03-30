package practice.guestregistry.services.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.InvalidDocumentStateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.services.exceptions.DomainCreationException;
import practice.guestregistry.services.service.PersonService;

import javax.validation.Valid;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person getPersonById (String id) {
        if (id == null) {
            throw new ResourceNotFoundException("id string null");
        }
        return personDao.findById(id);
    }

    @Override
    public List<Person> getAllPersons () {
        return personDao.findAll();
    }

    @Override
    public void addPerson(Person person) {
        if (personFieldsValid(person)) {
            personDao.add(person);
        } else {
            throw new InvalidDocumentStateException("person information is incorrect");
        }
    }

    @Override
    public void updatePerson (Person newPerson) {
        if (personFieldsValid(newPerson)) {
            if (personDao.existById(newPerson.getId())) {
                personDao.update(newPerson);
            } else {
                throw new ResourceNotFoundException("Can't update invalid person information");
            }
        } else {
            throw new InvalidDocumentStateException("Person contains incorrect information");
        }
    }

    @Override
    public void deletePersonById (String id) {
        if (personDao.existById(id)) {
            personDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Can't delete person by this id doesnt exist");
        }
    }

    @Override
    public void deleteAllPersons () {
        personDao.deleteAll();
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

    @Override
    public boolean existById(String id) {
        return personDao.existById(id);
    }


    private boolean personFieldsValid(Person person) {
        if (person.getPhoneNumber().matches("[0-9]*")
                && person.getEmail().contains("@")) {
            return true;
        }
        return false;
    }

}
