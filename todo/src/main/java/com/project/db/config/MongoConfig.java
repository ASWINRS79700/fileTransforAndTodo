package com.project.db.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.*;

@Configuration
public class MongoConfig {
    @Autowired
    private ConfigurableApplicationContext context;
    public void registerMongoFor1stTime(){


        // Get BeanFactory
        DefaultListableBeanFactory beanFactory =
                (DefaultListableBeanFactory) context.getBeanFactory();

        // Create MongoClient dynamically
        String uri = "mongodb://localhost:27017/";
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),   // ✅ FIX HERE
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // ✅ Create MongoClient with codec
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(
                                new com.mongodb.ConnectionString("mongodb://localhost:27017")
                        )
                        .codecRegistry(pojoCodecRegistry)
                        .build()
        );
        // Register MongoClient as bean
        beanFactory.registerSingleton("M1", mongoClient);
        System.out.println("Successfully registered the client");

    }
}
