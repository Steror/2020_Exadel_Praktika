package practice.guestregistry.services.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import practice.guestregistry.data.api.dao.EventDao;
import practice.guestregistry.data.api.domain.Event;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {
    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventDao eventDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getEventById() {
    }

    @Test
    void getAllEvents() {
    }

    @Test
    void addEvent() {
    }

    @Test
    void updateEvent() {
    }

    @Test
    void deleteEventById() {
    }

    @Test
    void deleteAllEvents() {
    }
}
