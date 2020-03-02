package practice.guestregistry.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.models.Card;

import java.util.List;
import java.util.Optional;


@Repository
public class CardDaoImpl implements CardDao {
    MongoTemplate mongoTemplate;
    SequenceDao sequenceDao;
    private static final String HOSTING_SEQ_KEY = "card";

    @Autowired
    public CardDaoImpl(
            MongoTemplate mongoTemplate,
            SequenceDao sequenceDao
    ) {
        this.mongoTemplate = mongoTemplate;
        this.sequenceDao = sequenceDao;
    }

    @Override
    public void deleteAll() {
//        mongoTemplate.remove(Card.class);   <<--- this one doesn't remove Document data
        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").exists(true)), Card.class);
    }

    @Override
    public Optional<Card> findById(ObjectId id) {
        return Optional.ofNullable (mongoTemplate.findById(id, Card.class));
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(Card.class);
    }

    @Override
    public void save(Card card) {
//        long temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        card.setId(ObjectId.get());
        System.out.println("Saving card:" + card);
        mongoTemplate.save(card);
    }

    @Override
    public void update(ObjectId id, Card card) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Card.class)) {
            card.setId(id);
            mongoTemplate.save(card);
        }
    }

    @Override
    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
//        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").is(id)),Card.class);
        mongoTemplate.findAllAndRemove(query, Card.class);
    }

//    @PostConstruct
//    private void after() {
//
//        if (!mongoTemplate.collectionExists(Card.class)   ) {
//            sequenceDao.initCollection(HOSTING_SEQ_KEY);
//        }
//    }
}
