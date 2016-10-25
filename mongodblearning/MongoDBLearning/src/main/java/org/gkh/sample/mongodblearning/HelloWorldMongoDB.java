/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Updates.inc;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.Date;
import java.util.Random;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Indexes.*;
import java.util.ArrayList;
import java.util.List;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import static org.gkh.sample.mongodblearning.JsonDocPrinter.printJson;

/**
 *
 * @author Dell - User
 */
public class HelloWorldMongoDB {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);

        MongoDatabase db = client.getDatabase("school").withReadPreference(ReadPreference.secondary());

        MongoCollection<Document> coll = db.getCollection("students", Document.class);

        Bson filter = eq("scores.type", "exam");

        List<Document> all = coll.find(filter).sort(ascending("_id")).into(new ArrayList<Document>());
//        all.forEach((doc) -> printJson(doc));
//        MongoCursor<Document> cursor = coll.find(filter)
//                                .sort(ascending("student_id", "scores.score")).iterator();

//        cursor.forEach((doc) -> printJson(doc));
        try (MongoCursor<Document> cursor = coll.find(filter)
                .sort(ascending("student_id")).iterator();) {
            while (cursor.hasNext()) {
                Document student = cursor.next();
                List<Document> scores = student.get("scores", ArrayList.class);
                List<Document> newScores = new ArrayList<Document>();
                Document scoreTemp = null;
                boolean isTempGreater = false;
                for (Document score : scores) {
                    if (score.get("type", String.class).equals("homework")) {
                        if (scoreTemp == null) {
                            scoreTemp = score;
                        } else {
                            isTempGreater = scoreTemp.get("score", Double.class).compareTo(score.get("score", Double.class)) >= 1;
                            newScores.add((isTempGreater) ? scoreTemp : score);
                        }
                    } else {
                        newScores.add(score);
                    }
                }
                coll.updateOne(eq("_id", student.get("_id")), new Document("$set", new Document("scores", newScores)));
                scoreTemp = null;
            }
        }

        all = coll.find(filter).sort(ascending("_id")).into(new ArrayList<Document>());
//        all.forEach((doc) -> printJson(doc));
//        new HelloWorldMongoDB().testDocument();

//        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());
        List<BsonDocument> list;
//        insertTest(db);
//        findTest(db);
        list = findArrayTest(db);
        System.out.println("=====findArrayTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = findInArrayTest(db);
        System.out.println("=====findInArrayTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = findInArrayIndexTest(db);
        System.out.println("=====findInArrayIndexTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = findWithFilterTest(db);
        System.out.println("=====findWithFilterTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = findWithProjectionTest(db);
        System.out.println("=====findWithProjectionTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = findWithSortSipLimitTest(db);
        System.out.println("=====findWithSortSipLimitTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = updateTest(db);
        System.out.println("=====updateTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });
        list = deleteTest(db);
        System.out.println("=====deleteTest=====");
        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });

//        new HelloWorldMongoDB().testDocument();
    }

    private static void findInArray(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findInArrayTest", BsonDocument.class);

        coll.drop();

    }

    private static void updateScores(Document doc) {

    }

//    private static void printJson(Document doc) {
//        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
    private static void insertTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("insertTest", BsonDocument.class);

        coll.drop();

        BsonDocument smith = new BsonDocument().append("name", new BsonString("Smith"))
                .append("age", new BsonInt32(30))
                .append("profession", new BsonString("programmer"));

        BsonDocument jones = new BsonDocument().append("name", new BsonString("Smith"))
                .append("age", new BsonInt32(30))
                .append("profession", new BsonString("programmer"));

        coll.insertMany(asList(smith, jones));
    }

    private static void findTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 10; i++) {
            coll.insertOne(new BsonDocument("x", new BsonInt32(i)));
        }

        BsonDocument doc = coll.find().first();

        printJson(doc);

        List<BsonDocument> list = coll.find().into(new ArrayList<BsonDocument>());

        list.stream().forEach((oneDoc) -> {
            printJson(oneDoc);
        });

        try (MongoCursor<BsonDocument> cursor = coll.find().iterator()) {
            cursor.forEachRemaining((oneDoc) -> {
                printJson(oneDoc);
            });
        }

        long count = coll.count();
        System.out.println("count:" + count);

    }
    
    private static List<BsonDocument> findArrayTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findInArrayTest", BsonDocument.class);

        coll.drop();

        List<BsonValue> values;

        for (int i = 0; i < 30; i += 3) {

            values = new ArrayList<>();

            values.add(new BsonInt32(i));
            values.add(new BsonInt32(i + 1));
            values.add(new BsonInt32(i + 2));

            coll.insertOne(new BsonDocument("x", new BsonArray(values)));
        }

        Bson filter = eq("x", new BsonArray(Arrays.asList(new BsonInt32(0),new BsonInt32(1),new BsonInt32(2))));

        return coll.find(filter).into(new ArrayList<BsonDocument>());
    }

    private static List<BsonDocument> findInArrayTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findInArrayTest", BsonDocument.class);

        coll.drop();

        List<BsonValue> values;

        for (int i = 0; i < 30; i += 3) {

            values = new ArrayList<>();

            values.add(new BsonInt32(i));
            values.add(new BsonInt32(i + 1));
            values.add(new BsonInt32(i + 2));

            coll.insertOne(new BsonDocument("x", new BsonArray(values)));
        }

        Bson filter = eq("x", 3);

        return coll.find(filter).into(new ArrayList<BsonDocument>());
    }
    
        private static List<BsonDocument> findInArrayIndexTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findInArrayTest", BsonDocument.class);

        coll.drop();

        List<BsonValue> values;

        for (int i = 0; i < 30; i += 3) {

            values = new ArrayList<>();

            values.add(new BsonInt32(i));
            values.add(new BsonInt32(i + 1));
            values.add(new BsonInt32(i + 2));

            coll.insertOne(new BsonDocument("x", new BsonArray(values)));
        }

        Bson filter = eq("x.2", 8);

        return coll.find(filter).into(new ArrayList<BsonDocument>());
    }

    private static List<BsonDocument> findWithFilterTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findWithFilterTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 10; i++) {
            coll.insertOne(new BsonDocument("x", new BsonInt32(new Random().nextInt(2)))
                    .append("y", new BsonInt32(new Random().nextInt(100))));
        }
        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

        return coll.find(filter).into(new ArrayList<BsonDocument>());

    }

    private static List<BsonDocument> findWithProjectionTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findWithProjectionTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 10; i++) {
            coll.insertOne(new BsonDocument("x", new BsonInt32(new Random().nextInt(2)))
                    .append("y", new BsonInt32(new Random().nextInt(100)))
                    .append("i", new BsonInt32(i)));
        }

        Bson filter = and(eq("x", 0), gt("y", 20), lt("y", 80));
//        Bson projection = new Document("y", 1).append("i", 1);
        Bson projection = fields(include("y", "i"),
                excludeId());

        return coll.find(filter)
                .projection(projection)
                .into(new ArrayList<BsonDocument>());
    }

    private static List<BsonDocument> findWithSortSipLimitTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("findWithSortSipLimitTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    coll.insertOne(new BsonDocument("i", new BsonInt32(i))
                            .append("j", new BsonInt32(j))
                            .append("k", new BsonInt32(k)));
                }
            }
        }

        Bson projection = fields(include("i", "j", "k"),
                excludeId());

//        Bson sort = new Document("i", 1).append("j", -1);
        Bson sort = Sorts.orderBy(Sorts.ascending("i"),
                Sorts.descending("j", "k"));

        return coll.find()
                .projection(projection)
                .sort(sort)
                .limit(2)
                .skip(20)
                .into(new ArrayList<BsonDocument>());
    }

    private static List<BsonDocument> updateTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("updateTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 8; i++) {
            coll.insertOne(new BsonDocument("_id", new BsonInt32(i))
                    .append("x", new BsonInt32(i))
                    .append("y", new BsonBoolean(true)));
        }

//        coll.updateOne(eq("x", 5),
//                new BsonDocument("$set", new BsonDocument("x", new BsonInt32(20)).append("updated", new BsonBoolean(true))));
//        coll.updateOne(eq("x", 5), combine(set("x", 20), set("updated", true)));
//        coll.updateOne(eq("_id", 9), combine(set("x", 20), set("updated", true)), new UpdateOptions().upsert(true));
        coll.updateMany(gte("x", 5), inc("x", 1));
        return coll.find()
                .into(new ArrayList<BsonDocument>());

    }

    private static List<BsonDocument> deleteTest(MongoDatabase db) {
        MongoCollection<BsonDocument> coll = db.getCollection("deleteTest", BsonDocument.class);

        coll.drop();

        for (int i = 0; i < 8; i++) {
            coll.insertOne(new BsonDocument("_id", new BsonInt32(i)));
        }

        coll.deleteOne(eq("_id", 4));

        return coll.find()
                .into(new ArrayList<BsonDocument>());
    }

    private void testDocument() {
        Document document = new Document()
                .append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("l", 1L)
                .append("double", 1.1)
                .append("b", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1, 2, 3));

        printJson(document);

        String str = document.getString("str");
        int i = document.getInteger("int");
    }

}
