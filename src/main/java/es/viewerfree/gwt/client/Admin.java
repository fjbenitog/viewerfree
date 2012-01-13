package es.viewerfree.gwt.client;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;

import es.viewerfree.gwt.client.common.BaseEntryPoint;

public class Admin extends BaseEntryPoint {
	  
	private LayoutPanel contentPanel;
	
	@Override
	protected void initValues() {

	}

	@Override
	protected Panel getContentPanel() {
		if(this.contentPanel==null){
			this.contentPanel = new LayoutPanel();
		}
		return this.contentPanel ;
	}

	
	
}
