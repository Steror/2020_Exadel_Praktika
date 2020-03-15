package practice.guestregistry.service;

import practice.guestregistry.data.api.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    public void deleteAll ();
    public Optional<PersonDTO> getPersonById (String id);
    public List<PersonDTO> getAllPersons ();
    public PersonDTO savePerson (PersonDTO newPersonDTO);
    public void updatePerson (PersonDTO newPersonDTO);
    public void deletePersonById (String id);
    public boolean personExist(PersonDTO personDTO);
}
