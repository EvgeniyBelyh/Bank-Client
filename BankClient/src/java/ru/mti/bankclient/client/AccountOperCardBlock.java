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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
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
public class AccountOperCardBlock implements IsWidget {

    private VerticalPanel verticalPanel = new VerticalPanel();
    private ListBox locAccount = new ListBox(); // список счетов списания
    private Button confirmBtn = new Button("Исполнить");
    private Button cancelBtn = new Button("Отмена");
    private MainPage mainPage;
    private ClientDTO user;

    public AccountOperCardBlock(MainPage mainPage) {

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
                    // выбираем только карты
                    if (acc.getAccountTypeId() != AccountTypes.DEPOSIT.getId()) {
                        locAccount.addItem(acc.getAccountTypeName() + " "
                                + acc.getNumber() + ", остаток " + acc.getBalance()
                                + " " + acc.getCurrencyName(), acc.getId().toString());
                    }
                }

                createHeader();
                createBody();

                locAccount.setStyleName("operation_fields");
            }
        };

        LoginService.Util.getInstance().loginFromSessionServer(userCallback);

    }

    /**
     * Создает заголовок страницы
     */
    public void createHeader() {

        HTML header = new HTML("<h2>Блокировка карты</h2><br>");
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

        headers.add(new HTML("<h3>Карта</h3>"));
        fields.add(locAccount);
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

        // определяем id счетов списания и зачисления
        int locAccountValue = Integer.parseInt(locAccount.getValue(locAccount.getSelectedIndex()));

        // создаем объект операции
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setAccountId(locAccountValue);
        operationDTO.setAmount(0);
        operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
        operationDTO.setDescription("Блокировка карты");
        operationDTO.setDestinationAccount("0");
        operationDTO.setOperationTypeId(OperTypes.CARD_BLOCK.getId());
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
                Window.alert("Карта блокирована успешно");
                mainPage.centerBodyPanel.clear();
                mainPage.createCenterPanel();                
            }
        };

        LoginService.Util.getInstance().saveOperation(operationDTO, operationCallback);

    }

    @Override
    public Widget asWidget() {
        return verticalPanel;
    }

}
