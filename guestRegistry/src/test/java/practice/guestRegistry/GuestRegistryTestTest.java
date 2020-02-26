package practice.guestRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import practice.guestRegistry.services.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestRegistryTestTest {
    @Autowired
    PersonService personService;

    @Test
    public void should_load_all_users()
    {
//        List<Person> users = personService.getAllUsers();
//        assertNotNull(users);
//        assertEquals(10, users.size());
    }

}
