
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Блок меню оплаты услуг
 * 
 * @author Белых Евгений
 */
public class ServicePayMenuBlock extends TransferMenuBlock {
    
    private static final int CELL_PHONE = 1;
    private static final int INTERNET = 2;
    private static final int UTILITIES = 3;
    private MainPage mainPage;
    
    public ServicePayMenuBlock(MainPage mainPage) {
        
        super("Оплата услуг");
        this.mainPage = mainPage;
    }
    
    /**
     * создает тело блока меню
     */   
    @Override
    protected void createBody() {
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        // ссылка на страницу оплаты сотовой связи
        Hyperlink cellPhone = new Hyperlink();
        cellPhone.setText("Сотовая связь");
        cellPhone.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createPaymentOperation(CELL_PHONE);
            }
        });
        // ссылка на страницу оплаты услуг Интернет
        Hyperlink internet = new Hyperlink();
        internet.setText("Интернет");
        internet.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createPaymentOperation(INTERNET);
            }
        });
        // ссылка на страницу оплаты услуг ЖКХ
        Hyperlink utilities = new Hyperlink();
        utilities.setText("ЖКХ");
        utilities.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createPaymentOperation(UTILITIES);
            }
        }); 
        // добавляем ссылки в тело блока
        body.add(cellPhone);
        body.add(internet);
        body.add(utilities);
        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        // добавляем тело на панель
        this.add(body);
    }
    
     /**
     * отображает нужные виджеты в зависимости от выбранного пользователем
     * типа операции
     * @param paymentType тип операции:  1 - сотовая связь
     *                                   2 - Интернет
     *                                   3 - ЖКХ
     */
    public void createPaymentOperation(int paymentType) {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
                
        switch(paymentType) {
            case CELL_PHONE:
                this.mainPage.centerBodyPanel.add(new ServicePayCellPhonePanel(mainPage));
                break;
            case INTERNET:
                this.mainPage.centerBodyPanel.add(new ServicePayInternetPanel(mainPage));
                break;
            case UTILITIES:
                this.mainPage.centerBodyPanel.add(new ServicePayUtilitiesPanel(mainPage));
                break;             
        }

    }    
}
