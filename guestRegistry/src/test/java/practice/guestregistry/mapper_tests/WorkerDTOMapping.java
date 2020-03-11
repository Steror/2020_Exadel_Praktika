package practice.guestregistry.mapper_tests;

import org.bson.types.ObjectId;
import org.junit.Test;
import practice.guestregistry.models.Card;
import practice.guestregistry.models.Person;
import practice.guestregistry.models.Worker;
import practice.guestregistry.dto.WorkerDTO;
import practice.guestregistry.dto.WorkerDTOMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerDTOMapping {

    public WorkerDTOMapper workerMapper = new WorkerDTOMapper();

    @Test
    public void mappingForward() {

        Card card = new Card();
        card.setId(ObjectId.get());
        card.setSerialNumber("numver");

        Person person = new Person();
        person.setId(ObjectId.get());
        person.setFirstName("first name");
        person.setLastName("last name");

        Worker worker = new Worker();
        worker.setId(ObjectId.get());
        worker.setCard(card);
        worker.setPerson(person);

        WorkerDTO workerDTO = workerMapper.map(worker);
//        System.out.println(card);
//        System.out.println(person);
//        System.out.println(worker);
//        System.out.println(workerDTO);
        assertThat(workerDTO.workerId).isEqualTo(worker.getId().toString());
//        assertThat(workerDTO.id).isEqualTo(worker.getId().toString());
        assertThat(workerDTO.personId).isEqualTo(person.getId().toString());
        assertThat(workerDTO.firstName).isEqualTo(person.getFirstName());
        assertThat(workerDTO.middleName).isEqualTo(person.getMiddleName());
        assertThat(workerDTO.lastName).isEqualTo(person.getLastName());
        assertThat(workerDTO.email).isEqualTo(person.getEmail());
        assertThat(workerDTO.phoneNumber).isEqualTo(person.getPhoneNumber());
        assertThat(workerDTO.cardId).isEqualTo(worker.getCard().getId().toString());
        assertThat(workerDTO.cardSerialNumber).isEqualTo(worker.getCard().getSerialNumber());
    }

    @Test
    public void mappedForwardAndBackwards() {

        Card card = new Card();
        card.setId(ObjectId.get());
        card.setSerialNumber("numver");

        Person person = new Person();
        person.setId(ObjectId.get());
        person.setFirstName("first name");
        person.setLastName("last name");

        Worker worker = new Worker();
        worker.setId(ObjectId.get());
        worker.setCard(card);
        worker.setPerson(person);

        WorkerDTO workerDTO = workerMapper.map(worker);

        Worker mappedWorker = new Worker();
        workerMapper.map(workerDTO, mappedWorker);
        System.out.println("ANOTHER VARIANT\n\n\n\n");
        System.out.println(card);
        System.out.println(person);
        System.out.println(worker);
        System.out.println(workerDTO);
        System.out.println(mappedWorker);
        assertThat(mappedWorker.getId()).isEqualTo(worker.getId());
        assertThat(mappedWorker.getPerson().getId()).isEqualTo(person.getId());
        assertThat(mappedWorker.getPerson().getFirstName()).isEqualTo(person.getFirstName());
        assertThat(mappedWorker.getPerson().getMiddleName()).isEqualTo(person.getMiddleName());
        assertThat(mappedWorker.getPerson().getLastName()).isEqualTo(person.getLastName());
        assertThat(mappedWorker.getPerson().getEmail()).isEqualTo(person.getEmail());
        assertThat(mappedWorker.getPerson().getPhoneNumber()).isEqualTo(person.getPhoneNumber());
        assertThat(mappedWorker.getCard().getId()).isEqualTo(worker.getCard().getId());
        assertThat(mappedWorker.getCard().getSerialNumber()).isEqualTo(worker.getCard().getSerialNumber());
    }
}
