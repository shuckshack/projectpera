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
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author hepgk
 */
public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");
        
        MongoClient client = new MongoClient("localhost", 27017);

        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> coll = db.getCollection("hello");
        
        coll.drop();
        
        coll.insertOne(new Document("name", new BsonString("MongoDB")));

        Spark.get("/", (Request rqst, Response rspns) -> {
            StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = config.getTemplate("hello.ftl");
                
                Map<String, Object> helloMap = new HashMap<>();
                
                Document doc = coll.find().first();
//                String name = doc.get("name").toString();
//                
//                helloMap.put("name", name);
                
                helloTemplate.process(doc, writer);
                
            } catch (MalformedTemplateNameException | ParseException | TemplateException ex) {
                Logger.getLogger(HelloWorldFreemarkerStyle.class.getName()).log(Level.SEVERE, null, ex);
                Spark.halt(500);
            } catch (IOException ex) {
                Logger.getLogger(HelloWorldFreemarkerStyle.class.getName()).log(Level.SEVERE, null, ex);
                Spark.halt(500);
            }
            return writer;
        });
    }

}
