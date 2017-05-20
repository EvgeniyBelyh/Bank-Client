package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
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
    private MainPage mainPage;
    private Button confirmBtn = new Button("Оплатить");
    private Button cancelBtn = new Button("Отмена");
    private SuggestBox suggestBox;

    public AdminPanel(MainPage mainPage) {

        this.mainPage = mainPage;

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
                
                for(ClientDTO clientDTO : clientDTOList) {
                    oracle.add(clientDTO.getLogin() + " : " + clientDTO.getName());
                }
                
                suggestBox = new SuggestBox(oracle);

                HTML header = new HTML("<h2>Панель администратора</h2><br>");
                header.setStyleName("operations_container h2");
                verticalPanel.add(header);

                HorizontalPanel hPanel = new HorizontalPanel();
                HorizontalPanel buttonPanel = new HorizontalPanel();
                VerticalPanel headers = new VerticalPanel();
                VerticalPanel fields = new VerticalPanel();
                fields.setSpacing(10);
                headers.setStyleName("operations_container");

                headers.add(new HTML("<h3>ФИО клиента</h3>"));
                fields.add(suggestBox);
                headers.add(new HTML("<br>"));

                suggestBox.setStyleName("operation_fields");


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
            }
        };
        
        // отправляем запрос на сервер
        LoginService.Util.getInstance().getClients(clientDTOCallback);

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

        Window.alert(suggestBox.getText());

    }

    @Override
    public Widget asWidget() {
        return verticalPanel;
    }

}
