/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Main entry point.
 *
 * @author Белых Евгений
 */
public class BankClientEntryPoint implements EntryPoint {
    
    TextBox login;
    PasswordTextBox pass;
    
    /**
     * Creates a new instance of BankClientEntryPoint
     */
    public BankClientEntryPoint() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        
        login = new TextBox();
        login.setStyleName("login_column");
        pass = new PasswordTextBox();
        pass.setStyleName("login_column");
        Button confirmButton = new Button("Вход");
        confirmButton.setStyleName("login_confirm_button");
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(login);
        vPanel.add(pass);
        vPanel.add(confirmButton);
        vPanel.setSize("515px", "220px");
        
        vPanel.setStyleName("login_form_container");
        
        //обработчик для клика по кнопке 'Confirm'
        confirmButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //confirmButton.setEnabled(false);
                sendInfoToServer();
            }
        });
        
        RootPanel.get("login_frame").add(vPanel);
        
    }
    
    public void sendInfoToServer() {
        
        final String symbol = login.getText().toLowerCase().trim();
        login.setFocus(true);

        // проверяем ввод определенных символов в поле логина
        if (!symbol.matches("^[0-9a-z.@]{1,50}$")) {
            Window.alert("Логин не может быть пустым и может состоять из латинских символов, цифр, а также символов '.' или '@'");
            login.setFocus(true);
            login.selectAll();
            return;
        }
        
        if (pass.getText().length() < 8) {
            Window.alert("Пароль не может быть менее 8 символов");
            pass.setFocus(true);
            pass.selectAll();
            return;
        }
        
        
    }
}
