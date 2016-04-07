/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning.hw23;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import static org.gkh.sample.mongodblearning.JsonDocPrinter.printJson;

/**
 *
 * @author Dell - User
 */
public class Homework2_3 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<BsonDocument> col = db.getCollection("grades", BsonDocument.class);
        System.out.println("count BEFORE: " + col.count());
        Bson sort = orderBy(ascending("student_id", "score"));
        
//        MongoCursor<BsonDocument> cursor = col.find().sort(sort).iterator();
        try (MongoCursor<BsonDocument> cursor = col.find().sort(sort).iterator()) {
            BsonDocument prevDoc = null, oneDoc;
            while (cursor.hasNext()) {
                oneDoc = cursor.next();
                printJson(oneDoc);
                String type = oneDoc.get("type").asString().getValue();
                if (type.equals("homework")) {
                    if (prevDoc != null) {
                        int oneDocId = oneDoc.get("student_id").asNumber().intValue();
                        int prevDocId = prevDoc.get("student_id").asNumber().intValue();
                        double oneDocScore = oneDoc.get("score").asDouble().getValue();
                        double prevDocScore = prevDoc.get("score").asDouble().getValue();

                        if (oneDocId == prevDocId) {
                            BsonDocument docToRemove = (oneDocScore > prevDocScore) ? prevDoc : oneDoc;
                            col.deleteOne(new BsonDocument("_id", docToRemove.get("_id")));
                            
                            prevDoc = null;
                        } else {
                            prevDoc = oneDoc;
                        }
                    } else {
                        prevDoc = oneDoc;
                    }
                }
            }
            System.out.println("count THEN: " + col.count());
        }
    }
}
