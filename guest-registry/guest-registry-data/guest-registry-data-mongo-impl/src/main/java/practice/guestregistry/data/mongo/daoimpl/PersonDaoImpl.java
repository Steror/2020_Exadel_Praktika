package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.mappers.PersonDomainEntityMapper;
import practice.guestregistry.domain.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDaoImpl implements PersonDao {

    private final MongoTemplate mongoTemplate;
    private final PersonDomainEntityMapper mapper; //TODO rename Mapper to unify code

    @Autowired
    public PersonDaoImpl(MongoTemplate mongoTemplate, PersonDomainEntityMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public Person findById(String id) {
        return mapper.map(mongoTemplate.findById(new ObjectId(id), PersonEntity.class));
//        PersonEntity personEntity = mongoTemplate.findById(new ObjectId(id), PersonEntity.class);
//        if (personEntity == null) {
//            throw new ResourceNotFoundException();
//        }
//        return Optional.ofNullable(mapper.map(personDB));
    }

    @Override
    public List<Person> findAll() {
        return mongoTemplate.findAll(PersonEntity.class).
                stream().
                map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Person add(Person person) {
        PersonEntity mappedPersonEntity = mapper.map(person);
        mappedPersonEntity.setId(ObjectId.get());
        return mapper.map(mongoTemplate.save(mappedPersonEntity));
    }

    @Override
    public Person update(Person personDomain) {
        PersonEntity mappedPersonEntity = mapper.map(personDomain);
        return mapper.map(mongoTemplate.save(mappedPersonEntity));
    }

    @Override
    public void deleteById(String id) {
        mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(new ObjectId(id))), PersonEntity.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), PersonEntity.class);
    }

    @Override
    public boolean existById(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(new ObjectId(id))), PersonEntity.class);
    }

    @Override
    public boolean exist(Person personDomain) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(mapper.map(personDomain))), PersonEntity.class);
    }
}
