package practice.guestregistry.api;

import eu.exadel.practice.guestregistration.data.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.services.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

//    @GetMapping("person")
    @GetMapping(path="{id}")
    public Optional<Person> getPerson(@PathVariable String id) {
        return service.getPersonById(id);
    }

    @GetMapping
    public List<Person> getPersons() {
        return service.getAllPersons();
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        service.addPerson(person);
    }

    @PutMapping
    public void updatePerson(@RequestBody Person newPerson) {
        service.updatePerson(newPerson);
    }

    @DeleteMapping(path="{id}")
    public void deletePerson(@PathVariable String id) {
        service.deletePersonById(id);
    }
}
