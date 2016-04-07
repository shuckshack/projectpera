/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

import java.io.StringWriter;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

/**
 *
 * @author Dell - User
 */
public class JsonDocPrinter {

    public static void printJson(BsonDocument doc) {
        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, false));

//        new DocumentCodec().encode(jsonWriter, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        new BsonDocumentCodec().encode(jsonWriter, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        System.out.println(jsonWriter.getWriter());

        System.out.flush();
    }

    public static void printJson(Document doc) {
        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));

        new DocumentCodec().encode(jsonWriter, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
//        new BsonDocumentCodec().encode(jsonWriter, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        System.out.println(jsonWriter.getWriter());

        System.out.flush();
    }
}
