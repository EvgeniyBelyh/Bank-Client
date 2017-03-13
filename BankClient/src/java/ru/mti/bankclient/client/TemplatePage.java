package ru.mti.bankclient.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class TemplatePage implements IsWidget
{
       
        private DockLayoutPanel dockPanel = new DockLayoutPanel(Style.Unit.EM);
        
        protected HorizontalPanel headerPanel;
        protected HorizontalPanel footerPanel;
        
        protected VerticalPanel leftBodyPanel;
        protected VerticalPanel centerBodyPanel;
        protected VerticalPanel rightBodyPanel;
        
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public TemplatePage()
	{
            createHeaderPanel();
            createBodyPanel();
            createFooterPanel();
            
            dockPanel.addNorth(headerPanel, 2);
            dockPanel.addSouth(footerPanel, 2);
            dockPanel.addEast(leftBodyPanel, 10);
            dockPanel.addWest(rightBodyPanel, 10);
            dockPanel.add(centerBodyPanel);
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createHeaderPanel()
	{
            headerPanel = new HorizontalPanel();
            headerPanel.setStyleName("header_container");
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createBodyPanel()
	{           
            leftBodyPanel = new VerticalPanel();
            leftBodyPanel.add(new Label("Левая панель"));
            centerBodyPanel = new VerticalPanel();
            centerBodyPanel.add(new Label("Центральная панель"));
            rightBodyPanel = new VerticalPanel();
            rightBodyPanel.add(new Label("Правая панель"));
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	protected void createFooterPanel()
	{
            footerPanel = new HorizontalPanel();
            footerPanel.setStyleName("login_footer"); 
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@Override
	public Widget asWidget()
	{
		return dockPanel;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
