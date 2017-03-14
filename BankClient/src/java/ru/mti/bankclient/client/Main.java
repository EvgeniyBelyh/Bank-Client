package ru.mti.bankclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Точка входа в программу
 * @author Белых Евгений
 */
public class Main implements EntryPoint {
    
    
    public void onModuleLoad() {
        
        RootLayoutPanel.get().add(new Login().asWidget());
                
    }
        
}