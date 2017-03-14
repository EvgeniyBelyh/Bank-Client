
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Главная страница программы
 * @author Белых Евгений
 */
public class MainPage extends TemplatePage {
    
    
    public MainPage(User user) {
        
        super();
        
        // собираем панель приветствия
        VerticalPanel welcomPanel = new VerticalPanel();
        welcomPanel.add(new HTML("<p>Добро пожаловать,<br>" + user.getName() + "</p>"));
        welcomPanel.setStyleName("welcom_client_container");
        
        // делаем ссылку для выхода
        Hyperlink exitLink = new Hyperlink();
        exitLink.setText("Выход");
        exitLink.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // формируем окно ввода логина и пароля
                RootLayoutPanel.get().add(new Login());
            }
        });
        // добавляем ссылку
        welcomPanel.add(exitLink);
        // добавляем панель приветствия в хедер
        this.headerPanel.add(welcomPanel);
        
        // добавляем блок меню переводов
        this.leftBodyPanel.add(new TransferMenuBlock());
         // добавляем блок меню переводов
        this.leftBodyPanel.add(new ServicePayMenuBlock());       
        
    }
    
}
