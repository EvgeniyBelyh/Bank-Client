
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootLayoutPanel;
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
    private ClientDTO user;
    
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
                createTransfersOwnAccounts();
            }
        });
        // ссылка на страницу переводов между клиентами банка
        Hyperlink otherClient = new Hyperlink();
        otherClient.setText("Другому клиенту банка");
        otherClient.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createTransfersInBank();
            }
        });
        // ссылка на страницу переводов в другой банк
        Hyperlink outBank = new Hyperlink();
        outBank.setText("В другой банк");
        outBank.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных для перевода
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
    
    
    public void createTransfersOwnAccounts() {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
        
        LoginService.Util.getInstance().loginFromSessionServer(new AsyncCallback<ClientDTO>() {
            @Override
            public void onSuccess(ClientDTO result) {
                user = result;
            }

            // в случае возникновения ошибки
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером. Повторите попытку позднее");
                
                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
                
                caught.printStackTrace();
            }

        });
        
        // добавляем панель с формами для перевода
        this.mainPage.centerBodyPanel.add(new TransfersOwnAccounts(user, mainPage));
    }
    
    
    public void createTransfersInBank() {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
        
        LoginService.Util.getInstance().loginFromSessionServer(new AsyncCallback<ClientDTO>() {
            @Override
            public void onSuccess(ClientDTO result) {
                user = result;
            }

            // в случае возникновения ошибки
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером. Повторите попытку позднее");
                
                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
                
                caught.printStackTrace();
            }

        });
        
        // добавляем панель с формами для перевода
        this.mainPage.centerBodyPanel.add(new TransfersInBank(user, mainPage));
    }
}
