package practice.guestregistry.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.guestregistry.data.api.dto.PersonDTO;
import practice.guestregistry.data.impl.dao.PersonDao;
import practice.guestregistry.exceptions.ResourceNotFoundException;

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

    public void deleteAll () {
        personDao.deleteAll();
    }

    public Optional<PersonDTO> getPersonById (String id) {
        return personDao.findById(id);
    }

    public List<PersonDTO> getAllPersons () {
        return personDao.findAll();
    }

    public PersonDTO savePerson (PersonDTO newPersonDTO) {
        return personDao.save(newPersonDTO);
    }

    public void updatePerson (PersonDTO newPersonDTO) {
        if (personDao.existById(newPersonDTO.getId())) {
            personDao.update(newPersonDTO);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }

    public void deletePersonById (String id) {
        if (personDao.existById(id)) {
            personDao.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Can't update by this update");
        }
    }


    public boolean personExist(PersonDTO person) {
        if (person == null) {
            return false;
        }
//        log.trace("person Exist " + person + " " +  practice.guestregistry.data.impl.personDao.exists(Example.of(person)));
//        return practice.guestregistry.data.impl.personDao.exists(Example.of(person));
        return personDao.exist(person);
    }

}
