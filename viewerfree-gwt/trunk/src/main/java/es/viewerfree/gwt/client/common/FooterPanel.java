package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class FooterPanel extends HorizontalPanel {

	private HorizontalPanel linksPanel;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	public FooterPanel() {
		super();
		this.addStyleName("footerPanel");
		this.add(getLinksPanel());
	}

	private HorizontalPanel getLinksPanel(){
		if(this.linksPanel==null){
			this.linksPanel = new HorizontalPanel();
			this.linksPanel.setSpacing(20);
			this.linksPanel.add(new HTML("&copy; <a href=\"http://sourceforge.net/projects/viewerfree/\">"+constants.softwareVersion()+"</a>."+
					" GNU General Public License (GPL). "+messages.designedBy()+
					"<a href=\"http://javi.viewerfree.es\">"+constants.owner()+"</a>"));
		}
		return this.linksPanel;
	}
	
	
}
