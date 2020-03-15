package practice.guestregistry.data.impl.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dto.PersonDTO;
import practice.guestregistry.data.impl.mappers.PersonDTOMapper;
import practice.guestregistry.data.impl.models.Card;
import practice.guestregistry.data.impl.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonDaoImpl implements PersonDao {
    private MongoTemplate mongoTemplate;
    private PersonDTOMapper mapper;
    //    SequenceDao sequenceDao;
    private static final String HOSTING_SEQ_KEY = "card";

    @Autowired
    public PersonDaoImpl(MongoTemplate mongoTemplate, PersonDTOMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), Card.class);
    }

    @Override
    public Optional<PersonDTO> findById(String id) {
        Person personDB = mongoTemplate.findById(new ObjectId(id), Person.class);
        return Optional.ofNullable(mapper.map(personDB));
    }

    @Override
    public List<PersonDTO> findAll() {
//        List<Person> tempList = mongoTemplate.findAll(Person.class);
//        List<PersonDTO> retList = new ArrayList<>();
//        System.out.println(mapper.map(tempList.get(0)));
//        for (Person person : tempList) {
//            retList.add(mapper.map(person));
//        }
//        return retList;
        return mongoTemplate.findAll(Person.class).
                stream().
                map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        Person mappedPerson = mapper.map(personDTO);
        mappedPerson.setId(ObjectId.get());
        return mapper.map(mongoTemplate.save(mappedPerson));
    }

    @Override
    public void deleteById(String id) {
        mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(new ObjectId(id))), Person.class);
    }

    public boolean existById(String id) {
        return  mongoTemplate.exists(Query.query(Criteria.where("id").is(new ObjectId(id))), Person.class);
    }


    @Override
    public PersonDTO update(PersonDTO personDTO) {
        Person mappedPerson = mapper.map(personDTO);
        return mapper.map(mongoTemplate.save(mappedPerson));
    }

    public boolean exist(PersonDTO personDTO) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(mapper.map(personDTO))), Card.class);
    }


//    @PostConstruct
//    private void after() {
//        if (!mongoTemplate.collectionExists(Card.class)   ) {
//            sequenceDao.initCollection(HOSTING_SEQ_KEY);
//        }
//    }
}
