package practice.guestRegistry.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestRegistry.models.Person;
import practice.guestRegistry.services.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

//    @GetMapping("person")
    @GetMapping(path="{id}")
    public Optional<Person> getPerson(@PathVariable long id) {
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

    @PutMapping(path="{id}")
    public void updatePerson(@PathVariable("id") long id, @RequestBody Person newPerson) {
        service.updatePerson(id, newPerson);
    }

    @DeleteMapping(path="{id}")
    public void deletePerson(@PathVariable long id) {
        service.deletePersonById(id);
    }
}
