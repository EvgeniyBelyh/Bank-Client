package ru.mti.bankclient.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.shared.impl.cldr.DateTimeFormatInfoImpl_ru;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.OperationDTO;

/**
 * Отображает панель с таблицей операций клиента
 *
 * @author 1
 */
public class OperationsPanel implements IsWidget {

    private VerticalPanel vPanel;

    
    public OperationsPanel() {

        vPanel = new VerticalPanel();
        createPanel();
    }

    private void createPanel() {
        // создаем заголовок 
        HTML cardHeader = new HTML("<h2>История операций</h2>");
        cardHeader.setStyleName("operations_container h2");
        vPanel.add(cardHeader);

        List<OperationDTO> operationsList = getOperationList();

        // добавляем таблицу с информацие об операциях
        FlexTable cardsTable = new FlexTable();
        cardsTable.addStyleName("simple-little-table");
        // заголовок таблицы
        cardsTable.setText(0, 0, "Дата");
        cardsTable.setText(0, 1, "Тип");
        cardsTable.setText(0, 2, "Сумма");
        cardsTable.setText(0, 3, "Статус");
        cardsTable.setText(0, 4, "Описание");
        // форматируем заголовок
        for (int m = 0; m < 5; m++) {
            cardsTable.getCellFormatter().addStyleName(0, m, "table_header");
        }

        int i = 1; // индекс строки в таблице
        
        // создаем объект формата даты
        DateTimeFormatInfoImpl_ru df = new DateTimeFormatInfoImpl_ru();
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(df.dateTimeLong("HH:mm:ss", "dd.MM.yyyy"));
        
        // выбираем только карточные счета
        for (OperationDTO oper : operationsList) {

            // дата создания
            cardsTable.setText(i, 0, dateFormat.format(oper.getCreateDate()));
            cardsTable.getCellFormatter().addStyleName(i, 0, "simple_cell");
            // тип операции
            cardsTable.setText(i, 1, oper.getOperationTypeName());
            cardsTable.getCellFormatter().addStyleName(i, 1, "simple_cell");
            // сумма
            cardsTable.setText(i, 2, String.valueOf(oper.getAmount()));
            cardsTable.getCellFormatter().addStyleName(i, 2, "simple_cell");
            // статус
            cardsTable.setText(i, 3, oper.getStatusName());
            cardsTable.getCellFormatter().addStyleName(i, 3, "simple_cell");
            // комментарий
            cardsTable.setText(i, 4, oper.getComment());
            cardsTable.getCellFormatter().addStyleName(i, 4, "simple_cell");
            i++;
        }

        vPanel.add(cardsTable);
    }

    /**
     * Создает список операций по всем счетам клиента
     *
     * @return список операций
     */
    private List<OperationDTO> getOperationList() {

        ClientDTO user = Util.getClientDTO();
        ArrayList<OperationDTO> operList = new ArrayList();

        for (AccountDTO acc : user.getAccountList()) {
            for (OperationDTO oper : acc.getOperationList()) {
                operList.add(oper);
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

    @Override
    public Widget asWidget() {
        return vPanel;
    }
}
