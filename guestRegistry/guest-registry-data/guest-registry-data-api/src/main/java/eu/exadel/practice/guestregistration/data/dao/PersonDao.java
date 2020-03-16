package eu.exadel.practice.guestregistration.data.dao;

import eu.exadel.practice.guestregistration.data.domain.Person;

import org.springframework.data.mongodb.repository.MongoRepository;

// No need implementation, just one interface, and you have CRUD, thanks Spring Data
public interface PersonDao extends MongoRepository<Person, String> {
//    Card findFirstByDomain(String domain);
//    Card findByDomainAndDisplayAds(String domain, boolean displayAds);

//
//    su situo likaits sugeneruoja koda pagal mongo uzklausa, bet dar neisbandziau
//    Supports native JSON query string
//    @Query("{person:'?0'}")
//    Person findCustomByDomain(String serialNumber);
//    @Query("{person: { $regex: ?0 } })")
//    List<Person> findCustomByRegExDomain(String serialNumber);

//
//    kiek suprantu apatinius atkomentavus, ju metodas yra sugeneruojamas automatiskai
//    cia kitu automatiskai generuojamu metodu sarasas
//    https://docs.spring.io/spring-data/data-document/docs/current/reference/html/#mongodb.repositories.queries

//    public Person findByFirstName(String firstName);
//    public List<Person> findByLastName(String lastName);
}
