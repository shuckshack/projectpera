/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning.hw51;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static java.util.Arrays.asList;
import java.util.List;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.gkh.sample.mongodblearning.JsonDocPrinter;

/**
 *
 * @author Dell - User
 */
public class AggregateHomework5_1 {
    private static final MongoClient CLIENT = new MongoClient("localhost", 27017);
    private static final MongoDatabase DB_BLOG = CLIENT.getDatabase("blog");
    private static final MongoCollection<BsonDocument> COLL_POSTS = DB_BLOG.getCollection("posts", BsonDocument.class);

    public static void main(String[] args) {
        AggregateHomework5_1 test = new AggregateHomework5_1();
        List<Bson> pipeLine = test.pipeLine();
        System.out.println(pipeLine);
        AggregateIterable<BsonDocument> iterable = COLL_POSTS.aggregate(pipeLine);
        
        iterable.iterator().forEachRemaining((BsonDocument doc) -> {
            JsonDocPrinter.printJson(doc);
        });
    }
    
    /**
     * db.posts.aggregate([{$unwind:"$comments"},{$group:{"_id":"$comments.author","comments":{$sum:1}}},{$sort:{"comments":-1}}])
     * @return 
     */
    private List<Bson> pipeLine() {
        List<Bson> pipeline = asList(
                unwind("$comments"),
                group(
                        "$comments.author",
                        sum(
                                "comments", 1
                        )
                ),
                sort(
                        orderBy(
                                asList(
                                        descending("comments")
                                )
                        )
                )
        );
        
        return pipeline;
    }
    
}
