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
public class ServicePayInternetPanel implements IsWidget {

    private VerticalPanel verticalPanel = new VerticalPanel();
    private List<ServiceProviderDTO> serviceProviderList = new ArrayList();
    private MainPage mainPage;
    private ClientDTO user;
    private ListBox locAccount = new ListBox();
    private ListBox serviceProviderListBox = new ListBox();
    private TextBox sumField = new TextBox();
    private Button confirmBtn = new Button("Оплатить");
    private Button cancelBtn = new Button("Отмена");
    private TextBox phoneNumber = new TextBox();

    public ServicePayInternetPanel(MainPage mainPage) {

        this.mainPage = mainPage;
        this.user = Util.getClientDTO();

        HTML header = new HTML("<h2>Оплата услуг - Интернет</h2><br>");
        header.setStyleName("operations_container h2");
        verticalPanel.add(header);
        
        
        HorizontalPanel hPanel = new HorizontalPanel();
        HorizontalPanel buttonPanel = new HorizontalPanel();
        VerticalPanel headers = new VerticalPanel();
        VerticalPanel fields = new VerticalPanel();
        fields.setSpacing(10);
        headers.setStyleName("operations_container");
        
        // создаем обработчик выборки операторов сотовой связи
        AsyncCallback<List<ServiceProviderDTO>> serviceProviderCallback = new AsyncCallback<List<ServiceProviderDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
            }

            @Override
            public void onSuccess(List<ServiceProviderDTO> result) {

                for (ServiceProviderDTO serviceProviderDTO : result) {
                    serviceProviderListBox.addItem(serviceProviderDTO.getName(), serviceProviderDTO.getId().toString());
                }
                serviceProviderList = result;
            }
        };
        // отправляем запрос на сервер
        LoginService.Util.getInstance().getServiceProviderByCategory(ProviderCategories.INTERNET.getId(), serviceProviderCallback);

        headers.add(new HTML("<h3>Оператор</h3>"));
        fields.add(serviceProviderListBox);
        headers.add(new HTML("<h3>Счет списания</h3>"));
        fields.add(locAccount);
        headers.add(new HTML("<h3>Сумма</h3>"));
        fields.add(sumField);
        headers.add(new HTML("<br>"));

        locAccount.setStyleName("operation_fields");
        serviceProviderListBox.setStyleName("operation_fields");
        sumField.setStyleName("operation_fields");
        
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
            Window.alert("Сумма перевода указана неверно");
            sumField.setFocus(true);
            return;
        }

        // выбираем объект счета списания        
        AccountDTO account = null;
        
        for(AccountDTO acc : user.getAccountList()) {
            if(acc.getId() == locAccountValue) {
                account = acc;
            }
        }
        
        // проверяем остаток на счете
        if (account.getBalance() < summ) {
            Window.alert("Недостаточно средств для перевода");
            locAccount.setFocus(true);
            return;
        }

        // выбираем объект оператора сотовой связи
        ServiceProviderDTO serviceProviderDTO = null;
        
        for(ServiceProviderDTO provider : serviceProviderList) {
            if(provider.getId() == Integer.parseInt(serviceProviderListBox.getSelectedValue())) {
                serviceProviderDTO = provider;
            }
        }
        
        
        // создаем объект операции
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setAccountId(locAccountValue);
        operationDTO.setAmount(summ);
        operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
        operationDTO.setDescription("Оплата услуг Интернет");
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
