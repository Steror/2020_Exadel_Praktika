package practice.guestRegistry.api;

import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getAllCards();
    }

    @PostMapping
    public void addCard(@RequestBody Card card) {
        service.addCard(card);
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
