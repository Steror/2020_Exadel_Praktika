package practice.guestregistry.data.mongo.config;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.*;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.domain.Person;

import javax.annotation.PostConstruct;

@Configuration
@DependsOn("mongoTemplate")
public class EntitiesIndexConfig {
    final MongoTemplate mongoTemplate;

    @Autowired
    public EntitiesIndexConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @PostConstruct
    public void initIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(PersonEntity.class);
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoTemplate.getConverter().getMappingContext());
        resolver.resolveIndexFor(PersonEntity.class).forEach(elem -> {
            indexOps.ensureIndex(elem);
//            log.debug("created index:" + elem.getIndexKeys().toJson() + " " + elem.getIndexOptions().toJson() + " " + elem.toString());
        });
//        System.out.println("\n\n\n");
//        mongoTemplate.indexOps(PersonEntity.class).getIndexInfo().stream().forEach(System.out::println);
//        System.out.println("\n\n\n\n\n\n\n\n\n\nI WAS SUMMONED DURING TESTING\n\n\n\n\n\n\n\n\n");
//
//        mongoTemplate.indexOps(PersonEntity.class).dropAllIndexes();
//        mongoTemplate.indexOps(PersonEntity.class) // so index is applied only to "docsB" collection
//                .ensureIndex(
//                        new Index().on("phoneNumber", Sort.Direction.ASC).unique()
//                );
//        mongoTemplate.indexOps(PersonEntity.class) // so index is applied only to "docsB" collection
//                .ensureIndex(
//                        new Index().on("email", Sort.Direction.ASC).unique()
//                );
//        TextIndexDefinition textIndexDefinition = TextIndexDefinition.builder()
//                .onField("firstName")
//                .onField("middleName")
//                .onField("lastName")
//                .build();
//        mongoTemplate.indexOps(PersonEntity.class) // so index is applied only to "docsB" collection
//                .ensureIndex(textIndexDefinition);
//    }
    }
}
