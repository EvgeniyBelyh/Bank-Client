package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.impl.cldr.DateTimeFormatInfoImpl_ru;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.DepositDTO;
import ru.mti.bankclient.shared.OperTypes;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBankDTO;
import ru.mti.bankclient.shared.Statuses;
import ru.mti.bankclient.shared.TemplateDTO;

/**
 * Отображает панель шаблонов
 *
 * @author Евгений Белых
 */
public class TemplatesPanel implements IsWidget {

    private VerticalPanel vPanel = new VerticalPanel();
    private MainPage mainPage;
    private ClientDTO user;
    private TextBox templateNameTextBox = new TextBox();

    public TemplatesPanel(MainPage mainPage) {

        this.mainPage = mainPage;
        this.user = Util.getClientDTO();

        // создаем заголовок 
        HTML depositHeader = new HTML("<h2>Шаблоны операций</h2><br>");
        depositHeader.setStyleName("operations_container h2");
        vPanel.add(depositHeader);

        createTable();
        createCancelButton();

    }

    private void createTable() {

        // добавляем таблицу с информацией о вкладах
        FlexTable templateExecuteTable = new FlexTable();
        templateExecuteTable.addStyleName("simple-little-table");
        // заголовок таблицы
        templateExecuteTable.setText(0, 0, "Наименование");
        templateExecuteTable.setText(0, 1, "Тип операции");
        templateExecuteTable.setText(0, 2, "Сумма");
        templateExecuteTable.setText(0, 3, " ");
        // форматируем заголовок
        for (int m = 0; m < 4; m++) {
            templateExecuteTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        int i = 1; // индекс строки в таблице

        // форматирование числа
        NumberFormat nFormat = NumberFormat.getFormat("#0.00");

        for (AccountDTO acc : user.getAccountList()) {
            for (TemplateDTO templateDTO : acc.getTemplateList()) {

                // наименование шаблона
                templateExecuteTable.setText(i, 0, templateDTO.getName());
                templateExecuteTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
                // тип операции
                templateExecuteTable.setText(i, 1, String.valueOf(OperTypes.getNameById(templateDTO.getOperationTypeId()).getName()));
                templateExecuteTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
                // ставка
                templateExecuteTable.setText(i, 2, String.valueOf(nFormat.format(templateDTO.getAmount())));
                templateExecuteTable.getCellFormatter().addStyleName(i, 2, "simple_cell");
                // кнопка применения шаблона
                Button openButton = new Button("Применить");
                openButton.addClickHandler(new TemplateClickHandler(templateDTO));
                templateExecuteTable.setWidget(i, 3, openButton);
                templateExecuteTable.getCellFormatter().addStyleName(i, 3, "simple_cell");

                i++;
            }
        }

        vPanel.add(templateExecuteTable);

        HorizontalPanel templateNamePanel = new HorizontalPanel();
        VerticalPanel vPanel1 = new VerticalPanel();
        VerticalPanel vPanel2 = new VerticalPanel();
        vPanel2.setStyleName("operations_container");
        vPanel1.add(new HTML("<h3>Название шаблона</h3>"));
        templateNameTextBox.setStyleName("operation_fields");
        vPanel2.add(templateNameTextBox);

        List<OperationDTO> operationsList = getOperationList();

        // добавляем таблицу с информацие об операциях
        FlexTable templateCreateTable = new FlexTable();
        templateCreateTable.addStyleName("simple-little-table");
        // заголовок таблицы
        templateCreateTable.setText(0, 0, "Дата");
        templateCreateTable.setText(0, 1, "Тип");
        templateCreateTable.setText(0, 2, "Сумма");
        templateCreateTable.setText(0, 3, "Статус");
        templateCreateTable.setText(0, 4, " ");
        // форматируем заголовок
        for (int m = 0; m < 5; m++) {
            templateCreateTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        i = 1; // индекс строки в таблице

        // создаем объект формата даты
        DateTimeFormatInfoImpl_ru df = new DateTimeFormatInfoImpl_ru();
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(df.dateTimeLong("HH:mm:ss", "dd.MM.yyyy"));

        // выбираем только карточные счета
        for (OperationDTO oper : operationsList) {

            // дата создания
            templateCreateTable.setText(i, 0, dateFormat.format(oper.getCreateDate()));
            templateCreateTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
            // тип операции
            templateCreateTable.setText(i, 1, oper.getOperationTypeName());
            templateCreateTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
            // сумма
            templateCreateTable.setText(i, 2, String.valueOf(oper.getAmount()));
            templateCreateTable.getCellFormatter().addStyleName(i, 2, "simple_cell");
            // статус
            templateCreateTable.setText(i, 3, oper.getStatusName());
            templateCreateTable.getCellFormatter().addStyleName(i, 3, "simple_cell");
            // кнопка создания шаблона
            Button createButton = new Button("Применить");
            createButton.addClickHandler(new TemplateCreateClickHandler(oper));
            templateExecuteTable.setWidget(i, 4, createButton);
            templateExecuteTable.getCellFormatter().addStyleName(i, 4, "simple_cell");

            i++;
        }

        vPanel.add(templateCreateTable);

    }

    /**
     * Создает список операций по всем счетам клиента
     *
     * @return список операций
     */
    private List<OperationDTO> getOperationList() {

        ArrayList<OperationDTO> operList = new ArrayList();

        for (AccountDTO acc : user.getAccountList()) {
            for (OperationDTO oper : acc.getOperationList()) {
                // в список включаются только исполненные операции
                if (oper.getStatusId() == Statuses.EXECUTED.getId()) {
                    operList.add(oper);
                }
            }
        }

        // сортируем список операций по датам создания
        Collections.sort(operList, new Comparator<OperationDTO>() {
            @Override
            public int compare(OperationDTO o1, OperationDTO o2) {
                return -(o1.getCreateDate().compareTo(o2.getCreateDate()));
            }
        });

        return operList;
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

    class TemplateClickHandler implements ClickHandler {

        private TemplateDTO templateDTO;

        public TemplateClickHandler(TemplateDTO templateDTO) {
            this.templateDTO = templateDTO;
        }

        @Override
        public void onClick(ClickEvent event) {

            AsyncCallback<Void> saveOperationCallback = new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
                }

                @Override
                public void onSuccess(Void result) {
                    Window.alert("Документ отправлен на сервер!");
                }
            };

            OperationDTO operationDTO = new OperationDTO();
            operationDTO.setAccountId(templateDTO.getAccountId());
            operationDTO.setAmount(templateDTO.getAmount());
            operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
            operationDTO.setDescription(templateDTO.getDescription());
            operationDTO.setDestinationAccount(templateDTO.getDestinationAccount());
            operationDTO.setOperationTypeId(templateDTO.getOperationTypeId());
            operationDTO.setStatusId(Statuses.NEW.getId());
            operationDTO.setPartnerBankId(new PartnerBankDTO(templateDTO.getPartnerBankId()));

            if (check()) {
                LoginService.Util.getInstance().saveOperation(operationDTO, saveOperationCallback);
            }
        }

        private boolean check() {

            // проверяем наличие суммы на счете
            for (AccountDTO account : user.getAccountList()) {
                if (account.getId() == templateDTO.getAccountId()) {
                    // проверяем остаток на счете
                    if (account.getBalance() < templateDTO.getAmount()) {
                        return false;
                    }
                }
            }

            return true;
        }

    }

    class TemplateCreateClickHandler implements ClickHandler {

        private OperationDTO operationDTO;
        private String templateName;

        public TemplateCreateClickHandler(OperationDTO operationDTO) {
            this.operationDTO = operationDTO;
            this.templateName = templateNameTextBox.getText();
        }

        @Override
        public void onClick(ClickEvent event) {

            AsyncCallback<Void> saveTemplateCallback = new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
                }

                @Override
                public void onSuccess(Void result) {
                    Window.alert("Документ отправлен на сервер!");
                }
            };

            if (templateName != "") {

                TemplateDTO templateDTO = new TemplateDTO();
                templateDTO.setAccountId(operationDTO.getAccountId());
                templateDTO.setAmount(operationDTO.getAmount());
                templateDTO.setDescription(operationDTO.getDescription());
                templateDTO.setOperationTypeId(operationDTO.getOperationTypeId());
                templateDTO.setPartnerBankId(operationDTO.getPartnerBankId().getId());
                templateDTO.setName(templateName);

                LoginService.Util.getInstance().saveTemplate(templateDTO, saveTemplateCallback);
            } else {
                Window.alert("Название шаблона не может быть пустым!");
            }
        }

    }

}
