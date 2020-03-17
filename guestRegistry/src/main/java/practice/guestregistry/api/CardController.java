package practice.guestregistry.api;

import eu.exadel.practice.guestregistration.data.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice.guestregistry.services.CardService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
@CrossOrigin(origins = "http://localhost:4200")
public class CardController {

    CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping(path="{id}")
    public Optional<Card> getCard(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @GetMapping
    public List<Card> getCards() {
        return cardService.getAllCards();
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody Card card) {
        Card savedCard = cardService.addCard(card);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedCard.getId().toString())
                .toUri());
        return new ResponseEntity<Card>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(path="{id}")
//    public ResponseEntity<Card> updateCard(@PathVariable("id") ObjectId id, @Valid @RequestBody Card newCard) {
    public ResponseEntity<Card> updateCard(@Valid @RequestBody Card newCard) {
        cardService.updateCard(newCard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="{id}")
    public void deleteCard(@PathVariable String id) {
        cardService.deleteCardById(id);
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

/*
@CrossOrigin(origins={"http://domain1.com", "http://domain2.com"},
                 allowedHeaders="X-AUTH-TOKEN",
                 allowCredentials="false",
                 maxAge=15*60,
                 methods={RequestMethod.GET, RequestMethod.POST }
                )
 */
