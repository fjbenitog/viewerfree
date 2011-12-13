package es.viewerfree.gwt.client;

import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.main.Content;

public class Main extends BaseEntryPoint {

	private Content contentPanel;
	  
	public void onModuleLoad() {
		super.onModuleLoad();
		focus();
	}

	private void focus() {
		getContentPanel().getLoginForm().focus();
	}
	

	protected Content getContentPanel(){
		if(this.contentPanel==null){
			this.contentPanel = new Content();
		}
		return this.contentPanel;
	}

	
}
