package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class BaseEntryPoint implements EntryPoint {

	private LayoutPanel mainPanel;
	private Panel headerPanel;
	private Panel footerPanel;

	  
	public void onModuleLoad() {
		RootLayoutPanel.get().add(getMainPanel());
		initValues();
		 DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading"));
	}

	protected void initValues() {
		
	}

	private LayoutPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.add(getHeaderPanel());
			this.mainPanel.setWidgetTopHeight(getHeaderPanel(), 0, Unit.PX, 105, Unit.PX);
			this.mainPanel.add(getFooterPanel());
			this.mainPanel.setWidgetBottomHeight(getFooterPanel(), 0, Unit.PX, 55, Unit.PX);
			Panel contentPanel = getContentPanel();
			this.mainPanel.add(contentPanel);
			this.mainPanel.setWidgetLeftRight(contentPanel, 0, Unit.PX, 0, Unit.PX);
			this.mainPanel.setWidgetTopBottom(contentPanel, 104, Unit.PX, 55, Unit.PX);
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
