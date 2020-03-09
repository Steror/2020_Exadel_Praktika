package practice.guestregistry.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.models.Worker;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkerDaoImpl implements WorkerDao {
    MongoTemplate mongoTemplate;
    //    SequenceDao sequenceDao;
    private static final String HOSTING_SEQ_KEY = "worker";

    @Autowired
    public WorkerDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), Worker.class);
    }

    @Override
    public Optional<Worker> findById(ObjectId id) {
        return Optional.ofNullable (mongoTemplate.findById(id, Worker.class));
    }

    @Override
    public List<Worker> findAll() {
        return mongoTemplate.findAll(Worker.class);
    }

    @Override
    public Worker save(Worker worker) {
        worker.setId(ObjectId.get());
        return mongoTemplate.save(worker);
    }

    @Override
    public Worker update(Worker worker) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").is(worker.getId())), Worker.class)) {
            worker.setId(worker.getId());
            return mongoTemplate.save(worker);
        }
        return null;
    }

    @Override
    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, Worker.class);
    }

    @Override
    public boolean existById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, Worker.class);
    }

//    @PostConstruct
//    private void after() {
//        if (!mongoTemplate.collectionExists(Worker.class)   ) {
//            sequenceDao.initCollection(HOSTING_SEQ_KEY);
//        }
//    }
}
