
package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Панель ввода логина и пароля
 * @author Евгений Белых
 */
public class LoginPanel implements IsWidget {
    
    private TextBox login;
    private PasswordTextBox pass;
    public Button confirmButton = new Button("Вход");
    FlexTable table = new FlexTable();
    AsyncCallback<ClientDTO> clientCallback;
    
    LoginPanel(AsyncCallback<ClientDTO> clientCallback) {
        
        this.clientCallback = clientCallback;
        
        login = new TextBox();
        login.setStyleName("login_textbox"); //ставим стиль оформления
        login.setTitle("Логин"); //ставим всплывающую подсказку
        login.getElement().setAttribute("placeholder", "Логин"); //ставим надпись внутри поля
        //поле для ввода пароля
        pass = new PasswordTextBox();
        pass.setStyleName("login_textbox");
        pass.setTitle("Пароль");
        pass.getElement().setAttribute("placeholder", "Пароль");
        //кнопка входа
       
        confirmButton.setStyleName("login_confirm_button");
        
        //обработчик для клика по кнопке 'Вход'
        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sendInfoToServer();
            }
        });
        
        table.setWidget(0, 0, login);
        table.setWidget(1, 0, pass);
        table.setWidget(2, 0, confirmButton);
        table.setSize("515px", "220px");
        table.setStyleName("login_form_container");
    }

    
     /**
     * Проверяет правильность ввода логина и пароля и отправляет данные на
     * сервер
     */
    private void sendInfoToServer() {

        final String symbol = login.getText().toLowerCase().trim();
        login.setFocus(true);

        // проверяем ввод определенных символов в поле логина
        if (!symbol.matches("^[0-9a-z.@]{1,50}$")) {
            Window.alert("Логин не может быть пустым и может состоять из "
                    + "латинских символов нижнего регистра, цифр, а также символов '.' или '@'");
            login.setFocus(true);
            login.selectAll();
            return;
        }
        // проверяем длину пароля
        if (pass.getText().length() < 8) {
            Window.alert("Пароль не может быть менее 8 символов");
            pass.setFocus(true);
            pass.selectAll();
            return;
        }
        
        LoginService.Util.getInstance().loginServer(login.getText(), pass.getText(), this.clientCallback);

    }
    
    
    @Override
    public Widget asWidget() {
        return table;
    }
}
