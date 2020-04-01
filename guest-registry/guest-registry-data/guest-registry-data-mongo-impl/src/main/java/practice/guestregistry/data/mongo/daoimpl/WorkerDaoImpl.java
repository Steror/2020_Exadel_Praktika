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
import practice.guestregistry.data.api.dao.CardDao;
import practice.guestregistry.data.api.dao.PersonDao;
import practice.guestregistry.data.api.dao.WorkerDao;
import practice.guestregistry.data.mongo.entities.CardEntity;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.data.mongo.entities.WorkerEntity;
import practice.guestregistry.data.mongo.mappers.WorkerMapper;
import practice.guestregistry.domain.Worker;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WorkerDaoImpl implements WorkerDao {
    private final MongoTemplate mongoTemplate;
    private final WorkerMapper mapper;
    private final CardDao cardDao;
    private final PersonDao personDao;
    private static final Logger log = LoggerFactory.getLogger(WorkerDaoImpl.class);
    @Autowired
    public WorkerDaoImpl(MongoTemplate mongoTemplate, WorkerMapper mapper, CardDao cardDao, PersonDao personDao) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
        this.cardDao = cardDao;
        this.personDao = personDao;
    }

    public Worker findById (String id) {
        WorkerEntity workerEntity = mongoTemplate.findById(id, WorkerEntity.class);
        if (workerEntity == null) {
            throw new ResourceNotFoundException("Cannot find worker by this id doesnt exist");
        } else {
            Worker worker = mapper.map(workerEntity);
            log.debug("[findById] returning mapped to domain: " + worker);
            return worker;
        }
    }


    public List<Worker> findAll () {
        return mongoTemplate.findAll(WorkerEntity.class)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    //TODO:on creation look up a person, if not exist create or increase reference count
    public void add (Worker worker) {
        WorkerEntity mappedWorkerEntity = mapper.map(worker);
        if (mappedWorkerEntity.getId() != null) {
            throw new EntityCreationException("id must be null");
        }

        //fully reconstruct card
        CardEntity cardEntity = mongoTemplate.findById(worker.getCardId(), CardEntity.class);
        mappedWorkerEntity.setCardEntity(cardEntity);
        log.debug("[add] mapped WorkerEntity: " + mappedWorkerEntity);

        personDao.increaseReferencedCount(worker.getPersonId());
        WorkerEntity savedWorker = mongoTemplate.save(mappedWorkerEntity);
        worker.setId(savedWorker.getId().toHexString());
        log.debug("[add] mapped Saved Worker entity: " + mappedWorkerEntity);
    }

    @Override
    public void update (Worker worker) {
        log.debug("[update] got worker: " + worker);
        WorkerEntity mappedWorkerEntity = mapper.map(worker);
        log.debug("[update] mapped worker to: " + mappedWorkerEntity);
        if (mappedWorkerEntity.getId() == null) {
            throw new EntityUpdateException("id must be null");
        }
        WorkerEntity saved= mongoTemplate.save(mappedWorkerEntity);
        System.out.println("\n\n\n" + saved + "\n\n\n");
    }

    @Override
    //TODO:mb in service: on deletion if person reference count is 1, delete person
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        WorkerEntity deletedWorkerEntity = mongoTemplate.findAndRemove(query, WorkerEntity.class);
        if (deletedWorkerEntity == null) {
            throw new ResourceNotFoundException("Cannot delete worker by this id doesnt exist");
        } else {
            PersonEntity personEntity = deletedWorkerEntity.getPersonEntity();
            if (personEntity.getReferenced() <= 1) {
                personDao.deleteById(personEntity.getId().toHexString());
            } else {
                personDao.decreaseReferencedCount(personEntity.getId().toHexString());
            }
        }

    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(Query.query(Criteria.where("id").exists(true)), WorkerEntity.class);
    }

    @Override
    public boolean exist(Worker worker) {
        return mongoTemplate.exists(Query.query(Criteria.byExample(mapper.map(worker))), WorkerEntity.class);
    }

    @Override
    public boolean existById(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(id)), WorkerEntity.class);
    }
}
