package com.project.db.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.project.db.config.MongoConfig;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.descending;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MongoService {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    MongoConfig mongoConfig;
    public <T> String save(T object, Class<T> clazz){
        try{
            Map<String, MongoClient> mongoClientMap=applicationContext.getBeansOfType(MongoClient.class);
            System.out.println("Beans: " + mongoClientMap.keySet());
            if(null!=mongoClientMap.get("M1")){
                System.out.println("config found");
                MongoClient mongoClient=mongoClientMap.get("M1");

                MongoDatabase db = mongoClient.getDatabase("ToDo");
                MongoCollection<T> col = db.getCollection("List",clazz);
                // Insert
                InsertOneResult toDo=col.insertOne(object);
                return toDo.getInsertedId().toString();
            }
            System.out.println("No config found");
            mongoConfig.registerMongoFor1stTime();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
    }

    public <T> List<T> get(Class<T> clazz,String user){
        try{
            Map<String, MongoClient> mongoClientMap=applicationContext.getBeansOfType(MongoClient.class);
            System.out.println("Beans: " + mongoClientMap.keySet());
            if(null!=mongoClientMap.get("M1")){
                System.out.println("config found");
                MongoClient mongoClient=mongoClientMap.get("M1");

                MongoDatabase db = mongoClient.getDatabase("ToDo");
                MongoCollection<T> col = db.getCollection("List",clazz);
                return col.find(eq("userName",user)).sort(ascending("createdTime")).into(new ArrayList<>());
            }
            System.out.println("No config found");
            mongoConfig.registerMongoFor1stTime();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
    }
    public <T> DeleteResult delete(Class<T> clazz, String user, String uid){
        try{
            Map<String, MongoClient> mongoClientMap=applicationContext.getBeansOfType(MongoClient.class);
            System.out.println("Beans: " + mongoClientMap.keySet());
            if(null!=mongoClientMap.get("M1")){
                System.out.println("config found");
                MongoClient mongoClient=mongoClientMap.get("M1");

                MongoDatabase db = mongoClient.getDatabase("ToDo");
                MongoCollection<T> col = db.getCollection("List",clazz);
                Map<String, Object> map = new HashMap<>();
                map.put("userName", "aswin");
                map.put("uuid", uid);
                Document filter = new Document(map);
                return col.deleteOne(filter);
            }
            System.out.println("No config found");
            mongoConfig.registerMongoFor1stTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
