/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Dell - User
 */
public class HelloWorldMongoDBAggregate {
    
    
    private static final MongoClient client = new MongoClient("localhost", 27017);
    private static final MongoDatabase db = client.getDatabase("test");
    private static final MongoCollection<BsonDocument> collection = db.getCollection("zips", BsonDocument.class);

    public static void main(String[] args) {
        
        HelloWorldMongoDBAggregate demo = new HelloWorldMongoDBAggregate();

        AggregateIterable<BsonDocument> cursor = collection.aggregate(demo.demoAggregate1());
        
        cursor.iterator().forEachRemaining((BsonDocument doc) -> {
            JsonDocPrinter.printJson(doc);
        });
        
        cursor = collection.aggregate(demo.demoAggregate2());
        
        cursor.iterator().forEachRemaining((BsonDocument doc) -> {
            JsonDocPrinter.printJson(doc);
        });
    }
    
    private List<Bson> demoAggregate1() {
        BsonDocument match = new BsonDocument(
                "$match",
                new BsonDocument(
                        "totalPop",
                        new BsonDocument(
                                "$gte",
                                new BsonInt32(10000000)
                        )
                )
        );
//        db.products.aggregate([{"$group":{"_id":"$category","num_products":{"$sum":1}}}])
//db.zips.aggregate([{"$group":{"_id":"$state","population":{"$sum":"$pop"}}}])
//db.zips.aggregate([{"$group":{"_id":"$state","average_pop":{"$avg":"$pop"}}}])
//db.zips.aggregate([{"$group":{"_id":"$city","postal_codes":{"$addToSet":"$_id"}}}])
//db.zips.aggregate([{"$group":{"_id":"$state","pop":{"$max":"$pop"}}}])
//db.zips.aggregate([{"$project":{"_id":0,"city":{"$toLower":"$city"},"pop":1,"state":1,"zip":"$_id"}}])
//db.zips.aggregate([{$sort:{"state":1,"city":1}}])
//db.zips.aggregate([{"$match":{"pop":{"$gt":100000}}}])
        BsonDocument groupBy = new BsonDocument(
                        "$group", 
                        new BsonDocument(
                                "_id", 
                                new BsonString("$state")
                        ).append(
                                "totalPop",
                                new BsonDocument(
                                        "$sum",
                                        new BsonString("$pop")
                                )
                        )
                );
        
        List<Bson> pipeLine = asList(
                groupBy,
                match
        );
        return pipeLine;
    }
    
    private List<Bson> demoAggregate2() {
        List<Bson> builderPipeLine = asList(Aggregates.group("$state", Accumulators.sum("totalPop", "$pop")), Aggregates.match(Filters.gte("totalPop", 10000000)));
        
        return builderPipeLine;
    }
    
//    private List<Bson> demoAggregate3() {
//        List<Bson> parserPipeLine = asList(Document.parse(json));
//    }
}
