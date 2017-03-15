package ru.mti.bankclient.client;


import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Шаблон для всех страниц проекта
 * @author Белых Евгений
 */
public class TemplatePage implements IsWidget
{
        //основной контейнер для всех элементов
        protected DockLayoutPanel dockPanel = new DockLayoutPanel(Style.Unit.EM);
    
        protected HorizontalPanel headerPanel; // контейнер для хедера
        protected HorizontalPanel footerPanel; // контейнер для футера
        
        protected CustomScrollPanel scrollPanel = new CustomScrollPanel();
        protected VerticalPanel leftBodyPanel = new VerticalPanel(); // контейнер для левой панели с меню
        protected VerticalPanel centerBodyPanel = new VerticalPanel(); // контейнер для центра
        protected VerticalPanel rightBodyPanel = new VerticalPanel(); // контейнер для правой панели
     
	// конструктор
	public TemplatePage() {
            
            createHeaderPanel(); // формируем хедер
            createFooterPanel(); // формируем футер
            
            // ставим промежутки между блоками
            leftBodyPanel.setSpacing(10);
            rightBodyPanel.setSpacing(10);
            
            scrollPanel.add(leftBodyPanel);
            
            // расставляем готовые контейнеры по местам
            dockPanel.addNorth(headerPanel, 8);
            dockPanel.addSouth(footerPanel, 8);           
            dockPanel.addWest(scrollPanel, 25);
            dockPanel.addEast(rightBodyPanel, 25);            
            dockPanel.add(centerBodyPanel);
            
            
            
            
	}
	
        /**
         * формирует хедер
         */
	private void createHeaderPanel()
	{
            headerPanel = new HorizontalPanel();
            headerPanel.setStyleName("header_container");
            HorizontalPanel logoPanel = new HorizontalPanel();
            logoPanel.setStyleName("logo_container");
            headerPanel.add(logoPanel);
	}
        
        /**
         * формирует футер
         */
	private void createFooterPanel()           
	{
            footerPanel = new HorizontalPanel();
            footerPanel.setStyleName("login_footer");
            footerPanel.add(new HTML("<p class=\"footer_text telephone\">8 800 123 45 67</p>\n" +
                "<p class=\"footer_text\">Генеральная лицензия Банка России №1234 от 01.01.2000</p> "));
	}
	
        /**
         * переопределяем метод, возвращающий виджет
         * @return виджет
         */
	@Override
	public Widget asWidget()
	{
		return dockPanel;
	}

}
