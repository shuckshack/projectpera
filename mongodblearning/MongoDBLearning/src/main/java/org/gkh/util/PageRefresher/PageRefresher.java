/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.util.PageRefresher;

import java.awt.Desktop;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author hepgk
 */
public class PageRefresher {
    
    public static void main(String[] args) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        RefresherService service = new RefresherService();
        service.setDesktop(desktop);
        service.setName("Refresher");
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        ScheduledFuture<?> result = executor.scheduleWithFixedDelay(service, 0, 300, TimeUnit.SECONDS);
        
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }
    
}
