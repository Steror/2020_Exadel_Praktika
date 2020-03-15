package practice.guestregistry.config;

//@Configuration
//public class MongoEmbeddedConfig {
//    private Environment environment;
//
//    @Autowired
//    public MongoEmbeddedConfig (Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    @DependsOn("embeddedMongoServer")
//    public MongoClient mongoClient () {
//
//        int port = this.environment.getProperty("local.mongo.port", Integer.class);
//        return new MongoClient("localhost", port);
//    }
//
//}
