
package ru.mti.bankclient.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import ru.mti.bankclient.client.rpc.BankClientService;
import ru.mti.bankclient.client.rpc.BankClientServiceAsync;
import ru.mti.bankclient.shared.AccountDTO;


/**
 * Класс формирует форму ввода данных для перевода
 * @author Белых Евгений
 */
public class TransfersContent extends VerticalPanel {
    
    private BankClientServiceAsync bankClientServiceAsync = GWT.create(BankClientService.class);
    final AsyncCallback<List<AccountDTO>> callback;
    private ListBox locAccount = new ListBox(); // список счетов списания
    private ListBox destAccount = new ListBox(); // список счетов зачисления
    private TextBox sumField = new TextBox(); // сумма
    private Button confirmBtn = new Button("Перевести");
    private Button cancelBtn = new Button("Отмена");
    
    public TransfersContent() {
        
        this.callback = new AsyncCallback<List<AccountDTO>>() {
            // при успешной отработке удаленного вызова
            public void onSuccess(List<AccountDTO> result) {
                for(AccountDTO acc : result) {
                    locAccount.addItem(acc.getAccountTypeName() + " " 
                            + acc.getNumber() + ", остаток " + acc.getBalance() 
                            + " " + acc.getCurrencyName(), acc.getId().toString());

                    destAccount.addItem(acc.getAccountTypeName() + " " 
                            + acc.getNumber() + ", остаток " + acc.getBalance() 
                            + " " + acc.getCurrencyName(), acc.getId().toString());
                }
                
            }
            // в случае возникновения ошибки
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Невозможно определить список счетов. Повторите попытку позднее");
                caught.printStackTrace();
            }         
        };  
        
        // отправляем запрос на сервер
        bankClientServiceAsync.getAccounts(MainPage.user.getId(), callback);
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
        buttonPanel.setStyleName("button_panel");
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        
        fields.add(buttonPanel);
        
        //headers.add(confirmBtn);
        
        hPanel.add(headers);
        hPanel.add(fields);
        
        this.add(hPanel);

    }
}
