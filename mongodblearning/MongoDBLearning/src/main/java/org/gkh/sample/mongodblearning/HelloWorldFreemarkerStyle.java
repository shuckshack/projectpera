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

/**
 *
 * @author hepgk
 */
public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        try {
            Template helloTemplate = config.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            
            Map<String, Object> helloMap = new HashMap<>();
            helloMap.put("name", "SUPERGENE");
            
            helloTemplate.process(helloMap, writer);
            
            System.out.println(writer);
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(HelloWorldFreemarkerStyle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HelloWorldFreemarkerStyle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(HelloWorldFreemarkerStyle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
