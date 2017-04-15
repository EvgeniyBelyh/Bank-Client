package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.DepositDTO;

/**
 * Отображает панель с вкладами для открытия
 *
 * @author Евгений Белых
 */
public class ServicePayCellPhonePanel implements IsWidget {

    private VerticalPanel vPanel = new VerticalPanel();
    private List<DepositDTO> depositList = new ArrayList();
    private MainPage mainPage;
    private ClientDTO user;
    private ListBox locAccount = new ListBox();
    private TextBox sumField = new TextBox();
    private TextBox searchField = new TextBox();
    private Button searchButton = new Button("Поиск");
    
    public ServicePayCellPhonePanel(MainPage mainPage) {

        this.mainPage = mainPage;
        this.user = Util.getClientDTO();

        // создаем заголовок 
        HTML depositHeader = new HTML("<h2>Оплата услуг - Сотовая связь</h2>");
        depositHeader.setStyleName("operations_container h2");
        vPanel.add(depositHeader);
        
        sumField.setStyleName("operation_fields");
        locAccount.setStyleName("operation_fields");
        
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setStyleName("operations_container");
        VerticalPanel headers = new VerticalPanel();
        
        VerticalPanel fields = new VerticalPanel();
        fields.setSpacing(10);

        headers.add(new HTML("<h3>Счет списания</h3>"));
        fields.add(locAccount);

        headers.add(new HTML("<h3>Сумма перевода</h3>"));
        fields.add(sumField);
        headers.add(new HTML("<br>"));
        
        hPanel.add(headers);
        hPanel.add(fields);
        
        vPanel.add(hPanel);
        
        for (AccountDTO account : user.getAccountList()) {
            if (account.getAccountTypeId() == AccountTypes.DEBIT_CARD.getId()) {
                locAccount.addItem(account.getAccountTypeName() + " "
                        + account.getNumber() + ", остаток " + account.getBalance()
                        + " " + account.getCurrencyName(), account.getId().toString());
            }
        }
        
        
        AsyncCallback<List<DepositDTO>> depositCallback = new AsyncCallback<List<DepositDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
            }

            @Override
            public void onSuccess(List<DepositDTO> result) {

                depositList = result;
                createTable();
                createCancelButton();
            }
        };
        LoginService.Util.getInstance().getDeposits(depositCallback);

    }
    
    
    
    private void createSearchPanel() {
        
        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.add(new HTML("<h3>Поиск по ИНН</h3>"));
        searchPanel.add(searchField);
        searchPanel.add(this);
        
        // обрабатываем нажатие кнопки Поиск
        searchButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                
            }
        });
        
        searchButton.setStyleName("confirm_button");
        searchPanel.add(searchButton);
        searchField.setStyleName("operation_fields");
        vPanel.add(searchPanel);
    }
    
    
    
    
    
    
    private void createTable() {

        // добавляем таблицу с информацией о вкладах
        FlexTable depositTable = new FlexTable();
        depositTable.addStyleName("simple-little-table");
        // заголовок таблицы
        depositTable.setText(0, 0, "Наименование");
        depositTable.setText(0, 1, "Срок вклада, дни");
        depositTable.setText(0, 2, "Ставка");
        depositTable.setText(0, 3, " ");
        // форматируем заголовок
        for (int m = 0; m < 4; m++) {
            depositTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        int i = 1; // индекс строки в таблице

        // выбираем только карточные счета
        for (DepositDTO deposit : depositList) {

            // наименование депозита
            depositTable.setText(i, 0, deposit.getName());
            depositTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
            // срок
            depositTable.setText(i, 1, String.valueOf(deposit.getDuration()));
            depositTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
            // ставка
            depositTable.setText(i, 2, String.valueOf(deposit.getInterestRate()));
            depositTable.getCellFormatter().addStyleName(i, 2, "simple_cell");
            // кнопка открытия
            Button openButton = new Button("Открыть");
            openButton.addClickHandler(new DepositClickHandler(deposit));
            depositTable.setWidget(i, 3, openButton);
            depositTable.getCellFormatter().addStyleName(i, 3, "simple_cell");

            i++;

        }

        vPanel.add(depositTable);

    }
    
    
    private void createCancelButton() {
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        
        Button cancelBtn = new Button("Отмена");
        cancelBtn.setStyleName("confirm_button");

        // обрабатываем нажатие кнопки Отмена
        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
            }
        });


        // добавляем кнопку к панели кнопок
        buttonPanel.add(cancelBtn);
        buttonPanel.setSpacing(10);
        
        vPanel.add(buttonPanel);
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

            AsyncCallback<Void> openDepositCallback = new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
                }

                @Override
                public void onSuccess(Void result) {
                    Window.alert("Документ отправлен на сервер!");
                }
            };

            if (check()) {
                LoginService.Util.getInstance().openDeposit(deposit, openDepositCallback);
            }
        }

        private boolean check() {

            Double summ;
            
            // пытаемся получить сумму перевода
            try {
                summ = Double.parseDouble(sumField.getValue());
            } catch (NumberFormatException ex) {
                Window.alert("Сумма перевода указана неверно");
                sumField.setFocus(true);
                return false;
            }

            // проверяем наличие суммы на счете
            for (AccountDTO account : user.getAccountList()) {
                if (account.getId() == Integer.parseInt(locAccount.getSelectedValue())) {
                    // проверяем остаток на счете
                    if (account.getBalance() < summ) {
                        locAccount.setFocus(true);
                        return false;
                    }
                }
            }

            return true;
        }

    }

}
