
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Блок меню денежных переводов
 * 
 * @author Белых Евгений
 */
public class TransferMenuBlock extends VerticalPanel {
    
    private MainPage mainPage;
    private static final int OWN_TRANSFER = 1;
    private static final int IN_TRANSFER = 2;
    private static final int OUT_TRANSFER = 3;
    
    public TransferMenuBlock(MainPage page) {
        
        super();
        this.mainPage = page;
        
        createHeader("Переводы");
        createBody();
        
        // ставим стиль для блока
        this.setStyleName("main_menu_container");
    }

    public TransferMenuBlock(String headerText) {
        
        super();
        
        createHeader(headerText);
        createBody();
        
        // ставим стиль для блока
        this.setStyleName("main_menu_container");
    }
    
     /**
     * создает заголовок блока меню
     */
    protected void createHeader(String headerText) {
        
        // форматируем заголовок
        VerticalPanel header = new VerticalPanel();
        header.add(new HTML("<p>" + headerText + "</p>"));
        header.setStyleName("menu_item_header");
        // добавляем заголовок к панели
        this.add(header);
    }
    
     /**
     * создает тело блока меню
     */
    protected void createBody() {
        
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        // ссылка на страницу переводов между собственными счетами
        Hyperlink ownAccount = new Hyperlink();
        ownAccount.setText("Между своими счетами");
        ownAccount.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createTransfer(OWN_TRANSFER);
            }
        });
        // ссылка на страницу переводов между клиентами банка
        Hyperlink otherClient = new Hyperlink();
        otherClient.setText("Другому клиенту банка");
        otherClient.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createTransfer(IN_TRANSFER);
            }
        });
        // ссылка на страницу переводов в другой банк
        Hyperlink outBank = new Hyperlink();
        outBank.setText("В другой банк");
        outBank.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createTransfer(OUT_TRANSFER);
            }
        }); 
        // добавляем ссылки в тело блока
        body.add(ownAccount);
        body.add(otherClient);
        body.add(outBank);
        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        // добавляем тело на панель
        this.add(body);
    }
    
    /**
     * отображает нужные виджеты в зависимости от выбранного пользователем
     * типа перевода
     * @param transferType тип перевода: 1 - между своими счетами
     *                                   2 - другому клиенту банка
     *                                   3 - в другой банк
     */
    public void createTransfer(int transferType) {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
        
        switch(transferType) {
            case OWN_TRANSFER:
                this.mainPage.centerBodyPanel.add(new TransfersOwnAccounts(mainPage));
                break;
            case IN_TRANSFER:
                this.mainPage.centerBodyPanel.add(new TransfersInBank(mainPage));
                break;
            case OUT_TRANSFER:
                this.mainPage.centerBodyPanel.add(new TransfersOutBank(mainPage));
                break;                
        }

    }
      
}
