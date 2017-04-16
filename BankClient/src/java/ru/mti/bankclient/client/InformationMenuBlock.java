package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Блок информации
 *
 * @author Белых Евгений
 */
public class InformationMenuBlock extends TransferMenuBlock {

    public InformationMenuBlock() {

        super("Информация");

    }

    /**
     * создает тело блока меню
     */
    @Override
    protected void createBody() {
        
        // панель тела блока меню
        VerticalPanel body = new VerticalPanel();
        
        HTML html = new HTML();
        html.setStyleName("currency_table_text");
        html.setText("Уважаемые пользователи! Мы рады сообщить Вам, что у нашего Интернет-банка "
                + "теперь новый интерфейс. Он более удобный и функциональный");
        


        
        body.add(html);
        
        // ставим стиль оформления
        body.setStyleName("menu_item");
        
        this.add(body);
    }

}
