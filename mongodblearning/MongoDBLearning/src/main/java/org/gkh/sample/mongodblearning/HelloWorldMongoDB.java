/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

/**
 *
 * @author Dell - User
 */
public class HelloWorldMongoDB {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        
        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        
        MongoCollection<BsonDocument> coll = db.getCollection("test", BsonDocument.class);
        
        new HelloWorldMongoDB().testDocument();
    }
    
    private static void printJson(Document doc) {
        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
        
        new DocumentCodec().encode(jsonWriter, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        
        System.out.println(jsonWriter.getWriter());
        
        System.out.flush();
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