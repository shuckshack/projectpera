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
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author Dell - User
 */
public class SparkFormHandling {

    public static void main(String[] args) {
        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(SparkFormHandling.class, "/");

        Spark.get("/", (Request rqst, Response rspns) -> {

            StringWriter writer = new StringWriter();
            try {
                Template template = config.getTemplate("fruitpicker.ftl");

                Map<String, Object> templateParamMap = new HashMap<>();
                templateParamMap.put("fruits", Arrays.asList("Apples", "Oranges", "Bananas", "Mangoes"));

                template.process(templateParamMap, writer);
//                Map<String, Object> helloMap = new HashMap<>();
//                helloMap.put("name", "SUPERGENE");
//
//                template.process(helloMap, writer);

            } catch (MalformedTemplateNameException ex) {
                Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
            return writer;
        });

    }

}
