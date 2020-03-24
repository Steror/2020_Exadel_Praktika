package practice.guestregistry.data.api.dao;


import practice.guestregistry.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    Optional<Person> findById (String id);
    List<Person> findAll ();
    Person add (Person person);
    Person update (Person person);
    void deleteById (String id);
    void deleteAll ();
    boolean existById(String id);
    boolean exist(Person person);

}
//// No need implementation, just one interface, and you have CRUD, thanks Spring Data
//public interface PersonDao extends MongoRepository<Person, ObjectId> {
////    Card findFirstByDomain(String domain);
////    Card findByDomainAndDisplayAds(String domain, boolean displayAds);
//
////
////    su situo likaits sugeneruoja koda pagal mongo uzklausa, bet dar neisbandziau
////    Supports native JSON query string
////    @Query("{person:'?0'}")
////    Person findCustomByDomain(String serialNumber);
////    @Query("{person: { $regex: ?0 } })")
////    List<Person> findCustomByRegExDomain(String serialNumber);
//
////
////    kiek suprantu apatinius atkomentavus, ju metodas yra sugeneruojamas automatiskai
////    cia kitu automatiskai generuojamu metodu sarasas
////    https://docs.spring.io/spring-data/data-document/docs/current/reference/html/#mongodb.repositories.queries
//
////    public Person findByFirstName(String firstName);
////    public List<Person> findByLastName(String lastName);
//}
