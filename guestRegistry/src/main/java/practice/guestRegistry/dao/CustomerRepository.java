package practice.guestRegistry.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import practice.guestRegistry.models.Customer;

import java.util.List;

public interface CustomerRepository extends MongoRepository <Customer, String> {
    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
}
