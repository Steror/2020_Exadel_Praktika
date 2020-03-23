//package practice.guestregistry.data.mogno;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
//import de.flapdoodle.embed.process.runtime.Network;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import practice.guestregistry.data.api.dao.PersonDao;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.UUID;
//
////@Configuration
////@Profile("junit")
////@Configuration
//@ComponentScan(basePackages = "practice.guestregistry")
//@EnableMongoRepositories({"practice.guestregistry.data.mongo.daoimpl", "practice.guestregistry.data.api.dao"})
//public class EmbeddedMongoDb {
//    private int port;
//    private MongodProcess process;
//    public EmbeddedMongoDb() {
//        this.port = 27017;
//    }
//    public EmbeddedMongoDb(int port) {
//        this.port = port;
//    }
//    public void start() throws IOException {
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        MongodExecutable executable = starter.prepare(new MongodConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(new Net(port,Network.localhostIsIPv6()))
//                .build());
//        process = executable.start();
//    }
//    public void stop() {
//        process.stop();
//    }
//
//
////    @Value("${spring.data.mongodb.port}")
////    private int port;
////
////    @Value("${spring.data.mongodb.database}")
////    private String database;
////
////    @Value("${spring.data.mongodb.host}")
////    private String host;
////
//////    private int port;
////    private String bindIp;
////
////    private MongodProcess process;
////    private MongoClient client;
//////    private MongoDatabase database;
////
////    public EmbeddedMongoDb() {
////        this.port = 27017;
////        this.bindIp = "localhost";
////    }
////
////    public EmbeddedMongoDb(int port) {
////        this.port = port;
////    }
////
////    @Bean
////    public MongoDatabase mongoDatabase() {
////
////        MongoClient client = new MongoClient(host, port);
////        MongoDatabase mongoDatabase = client.getDatabase(database);
////        return mongoDatabase;
////
////    }
////
//////    @Bean
//////    public MongoTemplate mongoTemplate() throws IOException {
//////        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
//////        mongo.setBindIp("localhost");
//////        MongoClient mongoClient = mongo.getObject();
//////        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "test_or_whatever_you_want_to_call_this_db");
//////        return mongoTemplate;
//////    }
////
//////    @Bean
//////    public MongoClient mongoClient() {
//////        return new MongoClient("localhost", 27017);
//////    }
////
////    public void start() throws IOException {
////
//////        MongodStarter starter = MongodStarter.getDefaultInstance();
//////        IMongodConfig mongodConfig = new MongodConfigBuilder()
//////                .version(Version.Main.PRODUCTION)
//////                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
//////                .build();
////////
//////        MongodExecutable executable = starter.prepare(new MongodConfigBuilder()
//////                .version(Version.Main.PRODUCTION)
//////                .net(new Net(bindIp, port, false))
//////                .build());
//////        MongodExecutable mongodExecutable = null;
//////        try {
//////            mongodExecutable = starter.prepare(mongodConfig);
//////            MongodProcess mongod = mongodExecutable.start();
//////
//////            MongoClient mongo = new MongoClient(bindIp, port);
//////            MongoDatabase db = mongo.getDatabase("test");
//////            db.createCollection("testing");
////////            db.
////////            db.save(new BasicDBObject("testDoc", new Date()));
//////
//////        } finally {
//////            if (mongodExecutable != null)
//////                mongodExecutable.stop();
//////        }
//////
////////        MongodForTestsFactory factory = null;
////////        try {
////////            factory = MongodForTestsFactory.with(Version.Main.PRODUCTION);
////////
////////            MongoClient mongo = factory.newMongo();
////////            MongoDatabase db = mongo.getDatabase("test-" + UUID.randomUUID());
////////            DBCollection col =
////////                    db.createCollection("testCol");
////////            col.save(new BasicDBObject("testDoc", new Date()));
////////
////////        } finally {
////////            if (factory != null)
////////                factory.shutdown();
////////        }
//////
//////        process = executable.start();
////////        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/test");
////////        MongoClient client = new MongoClient(uri);
////////        MongoDatabase mongoDatabase = client.getDatabase(uri.getDatabase());
////////        mongoDatabase.createCollection("");
////
////    }
////
////    public void stop() {
//////        process.stop();
////    }
//}
