package practice.guestregistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import practice.guestregistry.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestRegistryTestTest {
    @MockBean
    PersonService personService;

    @Test
    public void should_load_all_users()
    {
//        List<Person> users = personService.getAllUsers();
//        assertNotNull(users);
//        assertEquals(10, users.size());
    }

}
