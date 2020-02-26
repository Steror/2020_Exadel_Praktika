package practice.guestregistry.api;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.dao.CardDao;
import practice.guestregistry.models.Card;
import practice.guestregistry.services.CardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
@CrossOrigin("*")
/*
@CrossOrigin(origins={"http://domain1.com", "http://domain2.com"},
                 allowedHeaders="X-AUTH-TOKEN",
                 allowCredentials="false",
                 maxAge=15*60,
                 methods={RequestMethod.GET, RequestMethod.POST }
                )
 */
public class CardController {

    CardService service;
    CardDao cardDao;

    @Autowired
    public CardController(CardService service, CardDao cardDao) {
        this.service = service;
        this.cardDao = cardDao;
    }

//    @GetMapping("card")
    @GetMapping(path="{id}")
    public Optional<Card> getCard(@PathVariable ObjectId id) {
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Card> addCard(@RequestBody Card card) {
        Card savedCard = service.addCard(card);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    //update response?
    @PutMapping(path="{id}")
    public ResponseEntity<Card> updateCard(@PathVariable("id") ObjectId id, @RequestBody Card newCard) {
        service.updateCard(id, newCard);
        //grazina 204 jei nera kuno
        //201 jei pridejo
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    //delete case returns 200 all the time? every time
    @DeleteMapping(path="{id}")
    public void deleteCard(@PathVariable ObjectId id) {
        Card post = cardDao.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("No post found with id=" + id);
        });
        try {
//            postRepository.deleteById(post.getId());
           service.deleteCardById(id);
        } catch (Exception e) {
//            throw new PostDeletionException("Post with id="+id+" can't be deleted");
        }
        //404
        //200
//        service.deleteCardById(id);
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
