
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
    
    
    public ServicePayMenuBlock() {
        
        super("Оплата услуг");

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
                //TODO открывать форму ввода данных для оплаты услуг
            }
        });
        // ссылка на страницу оплаты услуг Интернет
        Hyperlink internet = new Hyperlink();
        internet.setText("Интернет");
        internet.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных для оплаты услуг
            }
        });
        // ссылка на страницу оплаты услуг ЖКХ
        Hyperlink utilities = new Hyperlink();
        utilities.setText("ЖКХ");
        utilities.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных для оплаты услуг
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
        
}
