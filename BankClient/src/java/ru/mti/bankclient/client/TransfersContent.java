
package ru.mti.bankclient.client;

import ru.mti.bankclient.shared.Account;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import ru.mti.bankclient.client.rpc.ClientAccounts;
import ru.mti.bankclient.client.rpc.ClientAccountsAsync;


/**
 * Класс формирует форму ввода данных для перевода
 * @author Белых Евгений
 */
public class TransfersContent extends VerticalPanel {
    
    private ClientAccountsAsync clientAccountsService = GWT.create(ClientAccounts.class);
    final AsyncCallback<List<Account>> callback;
    private ListBox locAccount = new ListBox(); // список счетов списания
    private ListBox destAccount = new ListBox(); // список счетов зачисления
    
    
    public TransfersContent() {
        
        this.callback = new AsyncCallback<List<Account>>() {
            // при успешной отработке удаленного вызова
            public void onSuccess(List<Account> result) {
                for(Account acc : result) {
                    locAccount.addItem(acc.getNumber(), acc.getId().toString());
                    destAccount.addItem(acc.getNumber(), acc.getId().toString());
                }
                
            }
            // в случае возникновения ошибки
            public void onFailure(Throwable caught) {
                Window.alert("Не могу выбрать счета для выпадающих списков");
                caught.printStackTrace();
            }         
        };  
        
        // отправляем запрос на сервер
        clientAccountsService.getAccountList(1, callback);
        createHeader();
        createBody();
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
        
        TextBox sumField = new TextBox(); // сумма
        Button confirmBtn = new Button("Перевести");
        
        this.add(locAccount);
        this.add(destAccount);
        this.add(sumField);
        this.add(confirmBtn);
    }
}
