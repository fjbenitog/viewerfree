package es.viewerfree.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

import es.viewerfree.gwt.client.common.FooterPanel;
import es.viewerfree.gwt.client.common.HeaderPanel;

public class Main implements EntryPoint {

	private DockPanel mainPanel;
	private Panel headerPanel;
	private Panel footerPanel;
	
	public void onModuleLoad() {
		RootPanel.get().add(getMainPanel());
	}
	
	private DockPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new DockPanel();
			this.mainPanel.setWidth("100%");
			this.mainPanel.add(getHeaderPanel(), DockPanel.NORTH);
			this.mainPanel.add(getFooterPanel(), DockPanel.SOUTH);
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
	
}
