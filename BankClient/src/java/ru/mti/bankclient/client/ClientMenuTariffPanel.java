package ru.mti.bankclient.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Отображает панель с тарифами банка
 *
 * @author Евгений Белых
 */
public class ClientMenuTariffPanel implements IsWidget {

    private VerticalPanel vPanel;

    
    public ClientMenuTariffPanel() {

        vPanel = new VerticalPanel();
        createPanel();
    }

    private void createPanel() {
        // создаем заголовок 
        HTML cardHeader = new HTML("<h2>Тарифы</h2><br>");
        cardHeader.setStyleName("operations_container h2");
        vPanel.add(cardHeader);
        
        // ссылка на тарифы банка
        Hyperlink help = new Hyperlink();
        help.setText("Тарифы от 17.04.2017.xlsx");
        help.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("Ссылка на файл с тарифами банка");
            }
        }); 
        
        VerticalPanel linkPanel = new VerticalPanel();
        linkPanel.setStyleName("operations_container");
        linkPanel.add(help);
        
        vPanel.add(linkPanel);

    }

    @Override
    public Widget asWidget() {
        return vPanel;
    }
}
