package practice.guestRegistry;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import practice.guestRegistry.models.Card;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GuestRegistryCardAPItest {
	private static final String ROOT_URL = "http://lcoalhost:8080/card/";
	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testGetAllCards() {
		ResponseEntity<Card[]> responseEntity =
				restTemplate.getForEntity(ROOT_URL, Card[].class);
		List<Card> cards = Arrays.asList(responseEntity.getBody());
		assertNotNull(cards);
	}

	@Test
	public void testGetCardById() {
		//damm koks cia url?
		Card card = restTemplate.getForObject(ROOT_URL+"1", Card.class);
		assertNotNull(card);
	}

	@Test
	public void testCreateCard() {
		Card card = new Card();
		card.setSerialNumber("XXXX-xxxx-XXXX");
		ResponseEntity<Card> postResponse =
				restTemplate.postForEntity(ROOT_URL, card, Card.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

//	@Test
//	public void testUpdateCard()
//	{
//		int id = 1;
//		Card post = restTemplate.getForObject(ROOT_URL+id, Card.class);
//		post.setContent("This my updated post1 content");
//		post.setUpdatedOn(new Date());
//		restTemplate.put(ROOT_URL+"/posts/"+id, post);
//		Card updatedCard = restTemplate.getForObject(ROOT_URL+id, Card.class);
//		assertNotNull(updatedCard);
//	}
//
//	@Test
//	public void testDeleteCard() {
//		int id = 2;
//		Card post = restTemplate.getForObject(ROOT_URL + id, Card.class);
//		assertNotNull(post);
//		restTemplate.delete(ROOT_URL + "/posts/" + id);
//		try {
//			post = restTemplate.getForObject(ROOT_URL + id, Card.class);
//		} catch (final HttpClientErrorException e) {
//			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
//		}
//	}
}
