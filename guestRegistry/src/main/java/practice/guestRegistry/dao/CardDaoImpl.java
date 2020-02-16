package practice.guestRegistry.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.Card;
import practice.guestRegistry.models.SequenceId;

import javax.annotation.PostConstruct;
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
    public Optional<Card> findById(long id) {
        return Optional.ofNullable (mongoTemplate.findById(id, Card.class));
    }

    @Override
    public List<Card> findAll() {
        return mongoTemplate.findAll(Card.class);
    }

    @Override
    public void save(Card card) {
        long temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        card.setId(temp);
        mongoTemplate.save(card);
    }

    @Override
    public void update(long id, Card card) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").exists(true)), Card.class)) {
            card.setId(id);
            mongoTemplate.save(card);
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
//        mongoTemplate.findAllAndRemove(Query.query(new Criteria().where("id").is(id)),Card.class);
        mongoTemplate.findAllAndRemove(query, Card.class);
    }

    @PostConstruct
    private void after() {

        if (!mongoTemplate.collectionExists(Card.class)   ) {
            sequenceDao.initCollection(HOSTING_SEQ_KEY);
        }
        System.out.println("--------------------------------");
        for (SequenceId name : mongoTemplate.findAll(SequenceId.class)) {
            System.out.println(name);
        }
        System.out.println("--------------------------------");
    }
}
