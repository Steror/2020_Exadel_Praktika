package practice.guestregistry.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.domain.Person;
import practice.guestregistry.services.service.PersonService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/person")
@CrossOrigin(origins = "http://localhost:4200")
//@ComponentScan({"practice.guestregistry.service"})
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//    @GetMapping("person")
    @GetMapping(path="{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.getPersonById(id);
    }

    @GetMapping
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    @PutMapping(path="{id}")
    public void updatePerson(@RequestBody Person newPerson) {
        personService.updatePerson(newPerson);
    }

    @DeleteMapping(path="{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePersonById(id);
    }
}
