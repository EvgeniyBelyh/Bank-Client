package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.OperTypes;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBankDTO;
import ru.mti.bankclient.shared.ProviderCategories;
import ru.mti.bankclient.shared.ServiceProviderDTO;
import ru.mti.bankclient.shared.Statuses;

/**
 * Создает панель оплаты Интернета
 *
 * @author Евгений Белых
 */
public class ServicePayUtilitiesPanel implements IsWidget {

    private VerticalPanel verticalPanel = new VerticalPanel();
    private List<ServiceProviderDTO> serviceProviderList = new ArrayList();
    private MainPage mainPage;
    private ClientDTO user;
    private ListBox locAccount = new ListBox();
    private ListBox serviceProviderListBox = new ListBox();
    private TextBox sumField = new TextBox();
    private TextBox agreementId = new TextBox();
    private TextBox providerInn = new TextBox();
    private Button confirmBtn = new Button("Оплатить");
    private Button cancelBtn = new Button("Отмена");
    private TextBox phoneNumber = new TextBox();
    private ServiceProviderDTO serviceProviderDTO;
    private AsyncCallback<ServiceProviderDTO> serviceProviderCallback;
    private HTML providerLabel =  new HTML();
    private HTML headerLabel =  new HTML();
    
    public ServicePayUtilitiesPanel(MainPage mainPage) {

        this.mainPage = mainPage;
        this.user = Util.getClientDTO();

        HTML header = new HTML("<h2>Оплата услуг - ЖКХ</h2><br>");
        header.setStyleName("operations_container h2");
        verticalPanel.add(header);

        HorizontalPanel hPanel = new HorizontalPanel();
        HorizontalPanel buttonPanel = new HorizontalPanel();
        VerticalPanel headers = new VerticalPanel();
        VerticalPanel fields = new VerticalPanel();
        fields.setSpacing(10);
        headers.setStyleName("operations_container");

        headers.add(new HTML("<h3>Счет списания</h3>"));
        fields.add(locAccount);
        headers.add(new HTML("<h3>Сумма</h3>"));
        fields.add(sumField);
        headers.add(new HTML("<h3>ИНН поставщика</h3>"));
        fields.add(providerInn);
        headers.add(headerLabel);
        fields.add(providerLabel);
        headers.add(new HTML("<h3>Номер лицевого счета</h3>"));
        fields.add(agreementId);
        headers.add(new HTML("<br>"));
        
        // создаем обработчик выборки оператора по ИНН
        serviceProviderCallback = new AsyncCallback<ServiceProviderDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                headerLabel.setHTML("<font color=\"#FFFFFF\">Поставщик</font>");
                providerLabel.setHTML("Поставщик не выбран. Проверьте ИНН");
            }

            @Override
            public void onSuccess(ServiceProviderDTO result) {

                serviceProviderDTO = result;
                headerLabel.setHTML("<font color=\"#FFFFFF\">Поставщик</font>");                
                providerLabel.setHTML("Поставщик: " + serviceProviderDTO.getName());
            }
        };

        providerInn.addBlurHandler(new BlurHandler() {

            @Override
            public void onBlur(BlurEvent event) {
                // отправляем запрос на сервер
                LoginService.Util.getInstance().getServiceProviderByInn(providerInn.getText(), serviceProviderCallback);
            }
        });
        

        locAccount.setStyleName("operation_fields");
        serviceProviderListBox.setStyleName("operation_fields");
        sumField.setStyleName("operation_fields");
        agreementId.setStyleName("operation_fields");
        providerInn.setStyleName("operation_fields");

        // создаем кнопки
        createButtons();

        // добавляем кнопки и стиль к панели кнопок
        buttonPanel.setStyleName("button_panel");
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        fields.add(buttonPanel);

        hPanel.add(headers);
        hPanel.add(fields);

        verticalPanel.add(hPanel);

        // заполняем список счетов списания денег
        for (AccountDTO account : user.getAccountList()) {
            if (account.getAccountTypeId() == AccountTypes.DEBIT_CARD.getId()) {
                locAccount.addItem(account.getAccountTypeName() + " "
                        + account.getNumber() + ", остаток " + account.getBalance()
                        + " " + account.getCurrencyName(), account.getId().toString());
            }
        }

    }

    /**
     * определяет обработчики и стили кнопок
     */
    private void createButtons() {

        cancelBtn.setStyleName("confirm_button");
        confirmBtn.setStyleName("confirm_button");

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

    }

    /**
     * обработчик нажатия клавиши Оплатить
     */
    private void confirmButtonHandler() {

        Double summ;
        // определяем id счетов списания и зачисления
        int locAccountValue = Integer.parseInt(locAccount.getValue(locAccount.getSelectedIndex()));

        // пытаемся получить сумму перевода
        try {
            summ = Double.parseDouble(sumField.getValue());
        } catch (NumberFormatException ex) {
            Window.alert("Сумма платежа указана неверно");
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

        // проверяем остаток на счете
        if (account.getBalance() < summ) {
            Window.alert("Недостаточно средств для оплаты");
            locAccount.setFocus(true);
            return;
        }
        
        // проверяем ИНН поставщика
        if(providerInn.getText().length() < 10 || providerInn.getText().length() > 10) {
            Window.alert("Неверно указан ИНН поставщика услуг");
            providerInn.setFocus(true);
            return;
        }
        
        // проверяем номер лицевого счета
        if(agreementId.getText().length() < 2) {
            Window.alert("Неверно указан номер лицевого счета");
            agreementId.setFocus(true);
            return;
        }
        

        // создаем объект операции
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setAccountId(locAccountValue);
        operationDTO.setAmount(summ);
        operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
        operationDTO.setDescription("Оплата услуг ЖКХ. Номер лицевого счета " + agreementId.getText());
        operationDTO.setDestinationAccount(serviceProviderDTO.getAccountNumber());
        operationDTO.setOperationTypeId(OperTypes.SERVICE_PAY.getId());
        operationDTO.setStatusId(Statuses.NEW.getId());
        operationDTO.setPartnerBankId(new PartnerBankDTO(serviceProviderDTO.getPartnerBankId()));

        AsyncCallback<Void> operationCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером. Повторите попытку позднее");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Документ отправлен на обработку");
            }
        };

        LoginService.Util.getInstance().saveOperation(operationDTO, operationCallback);

    }

    @Override
    public Widget asWidget() {
        return verticalPanel;
    }

}
