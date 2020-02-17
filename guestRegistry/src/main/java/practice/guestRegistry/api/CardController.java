package practice.guestRegistry.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.services.CardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {

    CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

//    @GetMapping("card")
    @GetMapping(path="{id}")
    public Optional<Card> getCard(@PathVariable long id) {
        return service.getCardById(id);
    }

    @GetMapping
    public List<Card> getCards() {
        List<Card> cards = service.getAllCards();
        for (Card card : cards) {
            System.out.println(card);
        }
        return cards;
    }

    @PostMapping
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        service.addCard(card);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path="{id}")
    public void updateCard(@PathVariable("id") long id, @RequestBody Card newCard) {
        System.out.println("I HAVE BEEN CALLED");
        service.updateCard(id, newCard);
    }

    @DeleteMapping(path="{id}")
    public void deleteCard(@PathVariable long id) {
        service.deleteCardById(id);
    }
}



/*
@RequestMapping("/colleagues/{name}")
    public List<Colleague> getRecognition(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping("/colleagues")
    public List<Colleague> getColleagues(){
        return repository.findAll();
    }

    @PostMapping("/colleagues")
    public ResponseEntity<String> addColleague(@RequestBody Colleague colleague){
        repository.save(colleague);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //This is of course a very naive implementation! We are assuming unique names...
    @DeleteMapping("/colleagues/{name}")
    public ResponseEntity<String> deleteColleague(@PathVariable  String name){
        List<Colleague> colleagues = repository.findByName(name);
        if(colleagues.size() == 1) {
            Colleague colleague = colleagues.get(0);
            repository.delete(colleague);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
 */
