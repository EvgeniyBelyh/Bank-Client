
package ru.mti.bankclient.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Date;
import java.util.List;
import ru.mti.bankclient.client.rpc.BankClientService;
import ru.mti.bankclient.client.rpc.BankClientServiceAsync;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBankDTO;


/**
 * Класс формирует форму ввода данных для перевода
 * @author Белых Евгений
 */
public class TransfersContent extends VerticalPanel {
    
    private BankClientServiceAsync bankClientServiceAsync = GWT.create(BankClientService.class);
    private final AsyncCallback<List<AccountDTO>> accountCallback;
    private ListBox locAccount = new ListBox(); // список счетов списания
    private ListBox destAccount = new ListBox(); // список счетов зачисления
    private TextBox sumField = new TextBox(); // сумма
    private Button confirmBtn = new Button("Перевести");
    private Button cancelBtn = new Button("Отмена");
    private List<AccountDTO> accountList;

    
    public TransfersContent() {
        
        this.accountCallback = new AsyncCallback<List<AccountDTO>>() {
            // при успешной отработке удаленного вызова
            public void onSuccess(List<AccountDTO> result) {
                for(AccountDTO acc : result) {
                    // пропускаем счета кредитных карт для списка счетов списания
                    if(acc.getAccountTypeId() == 1) {
                        destAccount.addItem(acc.getAccountTypeName() + " " 
                            + acc.getNumber() + ", остаток " + acc.getBalance() 
                            + " " + acc.getCurrencyName(), acc.getId().toString());
                        
                        continue;
                    }
                    
                    locAccount.addItem(acc.getAccountTypeName() + " " 
                            + acc.getNumber() + ", остаток " + acc.getBalance() 
                            + " " + acc.getCurrencyName(), acc.getId().toString());

                    destAccount.addItem(acc.getAccountTypeName() + " " 
                            + acc.getNumber() + ", остаток " + acc.getBalance() 
                            + " " + acc.getCurrencyName(), acc.getId().toString());
                }
                
                accountList = result;
                
            }
            // в случае возникновения ошибки
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Невозможно определить список счетов. Повторите попытку позднее");
                caught.printStackTrace();
            }         
        };  
        
        // отправляем запрос на сервер
        bankClientServiceAsync.getAccounts(MainPage.user.getId(), accountCallback);
        createHeader();
        createBody();
        
        locAccount.setStyleName("operation_fields");
        destAccount.setStyleName("operation_fields");
        sumField.setStyleName("operation_fields");
        //confirmBtn.setStyleName("confirm_button");
        //cancelBtn.setStyleName("confirm_button");
        this.setStyleName("operations_container");
    }
    
    /**
     * Создает заголовок страницы
     */
    public void createHeader() {
        
        HTML header = new HTML("<h2>Переводы между своими счетами</h2>");
        header.setStyleName("operations_container h2");
        this.add(header);
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
        
        confirmBtn.setStyleName("confirm_button");
        cancelBtn.setStyleName("confirm_button");
        
        // обрабатываем нажатие кнопки Отмена
        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RootLayoutPanel rootPanel = RootLayoutPanel.get();
                // убираем все виджеты
                rootPanel.clear();
                // открываем главную страницу
                rootPanel.add(new MainPage());
            }         
        });

        // обрабатываем нажатие кнопки Перевести
        confirmBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                confirmButtonHandler();
            }         
        });
        
        buttonPanel.setStyleName("button_panel");
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        
        fields.add(buttonPanel);
        
        //headers.add(confirmBtn);
        
        hPanel.add(headers);
        hPanel.add(fields);
        
        this.add(hPanel);

    }
    
    /**
     * обработчик нажатия клавиши Перевести
     */
    private void confirmButtonHandler() {
        
        Double summ;
        // определяем id счетов списания и зачисления
        int locAccountValue = Integer.parseInt(locAccount.getValue(locAccount.getSelectedIndex()));
        int destAccountValue = Integer.parseInt(destAccount.getValue(destAccount.getSelectedIndex()));
        
        // пытаемся получить сумму перевода
        try {
            summ = Double.parseDouble(sumField.getValue());
        } catch(NumberFormatException ex) {
            Window.alert("Сумма перевода указана неверно");
            sumField.setFocus(true);
            return;
        }
        // если счет списания равен счету зачисления, то выводим предупреждение
        if(locAccountValue == destAccountValue) {
            Window.alert("Неверно указан счет зачисления");
            destAccount.setFocus(true);
            return;
        }
        
        // создаем объект операции
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setAccountId(locAccountValue);
        operationDTO.setAmount(summ);
        operationDTO.setCreateDate(new Date(System.currentTimeMillis()));
        operationDTO.setDescription("Перевод между собственными счетами клиента " + MainPage.user.getName());
        operationDTO.setDestinationAccount(accountList.get(locAccountValue).getNumber());
        operationDTO.setOperationTypeId(1);
        operationDTO.setStatusId(1);
        operationDTO.setPartnerBankId(new PartnerBankDTO(1));             
        
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
        
        bankClientServiceAsync.saveOperation(operationDTO, operationCallback);
        
    }
    
}
