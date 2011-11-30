package es.viewerfree.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

import es.viewerfree.gwt.client.common.FooterPanel;
import es.viewerfree.gwt.client.common.HeaderPanel;
import es.viewerfree.gwt.client.main.Content;

public class Main implements EntryPoint {

	private DockPanel mainPanel;
	private Panel headerPanel;
	private Panel footerPanel;
	private Panel contentPanel;

	public void onModuleLoad() {
		RootPanel.get().add(getMainPanel());
	}

	private DockPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new DockPanel();
			this.mainPanel.setWidth("100%");
			this.mainPanel.add(getHeaderPanel(), DockPanel.NORTH);
			this.mainPanel.add(getFooterPanel(), DockPanel.SOUTH);
			this.mainPanel.add(getContentPanel(), DockPanel.CENTER);
			this.mainPanel.setCellHorizontalAlignment(getContentPanel(), HorizontalPanel.ALIGN_CENTER);
			this.mainPanel.setCellVerticalAlignment(getContentPanel(), HorizontalPanel.ALIGN_MIDDLE);
		}
		return this.mainPanel;
	}

	private Panel getHeaderPanel(){
		if(this.headerPanel==null){
			this.headerPanel = new HeaderPanel();
		}
		return this.headerPanel;
	}

	private Panel getFooterPanel(){
		if(this.footerPanel==null){
			this.footerPanel = new FooterPanel();
		}
		return this.footerPanel;
	}

	private Panel getContentPanel(){
		if(this.contentPanel==null){
			this.contentPanel = new Content();
		}
		return this.contentPanel;
	}

}
