package eu.exadel.practice.guestregistration.data.dao.impl;

import eu.exadel.practice.guestregistration.data.Mappers.WorkerMapper;
import eu.exadel.practice.guestregistration.data.domain.Worker;
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
import java.util.stream.Collectors;

@Repository
public class WorkerDaoImpl implements WorkerDao {
    MongoTemplate mongoTemplate;
    WorkerMapper workerMapper;

    @Autowired
    public WorkerDaoImpl(MongoTemplate mongoTemplate, WorkerMapper workerMapper) {
        this.mongoTemplate = mongoTemplate;
        this.workerMapper = workerMapper;
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), WorkerEntity.class);
    }

    @Override
    public Optional<Worker> findById(String id) {
        return Optional.ofNullable(workerMapper.entityToDomain(mongoTemplate.findById(id, WorkerEntity.class)));
    }

    @Override
    public List<Worker> findAll() {
        return mongoTemplate.findAll(WorkerEntity.class).stream().map( workerEntity ->
                workerMapper.entityToDomain(workerEntity)
        ).collect(Collectors.toList());
    }

    @Override
    public Worker save(Worker worker) {
        WorkerEntity workerEntity = workerMapper.domainToEntity(worker);
        workerEntity.setId(ObjectId.get());
        return workerMapper.entityToDomain(mongoTemplate.save(workerEntity));
    }

    @Override
    public Worker update(Worker worker) {
        WorkerEntity workerEntity = workerMapper.domainToEntity(worker);
        if (mongoTemplate.exists(Query.query(Criteria.where("id").is(workerEntity.getId())), WorkerEntity.class)) {
            return workerMapper.entityToDomain(mongoTemplate.save(workerEntity));
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.findAllAndRemove(query, WorkerEntity.class);
    }

    @Override
    public boolean existById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, WorkerEntity.class);
    }
}
