package com.pac.ride.config.mongo;



import com.mongodb.*;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;


@Slf4j
@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    public String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String dbName;


//    private MongoClientSettings mongoClientSettings(){
//        ConnectionString connectionString = new ConnectionString(mongoUri);
//        return MongoClientSettings.builder()
////                .readConcern(ReadConcern.DEFAULT)
////                .writeConcern(WriteConcern.MAJORITY)
////                .readPreference(ReadPreference.primary())
//                .applyConnectionString(connectionString)
//                .build();
//
//    }


    @Bean
    public MongoClient reactiveMongoClient(){
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean("passengerMongoTemplate")
    public ReactiveMongoTemplate reactiveMongoTemplate(){
        return new ReactiveMongoTemplate(reactiveMongoClient(),dbName);
    }

//    @Bean
//    public Mono<String> testConnection(MongoClient mongoClient){
//        return Mono.from(mongoClient.getDatabase(dbName).runCommand(new Document("ping",1)))
//                .map(result -> "DISPLAY1: MongoDB connection success "+ result.toString() )
//                .doOnError(error-> log.error("DISPLAY1: Problem connecting to mongo "+error.getMessage()));
//    }

}
