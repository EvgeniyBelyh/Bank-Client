package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import java.util.ArrayList;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Создает панель администратора
 *
 * @author Евгений Белых
 */
public class AdminPanel implements IsWidget {

    private VerticalPanel verticalPanel = new VerticalPanel();
    private List<ClientDTO> clientDTOList = new ArrayList();
    private ClientDTO selectedClientDTO;
    private TextBox newPasswordTextBox = new PasswordTextBox();
    private HTML newPasswordLabel;
    private HTML clientInfo = new HTML();
    private HTML clientInfoLabel;
    private Button blockBtn = new Button("Блокировка");
    private Button changePasswordBtn = new Button("Смена пароля");
    private Button cancelBtn = new Button("Отмена");
    private Button saveBtn = new Button("Сохранить");
    private SuggestBox suggestBox;

    public AdminPanel() {

        // создаем обработчик выборки операторов сотовой связи
        AsyncCallback<List<ClientDTO>> clientDTOCallback = new AsyncCallback<List<ClientDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
            }

            @Override
            public void onSuccess(List<ClientDTO> result) {

                clientDTOList = result;
                MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

                for (ClientDTO clientDTO : clientDTOList) {
                    oracle.add(clientDTO.getLogin() + " : " + clientDTO.getName());
                }

                suggestBox = new SuggestBox(oracle);
                suggestBox.setWidth("300px");

                suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {

                    @Override
                    public void onSelection(SelectionEvent<Suggestion> event) {
                        String login = event.getSelectedItem().getReplacementString();
                        login = (login.substring(0, login.indexOf(":"))).trim();

                        for (ClientDTO clientDTO : clientDTOList) {
                            if (login == clientDTO.getLogin()) {
                                selectedClientDTO = clientDTO;
                            }
                        }

                        updateClientInfo(selectedClientDTO);
                        clientInfoLabel.setVisible(true);
                        clientInfo.setVisible(true);
                    }
                });

                HTML header = new HTML("<h2>Панель администратора</h2><br>");
                header.setStyleName("operations_container h2");
                verticalPanel.add(header);

                HorizontalPanel hPanel = new HorizontalPanel();
                HorizontalPanel buttonPanel = new HorizontalPanel();
                VerticalPanel headers = new VerticalPanel();
                VerticalPanel fields = new VerticalPanel();
                fields.setSpacing(10);
                headers.setStyleName("operations_container");

                headers.add(new HTML("<h3>ФИО или логин клиента</h3>"));
                fields.add(suggestBox);
                clientInfoLabel = new HTML("<h3>Информация о клиенте</h3>");
                headers.add(clientInfoLabel);
                fields.add(clientInfo);
                newPasswordLabel = new HTML("<h3>Новый пароль</h3>");
                clientInfoLabel.setVisible(false);
                clientInfo.setVisible(false);
                newPasswordLabel.setVisible(false);
                newPasswordTextBox.setVisible(false);
                headers.add(newPasswordLabel);
                fields.add(newPasswordTextBox);

                headers.add(new HTML("<br>"));

                suggestBox.setStyleName("operation_fields");
                newPasswordTextBox.setStyleName("operation_fields");

                // создаем кнопки
                createButtons();

                saveBtn.setVisible(false);
                cancelBtn.setVisible(false);

                // добавляем кнопки и стиль к панели кнопок
                buttonPanel.setStyleName("button_panel");
                buttonPanel.add(blockBtn);
                buttonPanel.add(changePasswordBtn);
                buttonPanel.add(saveBtn);
                buttonPanel.add(cancelBtn);

                fields.add(buttonPanel);

                hPanel.add(headers);
                hPanel.add(fields);

                verticalPanel.add(hPanel);
            }
        };

        // отправляем запрос на сервер
        LoginService.Util.getInstance().getClients(clientDTOCallback);

    }

    /**
     * определяет обработчики и стили кнопок
     */
    private void createButtons() {

        changePasswordBtn.setStyleName("confirm_button");
        changePasswordBtn.setWidth("120px");
        blockBtn.setStyleName("confirm_button");
        cancelBtn.setStyleName("confirm_button");
        saveBtn.setStyleName("confirm_button");

        // обрабатываем нажатие кнопки Отмена
        changePasswordBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                newPasswordLabel.setVisible(true);
                newPasswordTextBox.setVisible(true);
                changePasswordBtn.setVisible(false);
                saveBtn.setVisible(true);
                cancelBtn.setVisible(true);
                blockBtn.setVisible(false);
                //mainPage.centerBodyPanel.clear();
                //mainPage.createCenterPanel();
            }
        });

        // обрабатываем нажатие кнопки Перевести
        blockBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectedClientDTO.setBlocked(!selectedClientDTO.isBlocked());
                confirmButtonHandler();                               
            }
        });

        saveBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(newPasswordTextBox.getText().length() < 8) {
                    Window.alert("Пароль не может быть менее 8 символов");
                    newPasswordTextBox.selectAll();
                } else {
                    selectedClientDTO.setPassword(newPasswordTextBox.getText());
                    confirmButtonHandler();
                }
            }
        });

        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                suggestBox.setText("");
                clientInfoLabel.setVisible(false);
                clientInfo.setVisible(false);
                newPasswordLabel.setVisible(false);
                newPasswordTextBox.setVisible(false);
                blockBtn.setVisible(true);
                changePasswordBtn.setVisible(true);
                saveBtn.setVisible(false);
                cancelBtn.setVisible(false);
            }
        });

    }

    /**
     * Обновляет информацию о клиенте
     */
    private void updateClientInfo(ClientDTO client) {
        String clientInfoString = "<b>" + client.getName() + "</b><br>";
        clientInfoString = clientInfoString + (client.isAdmin() ? "Администратор" : "Пользователь") + "<br>";
        clientInfoString = clientInfoString + (client.isBlocked() ? "Блокирован" : "Не блокирован") + "<br>";
        clientInfo.setHTML(clientInfoString);
    }

    /**
     * обработчик нажатия клавиши Оплатить
     */
    private void confirmButtonHandler() {

        AsyncCallback<Void> clientDTOCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером! Повторите попытку позднее");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Данные пользователя успешно изменены!");
                updateClientInfo(selectedClientDTO);
                saveBtn.setVisible(false);
                cancelBtn.setVisible(false);
                blockBtn.setVisible(true);
                changePasswordBtn.setVisible(true);
                newPasswordLabel.setVisible(false);
                newPasswordTextBox.setText("");
                newPasswordTextBox.setVisible(false);
            }
        };

        // отправляем запрос на сервер
        LoginService.Util.getInstance().updateClient(selectedClientDTO, clientDTOCallback);
              
    }

    @Override
    public Widget asWidget() {
        return verticalPanel;
    }

}
