package ru.mti.bankclient.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Блок курсов валют
 *
 * @author Белых Евгений
 */
public class CurrencyMenuBlock extends TransferMenuBlock {

    public CurrencyMenuBlock() {

        super("Курсы валют");

    }

    /**
     * создает тело блока меню
     */
    @Override
    protected void createBody() {
        
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        
        FlexTable currencyTable = new FlexTable();
        
        currencyTable.setStyleName("currency_table_text");
        
        currencyTable.setText(0, 1, "Покупка");
        currencyTable.setText(0, 2, "Продажа");
        
        currencyTable.setText(1, 0, "USD/RUR");
        currencyTable.setText(1, 1, "56.60");
        currencyTable.setText(1, 2, "57.60");

        currencyTable.setText(2, 0, "EUR/RUR");
        currencyTable.setText(2, 1, "60.28");
        currencyTable.setText(2, 2, "61.53");
        

        
        FlexCellFormatter formatter = currencyTable.getFlexCellFormatter();
        formatter.setHorizontalAlignment(0, 1, ALIGN_RIGHT);
        formatter.setHorizontalAlignment(0, 2, ALIGN_RIGHT);
        formatter.setHorizontalAlignment(1, 1, ALIGN_RIGHT);
        formatter.setHorizontalAlignment(1, 2, ALIGN_RIGHT);
        formatter.setHorizontalAlignment(2, 1, ALIGN_RIGHT);
        formatter.setHorizontalAlignment(2, 2, ALIGN_RIGHT); 

        formatter.setWidth(0, 0, "80px");
        formatter.setWidth(1, 0, "80px");
        formatter.setWidth(2, 0, "80px");

        formatter.setWidth(0, 2, "70px");
        formatter.setWidth(1, 2, "70px");
        formatter.setWidth(2, 2, "70px");
        
        body.add(currencyTable);
        
        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        this.add(body);
    }

}
