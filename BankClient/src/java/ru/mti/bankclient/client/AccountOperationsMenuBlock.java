
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Блок меню операций со счетами
 * @author Белых Евгений
 */
public class AccountOperationsMenuBlock extends TransferMenuBlock {
    
    public AccountOperationsMenuBlock() {
        
        super("Операции со счетами");

    }
    
    /**
     * создает тело блока меню
     */
    @Override
    protected void createBody() {
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        // ссылка на страницу открытия вклада
        Hyperlink openDeposit = new Hyperlink();
        openDeposit.setText("Открыть вклад");
        openDeposit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных для оплаты услуг
            }
        });
        // ссылка на страницу закрытия вклада
        Hyperlink closeDeposit = new Hyperlink();
        closeDeposit.setText("Закрыть вклад");
        closeDeposit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        });
        // ссылка на страницу блокировки карты
        Hyperlink cardBlock = new Hyperlink();
        cardBlock.setText("Блокировать карту");
        cardBlock.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        });
        // ссылка на страницу выпуска виртуальной карты
        Hyperlink issueVirtualCard = new Hyperlink();
        issueVirtualCard.setText("Выпустить виртуальную карту");
        issueVirtualCard.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        }); 
        // ссылка на страницу остории операций
        Hyperlink operationsHistory = new Hyperlink();
        operationsHistory.setText("Блокировать карту");
        operationsHistory.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //TODO открывать форму ввода данных
            }
        }); 
        // добавляем ссылки в тело блока
        body.add(openDeposit);
        body.add(closeDeposit);
        body.add(cardBlock);
        body.add(issueVirtualCard);
        body.add(operationsHistory);
        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        // добавляем тело на панель
        this.add(body);
    }
    
}
