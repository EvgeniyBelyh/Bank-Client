package ru.mti.bankclient.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.shared.impl.cldr.DateTimeFormatInfoImpl_ru;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.AccountTypes;
import ru.mti.bankclient.shared.BankMessageDTO;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Отображает панель с сообщениями от банка
 *
 * @author Евгений Белых
 */
public class ClientMenuBankMessagePanel implements IsWidget {

    private VerticalPanel vPanel;
    private ClientDTO user;

    
    public ClientMenuBankMessagePanel() {

        vPanel = new VerticalPanel();
        
        AsyncCallback<ClientDTO> userCallback = new AsyncCallback<ClientDTO>() {
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка выбора списка сообщений. Повторите попытку позднее");
            }

            @Override
            public void onSuccess(ClientDTO result) {

                user = result;

                createPanel();
            }
        };

        LoginService.Util.getInstance().loginFromSessionServer(userCallback);
              
    }

    private void createPanel() {
        // создаем заголовок 
        HTML cardHeader = new HTML("<h2>Сообщения банка</h2>");
        cardHeader.setStyleName("operations_container h2");
        vPanel.add(cardHeader);

        List<BankMessageDTO> bankMessageList = getBankMessageList();

        // добавляем таблицу с информацие об операциях
        FlexTable cardsTable = new FlexTable();
        cardsTable.addStyleName("simple-little-table");
        // заголовок таблицы
        cardsTable.setText(0, 0, "Дата");
        cardsTable.setText(0, 1, "Номер");
        cardsTable.setText(0, 2, "Текст");

        // форматируем заголовок
        for (int m = 0; m < 3; m++) {
            cardsTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        int i = 1; // индекс строки в таблице
        
        // создаем объект формата даты
        DateTimeFormatInfoImpl_ru df = new DateTimeFormatInfoImpl_ru();
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(df.dateTimeLong("HH:mm:ss", "dd.MM.yyyy"));
        
        // выбираем только карточные счета
        for (BankMessageDTO bankMessageDTO : bankMessageList) {

            // дата сообщения
            cardsTable.setText(i, 0, dateFormat.format(bankMessageDTO.getMessageDate()));
            cardsTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
            // номер сообщения
            cardsTable.setText(i, 1, String.valueOf(bankMessageDTO.getId()));
            cardsTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
            // текст сообщения
            cardsTable.setText(i, 2, String.valueOf(bankMessageDTO.getText()));
            cardsTable.getCellFormatter().addStyleName(i, 2, "simple_cell");

            i++;
        }

        vPanel.add(cardsTable);
    }

    /**
     * Создает список сообщений и сортирует в обратном порядке
     *
     * @return список сообщений банка
     */
    private List<BankMessageDTO> getBankMessageList() {

        List<BankMessageDTO> bankMessageList = user.getBankMessageList();

        // сортируем список сообщений по дате получения в обратном порядке
        Collections.sort(bankMessageList, new Comparator<BankMessageDTO>() {
            @Override
            public int compare(BankMessageDTO o1, BankMessageDTO o2) {
                return -(o1.getMessageDate().compareTo(o2.getMessageDate()));
            }
        });

        return bankMessageList;
    }

    @Override
    public Widget asWidget() {
        return vPanel;
    }
}
