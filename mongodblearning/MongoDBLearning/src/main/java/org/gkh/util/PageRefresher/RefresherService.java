/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.util.PageRefresher;

import java.awt.Desktop;
import java.net.URL;

/**
 *
 * @author hepgk
 */
public class RefresherService implements Runnable {

    private Desktop desktop;
    
    private String name;

    private static final String threadCheckerUrl = "http://msb21p.champ.aero:18001/queue_info_count_web.html";

    public Desktop getDesktop() {
        return desktop;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            System.out.println("Executing: " + threadCheckerUrl);
            try {
                URL url = new URL(threadCheckerUrl);
                desktop.browse(url.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
