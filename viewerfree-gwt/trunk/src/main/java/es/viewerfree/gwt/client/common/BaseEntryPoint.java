package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class BaseEntryPoint implements EntryPoint {

	private DockPanel mainPanel;
	private Panel headerPanel;
	private Panel footerPanel;

	  
	public void onModuleLoad() {
		RootPanel.get().add(getMainPanel());
		initValues();
	}

	protected void initValues() {
		
	}

	private DockPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new DockPanel();
			this.mainPanel.setWidth("100%");
			this.mainPanel.add(getHeaderPanel(), DockPanel.NORTH);
			this.mainPanel.add(getFooterPanel(), DockPanel.SOUTH);
			Panel contentPanel = getContentPanel();
			this.mainPanel.add(contentPanel, DockPanel.CENTER);
			this.mainPanel.setCellHorizontalAlignment(contentPanel, HorizontalPanel.ALIGN_CENTER);
			this.mainPanel.setCellVerticalAlignment(contentPanel, HorizontalPanel.ALIGN_MIDDLE);
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

	protected abstract Panel getContentPanel();
	
}
