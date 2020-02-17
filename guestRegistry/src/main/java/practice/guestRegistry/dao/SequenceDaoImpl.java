package practice.guestRegistry.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import practice.guestRegistry.exceptions.SequenceException;
import practice.guestRegistry.models.SequenceId;

@Repository
public class SequenceDaoImpl implements SequenceDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SequenceDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public long getNextSequenceId(String key) throws SequenceException {
        Query query = new Query(Criteria.where("_id").is(key));

        Update update = new Update();
        update.inc("seq", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);

        if (seqId == null) {
            throw new SequenceException("Unable to get sequence id for key : " + key);
        }

        return seqId.getSeq();
    }

    @Override
    public void initCollection(String tableName) {
        mongoTemplate.save(new SequenceId(tableName, 0));
    }
}
