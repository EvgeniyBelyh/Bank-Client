
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
    
    private User user;
    
    
    public MainPage(User user) {
        
        super();
        this.user = user;
        
        // создаем панель приветствия в хедере
        createWelcomPanel();
        // создаем меню
        createMenuBlocks();
        
    }
    
    /**
     * создает панель приветствия в хедере страницы
     */
    private void createWelcomPanel() {
        
        // собираем панель приветствия
        VerticalPanel welcomPanel = new VerticalPanel();
        welcomPanel.add(new HTML("<p>Добро пожаловать,<br>" + user.getName() + "</p>"));
        welcomPanel.setStyleName("welcom_client_container");
        
        // делаем ссылку для выхода
        Hyperlink exitLink = new Hyperlink();
        exitLink.setText("Выход");
        exitLink.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {              
                RootLayoutPanel rootPanel = RootLayoutPanel.get();
                // очищаем страницу
                rootPanel.clear();
                // формируем окно ввода логина и пароля
                rootPanel.add(new Login());
            }
        });
        // добавляем ссылку
        welcomPanel.add(exitLink);
        // добавляем панель приветствия в хедер
        this.headerPanel.add(welcomPanel);
        
    }
    
    /**
     * создает блоки меню в левой и правой панелях страницы
     */
    private void createMenuBlocks() {
        
        // добавляем блок меню переводов
        this.leftBodyPanel.add(new TransferMenuBlock());
         // добавляем блок меню оплаты услуг
        this.leftBodyPanel.add(new ServicePayMenuBlock());       
        // добавляем блок меню операций со счетами
        this.leftBodyPanel.add(new AccountOperationsMenuBlock()); 
        // добавляем блок меню клиента
        this.leftBodyPanel.add(new ClientMenuBlock()); 
    }
}
