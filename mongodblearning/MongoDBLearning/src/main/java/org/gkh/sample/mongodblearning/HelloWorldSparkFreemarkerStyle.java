/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.mongodblearning;

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
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 *
 * @author hepgk
 */
public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Spark.get("/", (Request rqst, Response rspns) -> {
            StringWriter writer = new StringWriter();
            try {
                Template helloTemplate = config.getTemplate("hello.ftl");
                
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", "SUPERGENE");
                
                helloTemplate.process(helloMap, writer);
                
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
