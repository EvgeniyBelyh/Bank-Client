
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Блок меню операций со счетами
 * @author Белых Евгений
 */
public class AccountOperationsMenuBlock extends TransferMenuBlock {
    
    private static final int OPEN_DEPOSIT = 1;
    private static final int CLOSE_DEPOSIT = 2;
    private static final int BLOCK_CARD = 3;
    private static final int VIRTUAL_CARD = 4;
    private static final int OPER_HISTORY = 5;
    private MainPage mainPage;
    
    public AccountOperationsMenuBlock(MainPage mainPage) {
        
        super("Операции со счетами");
        this.mainPage = mainPage;
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
                //TODO открывать форму ввода данных
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
                createAccountOperation(BLOCK_CARD);
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
        operationsHistory.setText("История операций");
        operationsHistory.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                createAccountOperation(OPER_HISTORY);
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
    
    
     /**
     * отображает нужные виджеты в зависимости от выбранного пользователем
     * типа операции
     * @param transferType тип операции: 1 - открыть депозит
     *                                   2 - закрыть депозит
     *                                   3 - блокировать карту
     *                                   4 - выпустить виртуальную карту
     *                                   5 - история операций
     */
    public void createAccountOperation(int transferType) {
        // убираем содержимое центральной панели
        this.mainPage.centerBodyPanel.clear();
        
        
        switch(transferType) {
            case OPEN_DEPOSIT:
                this.mainPage.centerBodyPanel.add(new TransfersOwnAccounts(mainPage));
                break;
            case CLOSE_DEPOSIT:
                this.mainPage.centerBodyPanel.add(new TransfersInBank(mainPage));
                break;
            case BLOCK_CARD:
                this.mainPage.centerBodyPanel.add(new AccountOperCardBlock(mainPage));
                break;
            case VIRTUAL_CARD:
                this.mainPage.centerBodyPanel.add(new TransfersOutBank(mainPage));
                break; 
            case OPER_HISTORY:
                this.mainPage.centerBodyPanel.add(new OperationsPanel());
                break;                 
        }

    }
}
