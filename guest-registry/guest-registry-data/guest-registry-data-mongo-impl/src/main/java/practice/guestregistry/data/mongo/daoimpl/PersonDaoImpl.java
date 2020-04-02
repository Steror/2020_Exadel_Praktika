package practice.guestregistry.data.mongo.daoimpl;

import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonDaoImpl implements PersonDao {

    private final MongoTemplate mongoTemplate;
    private final PersonDomainEntityMapper mapper; //TODO rename Mapper to unify code
    private static final Logger log = LoggerFactory.getLogger(PersonDaoImpl.class);

    @Autowired
    public PersonDaoImpl(MongoTemplate mongoTemplate, PersonDomainEntityMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

//    @PostConstruct
//    @EventListener(ApplicationReadyEvent.class)
//    public void initIndicesAfterStartup() {
////        mongoTemplate.indexOps(PersonEntity.class).ensureIndex(
////          new Index().on("phoneNumber", Sort.Direction.ASC).unique()
////        );
////        mongoTemplate.indexOps(PersonEntity.class).getIndexInfo().stream().forEach(System.out::println);
////    }
//
//        IndexOperations indexOps = mongoTemplate.indexOps(PersonEntity.class);
//        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoTemplate.getConverter().getMappingContext());
//        resolver.resolveIndexFor(PersonEntity.class).forEach(elem -> {
//            indexOps.ensureIndex(elem);
//            log.debug("created index:" + elem.getIndexKeys().toJson() + " " + elem.getIndexOptions().toJson() + " " + elem.toString());
//        });
//    }

    @Override
    public Person findById(String id) {
        PersonEntity personEntity = mongoTemplate.findById(id, PersonEntity.class);
        if (personEntity == null) {
            throw new ResourceNotFoundException("Cannot find person by this id doesnt exist");
        } else {
            return mapper.map(personEntity);
        }
    }


    @Override
    public List<Person> findAll() {
        return mongoTemplate.findAll(PersonEntity.class)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Person person) {
        log.debug("[add] got person: " + person);
        PersonEntity mappedPersonEntity = mapper.map(person);
        if (mappedPersonEntity.getId() != null) {
            throw new EntityCreationException("id must be null");
        }

        log.debug("[add] saving mapped person entity: " + mappedPersonEntity);
        PersonEntity savedPerson;
        try {
            savedPerson = mongoTemplate.save(mappedPersonEntity);
        } catch (Exception ex) {
            throw new EntityCreationException("caused by", ex);
        }
        log.debug("[add] savedPerson: " + savedPerson);
        person.setId(savedPerson.getId().toHexString());
    }

    @Override
    public void update(Person person) {
        PersonEntity mappedPersonEntity = mapper.map(person);
        if (mappedPersonEntity.getId() == null) {
            throw new EntityUpdateException("id must be null");
        }
        try {
            System.out.println(mongoTemplate.findAll(PersonEntity.class));
            mongoTemplate.save(mappedPersonEntity);
            System.out.println(mongoTemplate.findAll(PersonEntity.class));
        } catch (Exception ex) {
            throw new EntityUpdateException("Caused by", ex);
        }
    }

    @Override
    public void deleteById(String id) {
//        PersonEntity deletedPerson = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), PersonEntity.class);
        Query query = Query.query(Criteria.where("id").is(id));
        DeleteResult deletedWorker = mongoTemplate.remove(query, PersonEntity.class);
        if (deletedWorker.getDeletedCount() == 0) {
            throw new ResourceNotFoundException("Cannot delete person by this id doesnt exist");
        }
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), PersonEntity.class);
    }

    @Override
    public boolean existById(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(id)), PersonEntity.class);
    }

    public boolean exist(Person person) {
        Query query = Query.query(Criteria.where("id").is(person.getId())
                .and("firstName").is(person.getFirstName())
                .and("middleName").is(person.getMiddleName())
                .and("lastName").is(person.getLastName())
                .and("email").is(person.getEmail())
                .and("phoneNumber").is(person.getPhoneNumber()));
        return mongoTemplate.exists(query, PersonEntity.class);
    }

    public boolean existByFullNameorEmailorAddress(Person person) {
        Query query = Query.query(new Criteria().orOperator(
                new Criteria().andOperator(
                        Criteria.where("firstName").is(person.getFirstName()),
                        Criteria.where("middleName").is(person.getMiddleName()),
                        Criteria.where("lastName").is(person.getLastName())),
                Criteria.where("email").is(person.getEmail()),
                Criteria.where("phoneNumber").is(person.getPhoneNumber())
                )
        );
//        log.trace("[existByFullNameorEmailorAddress] FOUNT THIS: " + mongoTemplate.find(query, PersonEntity.class) );
        return mongoTemplate.exists(query, PersonEntity.class);
    }

    @Override
    public void increaseReferencedCount(String personId) {
        Query query = Query.query(Criteria.where("id").is(personId));
        Update update = new Update().inc("referenced", 1);
        PersonEntity modified = mongoTemplate.findAndModify(query, update, PersonEntity.class);
        if (modified == null) {
            throw new ResourceNotFoundException("Person doenst Exist");
        }
    }

    @Override
    public void decreaseReferencedCount(String personId) {
        Query query = Query.query(Criteria.where("id").is(personId));
        Update update = new Update().inc("referenced", -1);
        PersonEntity modified = mongoTemplate.findAndModify(query, update, PersonEntity.class);
        if (modified == null) {
            throw new ResourceNotFoundException("Person doenst Exist");
        }
    }
//    @Override
//    public boolean existByFullName(String name, String middle, String last) {
//        return false;
//    }
//
//    @Override
//    public boolean existByEmail(String email) {
//        return false;
//    }
//
//    @Override
//    public long countByPhoneNumber(String number) {
//        Query query = Query.query(Criteria.where("phoneNumber").is(number));
//        return mongoTemplate.count(query, PersonEntity.class);
//    }
}
