package es.viewerfree.gwt.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class Content extends HorizontalPanel{
	
	private LoginForm loginForm;
	
	private HTML info;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	public Content() {
		setStyleName("loginPanel");
		setSpacing(70);
		add(getInfo());
		add(getLoginForm());
	}

	private LoginForm getLoginForm(){
		if(this.loginForm==null){
			this.loginForm = new LoginForm();
		}
		return this.loginForm;
	}
	
	private HTML getInfo(){
		if(this.info==null){
			StringBuffer htmlText = new StringBuffer();
			htmlText.append("<div class='title'>").append(constants.viewerfree()).append("</div>");
			htmlText.append("<div class='subtitle'>").append(messages.subtitle()).append("</div>");
			htmlText.append("<div class='content'>").append(messages.contentInfo()).append("</div>");
			htmlText.append("<ul>");
			htmlText.append("<li>").append(messages.list1()).append("</li>");
			htmlText.append("<li>").append(messages.list2()).append("</li>");
			htmlText.append("<li>").append(messages.list3()).append("</li>");
			htmlText.append("<li>").append(messages.list4()).append("</li>");
			htmlText.append("</ul>");
			this.info = new HTML(htmlText.toString());
		}
		return this.info;
	}
}
