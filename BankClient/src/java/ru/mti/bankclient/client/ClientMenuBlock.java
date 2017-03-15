
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
    
    public ClientMenuBlock() {
        
        super("Мое меню");

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
                //TODO открывать форму ввода данных
            }
        });
        // ссылка на страницу с сообщениями от банка
        Hyperlink bankMessages = new Hyperlink();
        bankMessages.setText("Сообщения от банка");
        bankMessages.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        });
        // ссылка на страницу тарифов
        Hyperlink tarif = new Hyperlink();
        tarif.setText("Тарифы");
        tarif.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        });
        // ссылка на страницу помощи
        Hyperlink help = new Hyperlink();
        help.setText("Помощь");
        help.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
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
    
}
