package es.viewerfree.gwt.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

import es.viewerfree.gwt.client.common.BaseEntryPoint;

public class Viewer extends BaseEntryPoint {
	  


	@Override
	protected Panel getContentPanel() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(new Label("Estoy dentro!!!!!"));
		return horizontalPanel ;
	}




	
}
