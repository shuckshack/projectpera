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
        config.setClassForTemplateLoading(SparkFormHandling.class, "/fruitpicker");

        Spark.get("/fruit", (Request rqst, Response rspns) -> {

            return processFruitWriter(config);
        });

        Spark.get("/fruit/:name", (Request rqst, Response rspns) -> {
            String name = rqst.params("name");

            return processFruitWriter(config, name);
        });

        Spark.post("/favourite_fruit", (Request rqst, Response rspns) -> {
            String pickedFruit = rqst.queryParams("fruit");
            String name = rqst.params("name");
            if (pickedFruit == null || pickedFruit.length() == 0) {
                return "Why didn't you pick a fruit?";
            } else {
                return "Favourite fruit: " + pickedFruit;
            }
//            return new Object();
        });

    }

    private static StringWriter processFruitWriter(Configuration config) {
        return processFruitWriter(config, null);
    }

    private static StringWriter processFruitWriter(Configuration config, String name) {
        StringWriter writer = new StringWriter();
        try {
            Template template = config.getTemplate("fruitpicker.ftl");

            Map<String, Object> templateParamMap = new HashMap<>();
            templateParamMap.put("fruits", Arrays.asList("Apples", "Oranges", "Bananas", "Mangoes"));
            if (name == null || name.length() == 0) {
                templateParamMap.put("name", "DEFAULT");
            } else {
                templateParamMap.put("name", name);
            }

            template.process(templateParamMap, writer);

        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(SparkFormHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writer;
    }

}
