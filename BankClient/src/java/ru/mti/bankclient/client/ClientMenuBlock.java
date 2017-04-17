
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Блок меню клиента
 * @author Белых Евгений
 */
public class ClientMenuBlock extends TransferMenuBlock {
    
    private static final int TEMPLATES = 1;
    private static final int BANK_MESSAGES = 2;
    private static final int TARIFF = 3;
    private static final int HELP = 4;
    
    private MainPage mainPage;
    
    public ClientMenuBlock(MainPage mainPage) {
        
        super("Мое меню");
        this.mainPage = mainPage;
    }
    
    /**
     * создает тело блока меню
     */
    @Override
    protected void createBody() {
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        // ссылка на страницу с шаблонами документов
        Hyperlink templates = new Hyperlink();
        templates.setText("Шаблоны");
        templates.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createClientMenuPanel(TEMPLATES);
            }
        });
        // ссылка на страницу с сообщениями от банка
        Hyperlink bankMessages = new Hyperlink();
        bankMessages.setText("Сообщения от банка");
        bankMessages.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createClientMenuPanel(BANK_MESSAGES);
            }
        });
        // ссылка на страницу тарифов
        Hyperlink tarif = new Hyperlink();
        tarif.setText("Тарифы");
        tarif.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createClientMenuPanel(TARIFF);
            }
        });
        // ссылка на страницу помощи
        Hyperlink help = new Hyperlink();
        help.setText("Помощь");
        help.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createClientMenuPanel(HELP);
            }
        }); 

        // добавляем ссылки в тело блока
        body.add(templates);
        body.add(bankMessages);
        body.add(tarif);
        body.add(help);

        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        // добавляем тело на панель
        this.add(body);
    }
    
    
     /**
     * отображает нужные виджеты в зависимости от выбранного пользователем
     * типа операции
     * @param menuBlockIndex тип операции: 1 - открыть депозит
     *                                   2 - закрыть депозит
     *                                   3 - блокировать карту
     *                                   4 - выпустить виртуальную карту
     *                                   5 - история операций
     */
    public void createClientMenuPanel(int menuBlockIndex) {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
        
        
        switch(menuBlockIndex) {
            case TEMPLATES:
                this.mainPage.centerBodyPanel.add(new TemplatesPanel(mainPage));
                break;
            case BANK_MESSAGES:
                this.mainPage.centerBodyPanel.add(new ClientMenuBankMessagePanel());
                break;
            case TARIFF:
                this.mainPage.centerBodyPanel.add(new ClientMenuTariffPanel());
                break;
            case HELP:
                this.mainPage.centerBodyPanel.add(new ClientMenuHelpPanel());
                break;                 
        }

    }
}
