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
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.conversions.Bson;

/**
 *
 * @author Dell - User
 */
public class HelloWorldMongoDBAggregate {
    
    
    private static final MongoClient client = new MongoClient("localhost", 27017);
    private static final MongoDatabase db = client.getDatabase("course");
    private static final MongoCollection<BsonDocument> collection = db.getCollection("zipcodes", BsonDocument.class);

    public static void main(String[] args) {
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
        
        List<BsonDocument> pipeLine = asList(
                groupBy,
                match
        );
        
        List<Bson> builderPipeLine = Aggregates.group("state", Accumulators.sum("totalPop", "$pop"));
        
        AggregateIterable<BsonDocument> cursor = collection.aggregate(pipeLine);
        
        cursor.iterator().forEachRemaining((BsonDocument doc) -> {
            JsonDocPrinter.printJson(doc);
        });
    }
}
