package es.viewerfree.gwt.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class Content extends LayoutPanel{
	
	private LoginForm loginForm;
	
	private HTML info;
	
	private LayoutPanel rightPanel;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	public Content() {
		setStyleName("loginPanel");
		add(getInfo());
		setWidgetLeftWidth(getInfo(), 0, Unit.PCT, 50, Unit.PCT);
		add(getRightPanel());
		setWidgetRightWidth(getRightPanel(), 0, Unit.PX, 48, Unit.PCT);
	}

	public LayoutPanel getRightPanel(){
		if(this.rightPanel==null){
			this.rightPanel = new LayoutPanel();
			this.rightPanel.add(getLoginForm());
			this.rightPanel.setWidgetLeftWidth(getLoginForm(), 0, Unit.PCT, 500, Unit.PX);
			this.rightPanel.setWidgetTopHeight(getLoginForm(), 90, Unit.PX, 100, Unit.PCT);
			Style style = getLoginForm().getElement().getStyle();
			style.clearLeft();
			style.clearTop();
		}
		return this.rightPanel;
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
			this.info.setStyleName("left");
		}
		return this.info;
	}
	
	public void focus(){
		getLoginForm().focus();
	}
}
