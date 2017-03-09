
package ru.mti.bankclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import ru.mti.bankclient.client.UserCheck.UserCheck;
import ru.mti.bankclient.client.UserCheck.UserCheckAsync;




/**
 * Точка входа в приложение
 *
 * @author Белых Евгений
 */
public class Login implements EntryPoint {
    
    TextBox login;
    PasswordTextBox pass;
    UserCheckAsync userCheckService = GWT.create(UserCheck.class);
    
    /**
     * Конструктор по умолчанию
     */
    public Login() {
    }

    /**
     * Аналог метода main
     */
    public void onModuleLoad() {
        
        //текстовое поле для ввода логина
        login = new TextBox();
        login.setStyleName("login_column"); //ставим стиль оформления
        login.setTitle("Логин"); //ставим всплывающую подсказку
        login.getElement().setAttribute("placeholder", "Логин"); //ставим надпись внутри поля
        //поле для ввода пароля
        pass = new PasswordTextBox();
        pass.setStyleName("login_column");
        pass.setTitle("Пароль");
        pass.getElement().setAttribute("placeholder", "Пароль");
        //кнопка входа
        Button confirmButton = new Button("Вход");
        confirmButton.setStyleName("login_confirm_button");
        
        //вертикальная панель для компановки элементов
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(login);
        vPanel.add(pass);
        vPanel.add(confirmButton);
        vPanel.setSize("515px", "220px");       
        vPanel.setStyleName("login_form_container");
        
        //обработчик для клика по кнопке 'Вход'
        confirmButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //confirmButton.setEnabled(false);
                sendInfoToServer();
            }
        });
        //добавляем элементы на страницу
        RootPanel.get("login_frame").add(vPanel);
    }
    
    
    /**
     * Проверяет правильность ввода логина и пароля и отправляет данные на сервер
     */
    private void sendInfoToServer() {
        
        final String symbol = login.getText().toLowerCase().trim();
        login.setFocus(true);

        // проверяем ввод определенных символов в поле логина
        if (!symbol.matches("^[0-9a-z.@]{1,50}$")) {
            Window.alert("Логин не может быть пустым и может состоять из " +
                    "латинских символов нижнего регистра, цифр, а также символов '.' или '@'");
            login.setFocus(true);
            login.selectAll();
            return;
        }
        
        if (pass.getText().length() < 8) {
            Window.alert("Пароль не может быть менее 8 символов");
            pass.setFocus(true);
            pass.selectAll();
            return;
        }
        
        // обрабатываем полученный от сервера результат
        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                Window.alert(result);
            }
            
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером");
            }
        };
        // отправляем логин и пароль на сервер
        userCheckService.checkUser(login.getText(), pass.getText(), callback);
        
        
        
        
    }
}
