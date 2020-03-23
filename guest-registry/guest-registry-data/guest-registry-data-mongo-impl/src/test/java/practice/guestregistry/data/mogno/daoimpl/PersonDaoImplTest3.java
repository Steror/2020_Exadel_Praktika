//package practice.guestregistry.data.mogno.daoimpl;
//
//import com.mongodb.*;
//import com.mongodb.client.MongoDatabase;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import practice.guestregistry.data.mogno.EmbeddedMongoDb;
//import practice.guestregistry.data.mongo.daoimpl.PersonDaoImpl;
//import practice.guestregistry.data.mongo.entities.PersonEntity;
//
//import java.io.IOException;
//
////@ContextConfiguration(locations = {"classpath:spring.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes = {EmbeddedMongoConfig2.class})
//public class PersonDaoImplTest3 {
//    private PersonDaoImpl repoImpl;
//    private MongodExecutable mongodExecutable;
//    private MongodProcess mongoProcess;
//    private MongoTemplate mongoTemplate;
//    private MongoDatabase mongoDatabase;
//
//    @Autowired
//    private MongoTemplate mongo;
//    private EmbeddedMongoDb embeddedMongo;
//    @Autowired
//    private PersonDaoImpl personDao; // <- tested repository
//
//
//    @AfterEach
//    public void clean() {
//        mongodExecutable.stop();
//    }
//
//
//        @BeforeEach
//        void setUp() throws IOException { // clean db (slower) or collection (faster) before each test
//            // mongo.getDb().drop();
////        mongo.remove(new Query(), PersonEntity.class);
//            embeddedMongo = new EmbeddedMongoDb();
//            embeddedMongo.start();
//
//
//
////    private static final String IP = "localhost";
////    private static final int PORT = 27017; // <- set MongoDB port
////
////    @TestConfiguration
////    @Import(PersonDaoImpl.class) // <- set the tested repository
//            // @ComponentScan("com.example.repo") <- or package for several repositories
////    static class Config {
////        @Bean
////        public IMongodConfig embeddedMongoConfiguration() throws IOException {
////            return new MongodConfigBuilder()
////                    .version(Version.V4_0_2) // <- set MongoDB version
////                    .version(Version.Main.PRODUCTION)
////                    .net(new Net(IP, PORT, Network.localhostIsIPv6()))
////                    .build();
////        }
//
////        String ip = "localhost";
////        int port = 27017;
////        String dbName = "test";
////
////        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
////                .net(new Net(ip, port, Network.localhostIsIPv6()))
////                .build();
////
////        MongodStarter starter = MongodStarter.getDefaultInstance();
////        mongodExecutable = starter.prepare(mongodConfig);
////        mongoProcess = mongodExecutable.start();
////        MongoClient mongoClient = new MongoClient("localhost", 27017);
////        mongoDatabase = mongoClient.getDatabase("test");
////        mongoTemplate = new MongoTemplate((com.mongodb.client.MongoClient) mongoDatabase, "test");
////        mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
////        mongoTemplate = new MongoTemplate("mongodb://localhost:27017/test");
////        mongoTemplate = new MongoTemplate(MongoDbFactorySupport());
//
////        MappingMongoConverter converter =
////                new MappingMongoConverter(new DefaultDbRefResolver(completeFactory()), new MongoMappingContext());
////        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
////        mongoTemplate = new MongoTemplate(completeFactory(), converter);
//
////        MongoClient client = new MongoClient(ip, port);
////        MongoDatabase database = client.getDatabase("test");
////
////        mongoTemplate = new MongoTemplate(new SimpleMongoClientDbFactory("mongodb://localhost:27017/test"));
//
////        System.out.println(mongoTemplate);
////    public MongoDbFactory completeFactory() throws Exception {
////        MongoClient client = new MongoClient(new MongoClientURI(mongoProperties.getComplete().getUri()));
////        MongoClient client = new MongoClient("mongodb://localhost:27017/test");
////        return new SimpleMongoDbFactory("localhost:27017/test");
//        }
//
//
//
//    @After
//    public void tearDown() {
//        embeddedMongo.stop();
//    }
//
////    }
//
//    @DisplayName("given object to save"
//            + " when save object using MongoDB template"
//            + " then object is saved")
//    @Test
//    public void test() throws Exception {
////        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
////        MongoTemplate mongo = (MongoTemplate)context.getBean("mongoTemplate");
//        mongo.getCollectionNames().forEach(System.out::println);
////        personDao.findAll().stream().forEach(System.out::println);
//
//
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
////         when
////        mongoTemplate.save(objectToSave, "collection");
//        if (mongoTemplate == null) {
//            System.out.println("\nERRORR!!!!\n");
//        }
//        mongoTemplate.findAll(PersonEntity.class).stream().forEach(System.out::println);
//
//        // then
////        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
////                .containsOnly("value");
//    }
//}
