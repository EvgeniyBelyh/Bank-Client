package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.DepositDTO;

/**
 * Отображает панель с вкладами для открытия
 *
 * @author Евгений Белых
 */
public class DepositOpenPanel implements IsWidget {

    private VerticalPanel vPanel = new VerticalPanel();;
    private List<DepositDTO> depositList = new ArrayList();
    private MainPage mainPage;
    private AsyncCallback<List<DepositDTO>> depositCallback;
    
    public DepositOpenPanel(MainPage mainPage) {

        this.mainPage = mainPage;
        
        // создаем заголовок 
        HTML cardHeader = new HTML("<h2>Открытие вклада</h2>");
        cardHeader.setStyleName("operations_container h2");
        vPanel.add(cardHeader);
        
        depositCallback = new AsyncCallback<List<DepositDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
            }

            @Override
            public void onSuccess(List<DepositDTO> result) {

                depositList = result;
                createTable();
            }
        };
        LoginService.Util.getInstance().getDeposits(depositCallback);

    }

    private void createTable() {


        // добавляем таблицу с информацией о вкладах
        FlexTable cardsTable = new FlexTable();
        cardsTable.addStyleName("simple-little-table");
        // заголовок таблицы
        cardsTable.setText(0, 0, "Наименование");
        cardsTable.setText(0, 1, "Срок вклада, дни");
        cardsTable.setText(0, 2, "Ставка");
        cardsTable.setText(0, 3, " ");
        // форматируем заголовок
        for (int m = 0; m < 4; m++) {
            cardsTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        int i = 1; // индекс строки в таблице
        
        // выбираем только карточные счета
        for (DepositDTO deposit : depositList) {

            // наименование депозита
            cardsTable.setText(i, 0, deposit.getName());
            cardsTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
            // срок
            cardsTable.setText(i, 1, String.valueOf(deposit.getDuration()));
            cardsTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
            // ставка
            cardsTable.setText(i, 2, String.valueOf(deposit.getInterestRate()));
            cardsTable.getCellFormatter().addStyleName(i, 2, "simple_cell");
            // кнопка открытия
            Button openButton = new Button("Открыть");
            openButton.addClickHandler(new DepositClickHandler(deposit));
            cardsTable.setWidget(i, 3, openButton);
            cardsTable.getCellFormatter().addStyleName(i, 3, "simple_cell");

            i++;
            
        }

        vPanel.add(cardsTable);

    }
    
    
    @Override
    public Widget asWidget() {
        return vPanel;
    }
    
    
    class DepositClickHandler implements ClickHandler {
        
        private DepositDTO deposit;
        
        public DepositClickHandler(DepositDTO deposit) {
            this.deposit = deposit;
        }
        
        @Override
        public void onClick(ClickEvent event) {
            
            AccountDTO account = new AccountDTO();
            account.setAccountName(deposit.getName());
            account.setAccountTypeId(AccountTypes.DEPOSIT.getId());
            account.setBalance(0);
            account.setBlocked(false);
            account.setClientId(1); //TODO
            account.setCurrencyId(1);
                       
            // определяем дату закрытия вклада
            Date currentDate = new Date(System.currentTimeMillis());
            CalendarUtil.addDaysToDate(currentDate, deposit.getDuration());
            account.setExpirationDate(currentDate);
            
            account.setInterestRate(deposit.getInterestRate());
            
            Window.alert("Вы выбрали депозит: " + String.valueOf(deposit.getId()) + " " + deposit.getName());

        }
        
        
        
    }

}
