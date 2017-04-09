package ru.mti.bankclient.client;

import ru.mti.bankclient.shared.ClientDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;

/**
 * Главная страница программы
 *
 * @author Белых Евгений
 */
public class MainPage extends TemplatePage {

    public static ClientDTO user; // Объект клиента
    public static int CURRENT_BANK = 1; // Код банка в справочнике банков

    public MainPage() {

        super();

        // создаем панель приветствия в хедере
        createWelcomePanel();
        // создаем меню
        createMenuBlocks();
        // создаем отображение центральной панели
        createCenterPanel();
    }

    /**
     * создает панель приветствия в хедере страницы
     */
    private void createWelcomePanel() {

        // собираем панель приветствия
        VerticalPanel welcomPanel = new VerticalPanel();
        welcomPanel.add(new HTML("<p>Добро пожаловать,<br>" + user.getName() + "</p>"));
        welcomPanel.setStyleName("welcom_client_container");

        // делаем ссылку для выхода
        Hyperlink exitLink = new Hyperlink();
        exitLink.setText("Выход");
        exitLink.addClickHandler(new ClickHandler() {
            @Override
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
        this.leftBodyPanel.add(new TransferMenuBlock(this));
        // добавляем блок меню оплаты услуг
        this.leftBodyPanel.add(new ServicePayMenuBlock());
        // добавляем блок меню операций со счетами
        this.leftBodyPanel.add(new AccountOperationsMenuBlock());
        // добавляем блок меню клиента
        this.leftBodyPanel.add(new ClientMenuBlock());
    }

    /**
     * создает центральную панель главной страницы
     */
    private void createCenterPanel() {

        // создаем заголовок 
        HTML cardHeader = new HTML("<h2>Карты</h2>");
        cardHeader.setStyleName("operations_container h2");
        this.centerBodyPanel.add(cardHeader);
                
        // добавляем таблицу с информацие о картах
        FlexTable cardsTable = new FlexTable();
        cardsTable.addStyleName("simple-little-table");
        // заголовок таблицы
        cardsTable.setText(0,0,"Тип");
        cardsTable.setText(0,1,"Номер счета");
        cardsTable.setText(0,2,"Номер карты");
        cardsTable.setText(0,3,"Баланс");
        // форматируем заголовок
        for(int m = 0; m < 4; m++) {
            cardsTable.getCellFormatter().addStyleName(0,m,"table_header");
        }
        
        int i = 1; // индекс строки в таблице
        
        // выбираем только карточные счета
        for (AccountDTO account : user.getAccountList()) {

            if (account.getAccountTypeId() != AccountTypes.DEPOSIT.getId()) {
                // тип счета и валюта счета
                cardsTable.setText(i, 0, account.getAccountTypeName() + "(" + account.getCurrencyName() + ")");
                cardsTable.getCellFormatter().addStyleName(i,0,"simple_cell");                
                // номер счета
                cardsTable.setText(i, 1, account.getNumber());
                cardsTable.getCellFormatter().addStyleName(i,1,"simple_cell"); 
                // номер карты
                cardsTable.setText(i, 2, account.getCardNumber());
                cardsTable.getCellFormatter().addStyleName(i,2,"simple_cell"); 
                // текущий баланс
                cardsTable.setText(i, 3, String.valueOf(account.getBalance()) + " " + account.getCurrencyName());
                cardsTable.getCellFormatter().addStyleName(i,3,"simple_cell"); 
                i++;
            }
        }
        
        this.centerBodyPanel.add(cardsTable);
        
        // создаем заголовок 
        HTML depositHeader = new HTML("<h2>Вклады</h2>");
        depositHeader.setStyleName("operations_container h2");
        this.centerBodyPanel.add(depositHeader);
        
        // добавляем таблицу с информацие о вкладах
        FlexTable depositTable = new FlexTable();
        depositTable.addStyleName("simple-little-table");
        
        // заголовок таблицы
        depositTable.setText(0,0,"Тип");
        depositTable.setText(0,1,"Номер счета");
        depositTable.setText(0,2,"Баланс");
        
        // форматируем заголовок
        for(int m = 0; m < 3; m++) {
            depositTable.getCellFormatter().addStyleName(0,m,"table_header");
        }
        
        i = 1; // обновляем значение индекса строки таблицы
        
        // выбираем только депозитные счета
        for (AccountDTO account : user.getAccountList()) {
            
            if (account.getAccountTypeId() == AccountTypes.DEPOSIT.getId()) {
                // тип счета и валюта счета
                depositTable.setText(i, 0, account.getAccountTypeName() + "(" + account.getCurrencyName() + ")");
                depositTable.getCellFormatter().addStyleName(i,0,"simple_cell");
                // номер счета
                depositTable.setText(i, 1, account.getNumber());
                depositTable.getCellFormatter().addStyleName(i,1,"simple_cell");
                // текущий баланс
                depositTable.setText(i, 2, String.valueOf(account.getBalance()) + " " + account.getCurrencyName());
                depositTable.getCellFormatter().addStyleName(i,2,"simple_cell");
                i++;
            }
        }
        
        this.centerBodyPanel.add(depositTable);
    }

}
