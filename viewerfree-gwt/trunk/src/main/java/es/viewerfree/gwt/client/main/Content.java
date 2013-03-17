package es.viewerfree.gwt.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class Content extends LayoutPanel{
	
	private LoginForm loginForm;
	
	private HTML info;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	public Content() {
		setStyleName("loginPanel");
		add(getInfo());
		setWidgetTopBottom(getInfo(), 20, Unit.PX, 10, Unit.PCT);
		setWidgetLeftRight(getInfo(), 15, Unit.PCT, 550, Unit.PX);
		add(getLoginForm());
		setWidgetTopBottom(getLoginForm(), 40, Unit.PX, 10, Unit.PCT);
		setWidgetRightWidth(getLoginForm(), 0, Unit.PX, 500, Unit.PX);
	}
	
	public LoginForm getLoginForm(){
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
			htmlText.append("<div class='info'>").append(messages.demoMessage()).append("</div>");
			this.info = new HTML(htmlText.toString());
		}
		return this.info;
	}
	
	public void focus(){
		getLoginForm().focus();
	}
}
