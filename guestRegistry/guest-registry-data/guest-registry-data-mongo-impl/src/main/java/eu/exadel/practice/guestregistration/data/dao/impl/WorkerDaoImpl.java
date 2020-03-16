package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.entities.WorkerEntity;
import eu.exadel.practice.guestregistration.data.dao.WorkerDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkerDaoImpl implements WorkerDao {
    MongoTemplate mongoTemplate;

    @Autowired
    public WorkerDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), WorkerEntity.class);
    }

    @Override
    public Optional<WorkerEntity> findById(ObjectId id) {
        return Optional.ofNullable (mongoTemplate.findById(id, WorkerEntity.class));
    }

    @Override
    public List<WorkerEntity> findAll() {
        return mongoTemplate.findAll(WorkerEntity.class);
    }

    @Override
    public WorkerEntity save(WorkerEntity workerEntity) {
        workerEntity.setId(ObjectId.get());
        return mongoTemplate.save(workerEntity);
    }

    @Override
    public WorkerEntity update(WorkerEntity workerEntity) {
        if (mongoTemplate.exists(Query.query(Criteria.where("id").is(workerEntity.getId())), WorkerEntity.class)) {
            workerEntity.setId(workerEntity.getId());
            return mongoTemplate.save(workerEntity);
        }
        return null;
    }

    @Override
    public void deleteById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, WorkerEntity.class);
    }

    @Override
    public boolean existById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, WorkerEntity.class);
    }
}
