/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author 1
 */
public class MainPage extends TemplatePage implements EntryPoint {
    

    public void onModuleLoad() {
        
        RootPanel.get("login_frame").add(new MainPage().asWidget());
        
    }
    
    
}
