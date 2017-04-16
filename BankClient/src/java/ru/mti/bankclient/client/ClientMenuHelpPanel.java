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
public class ClientMenuHelpPanel implements IsWidget {

    private VerticalPanel vPanel;

    
    public ClientMenuHelpPanel() {

        vPanel = new VerticalPanel();
        createPanel();
    }

    private void createPanel() {
        // создаем заголовок 
        HTML helpHeader = new HTML("<h2>Помощь</h2><br>");
        helpHeader.setStyleName("operations_container h2");
        vPanel.add(helpHeader);

    }

    @Override
    public Widget asWidget() {
        return vPanel;
    }
}
