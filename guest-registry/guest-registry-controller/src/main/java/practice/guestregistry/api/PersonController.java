package practice.guestregistry.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.data.api.dto.PersonDTO;
import practice.guestregistry.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
@CrossOrigin(origins = "http://localhost:4200")
@ComponentScan({"practice.guestregistry.service"})
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//    @GetMapping("person")
    @GetMapping(path="{id}")
    public Optional<PersonDTO> getPerson(@PathVariable String id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<PersonDTO> getPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public void addPerson(@RequestBody PersonDTO person) {
        personService.savePerson(person);
    }

    @PutMapping(path="{id}")
    public void updatePerson(@RequestBody PersonDTO newPerson) {
        personService.updatePerson(newPerson);
    }

    @DeleteMapping(path="{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePersonById(id);
    }
}
