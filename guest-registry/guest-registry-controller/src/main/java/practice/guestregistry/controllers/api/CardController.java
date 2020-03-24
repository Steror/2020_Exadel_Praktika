package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.domain.Card;
import practice.guestregistry.services.service.CardService;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/card")
@CrossOrigin(origins = "http://localhost:4200")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping(path="{id}")
    public Card getCard(@PathVariable String id) {
        return service.getCardById(id);
    }

    @GetMapping
    public List<Card> getCards() {
        return service.getAllCards();
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = service.addCard(card);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedCard.getId().toString())
                .toUri());
        return new ResponseEntity<Card>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<Card> updateCard(@RequestBody Card newCard) {
        service.updateCard(newCard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="{id}")
    public void deleteCard(@PathVariable String id) {
        service.deleteCardById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @ExceptionHandler(PostDeletionException.class)
//    public ResponseEntity<?> servletRequestBindingException(PostDeletionException e)
//    {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setErrorMessage(e.getMessage());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        errorDetails.setDevErrorMessage(sw.toString());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
