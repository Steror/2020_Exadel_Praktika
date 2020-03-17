package practice.guestregistry.data.mongo.daoimpl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.data.api.domain.Worker;
import practice.guestregistry.data.mongo.entities.WorkerEntity;
import practice.guestregistry.data.mongo.mappers.WorkerMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WorkerDaoImpl implements WorkerDao {
    private MongoTemplate mongoTemplate;
    private WorkerMapper mapper;
//    SequenceDao sequenceDao;
//    private static final String HOSTING_SEQ_KEY = "worker";

    @Autowired
    public WorkerDaoImpl(MongoTemplate mongoTemplate, WorkerMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    public Worker findById (String id) {
        WorkerEntity workerDB = mongoTemplate.findById(new ObjectId(id), WorkerEntity.class);
//        return Optional.ofNullable(mapper.map(workerDB));
        return mapper.map(workerDB);
    }


    public List<Worker> findAll () {
        List<WorkerEntity> list = mongoTemplate.findAll(WorkerEntity.class);
        System.out.println(list);
        return mongoTemplate.findAll(WorkerEntity.class)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Worker save (Worker worker) {
        //ObjectId temp = sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY);
        WorkerEntity mappedWorkerEntity = mapper.map(worker);
        mappedWorkerEntity.setId(ObjectId.get());
        return mapper.map(mongoTemplate.save(mappedWorkerEntity));
    }

    @Override
    public Worker update (Worker worker) {
        WorkerEntity mappedWorkerEntity = mapper.map(worker);
        return mapper.map(mongoTemplate.save(mappedWorkerEntity));
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(new ObjectId(id)));
        mongoTemplate.remove(query, WorkerEntity.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.findAllAndRemove(Query.query(Criteria.where("id").exists(true)), WorkerEntity.class);
    }

    @Override
    public boolean exist(Worker worker) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(mapper.map(worker))), WorkerEntity.class);
    }

    @Override
    public boolean existById(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(new ObjectId(id))), WorkerEntity.class);
    }
}
