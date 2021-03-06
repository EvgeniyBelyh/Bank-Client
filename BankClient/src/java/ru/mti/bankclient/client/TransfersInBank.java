package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.OperTypes;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBankDTO;
import ru.mti.bankclient.shared.Statuses;

/**
 * Класс формирует форму ввода данных для перевода
 *
 * @author Белых Евгений
 */
public class TransfersInBank implements IsWidget {

    private VerticalPanel verticalPanel = new VerticalPanel();
    private ListBox locAccount = new ListBox(); // список счетов списания
    private TextBox destAccount = new TextBox(); // счет зачисления другого клиента
    private TextBox sumField = new TextBox(); // сумма
    private Button confirmBtn = new Button("Перевести");
    private Button cancelBtn = new Button("Отмена");
    private List<AccountDTO> accountList;
    private MainPage mainPage;
    private ClientDTO user;

    public TransfersInBank(MainPage mainPage) {

        this.mainPage = mainPage;

        AsyncCallback<ClientDTO> userCallback = new AsyncCallback<ClientDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка формирования списка счетов. Повторите попытку позднее");
            }

            @Override
            public void onSuccess(ClientDTO result) {

                user = result;

                for (AccountDTO acc : user.getAccountList()) {

                    if (acc.getAccountTypeId() == AccountTypes.DEBIT_CARD.getId()) {
                        locAccount.addItem(acc.getAccountTypeName() + " "
                                + acc.getNumber() + ", остаток " + acc.getBalance()
                                + " " + acc.getCurrencyName(), acc.getId().toString());
                    }
                }

                accountList = user.getAccountList();

                createHeader();
                createBody();

                locAccount.setStyleName("operation_fields");
                destAccount.setStyleName("operation_fields");
                sumField.setStyleName("operation_fields");
            }
        };

        LoginService.Util.getInstance().loginFromSessionServer(userCallback);

    }

    /**
     * Создает заголовок страницы
     */
    public void createHeader() {

        HTML header = new HTML("<h2>Перевод другому клиенту банка</h2><br>");
        header.setStyleName("operations_container h2");
        verticalPanel.add(header);
    }

    /**
     * Создает тело формы ввода данных для перевода
     */
    public void createBody() {

        HorizontalPanel hPanel = new HorizontalPanel();
        HorizontalPanel buttonPanel = new HorizontalPanel();
        VerticalPanel headers = new VerticalPanel();
        VerticalPanel fields = new VerticalPanel();
        fields.setSpacing(10);

        headers.add(new HTML("<h3>Счет списания</h3>"));
        fields.add(locAccount);
        headers.add(new HTML("<h3>Счет зачисления</h3>"));
        fields.add(destAccount);
        headers.add(new HTML("<h3>Сумма перевода</h3>"));
        fields.add(sumField);
        headers.add(new HTML("<br>"));
        headers.setStyleName("operations_container");

        confirmBtn.setStyleName("confirm_button");
        cancelBtn.setStyleName("confirm_button");

        // обрабатываем нажатие кнопки Отмена
        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
            }
        });

        // обрабатываем нажатие кнопки Перевести
        confirmBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                confirmButtonHandler();
            }
        });

        // добавляем кнопки и стиль к панели кнопок
        buttonPanel.setStyleName("button_panel");
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        fields.add(buttonPanel);

        hPanel.add(headers);
        hPanel.add(fields);

        verticalPanel.add(hPanel);

    }

    /**
     * обработчик нажатия клавиши Перевести
     */
    private void confirmButtonHandler() {

        Double summ;
        // определяем id счетов списания и зачисления
        int locAccountValue = Integer.parseInt(locAccount.getValue(locAccount.getSelectedIndex()));

        // пытаемся получить сумму перевода
        try {
            summ = Double.parseDouble(sumField.getValue());
        } catch (NumberFormatException ex) {
            Window.alert("Сумма перевода указана неверно");
            sumField.setFocus(true);
            return;
        }

        // выбираем объект счета списания        
        AccountDTO account = null;

        for (AccountDTO acc : user.getAccountList()) {
            if (acc.getId() == locAccountValue) {
                account = acc;
            }
        }

        // проверяем блокировку счета
        if (account.getBlocked()) {
            Window.alert("Счет списания блокирован. Операция невозможна");
            return;
        }
        
        // проверяем остаток на счете
        if (account.getBalance() < summ) {
            Window.alert("Недостаточно средств для перевода");
            locAccount.setFocus(true);
            return;
        }

        // создаем объект операции
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setAccountId(locAccountValue);
        operationDTO.setAmount(summ);
        operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
        operationDTO.setDescription("Перевод внутри банка");
        operationDTO.setDestinationAccount(this.destAccount.getText());
        operationDTO.setOperationTypeId(OperTypes.TRANSFER_IN.getId());
        operationDTO.setOperationTypeName("Внутренний перевод");
        operationDTO.setStatusId(Statuses.NEW.getId());
        operationDTO.setPartnerBankId(new PartnerBankDTO(MainPage.CURRENT_BANK));

        AsyncCallback<Void> operationCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером. Повторите попытку позднее");
                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Перевод успешно завершен!");
                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();
            }
        };

        LoginService.Util.getInstance().executeOperation(operationDTO, operationCallback);

    }

    @Override
    public Widget asWidget() {
        return verticalPanel;
    }

}
