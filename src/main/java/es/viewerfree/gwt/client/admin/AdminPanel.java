package es.viewerfree.gwt.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import es.viewerfree.gwt.client.ViewerFreeMessages;

public class AdminPanel extends LayoutPanel {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private LayoutPanel adminTitlePanel;
	
	private Label adminTitleLabel;
	
	private TabLayoutPanel adminTabs;
	
	private CreateUserForm createUserForm;
	
	private FlowPanel userActionPanel;
	
	private Button createUserButton;

	public AdminPanel() {
		setStyleName("adminContent");
		add(getAdminTitlePanel());
		setWidgetTopHeight(getAdminTitlePanel(), 0, Unit.PX, 70, Unit.PX);
		add(getAdminTabs());
		setWidgetTopBottom(getAdminTabs(), 70, Unit.PX, 50, Unit.PX);
		setWidgetLeftRight(getAdminTabs(), 50, Unit.PX, 50, Unit.PX);
	}
	
	private LayoutPanel getAdminTitlePanel(){
		if(this.adminTitlePanel == null){
			this.adminTitlePanel = new LayoutPanel();
			this.adminTitlePanel.add(getAdminTitleLabel());
			this.adminTitlePanel.setWidgetLeftRight(getAdminTitleLabel(), 50, Unit.PX, 100, Unit.PX);
			this.adminTitlePanel.setWidgetTopBottom(getAdminTitleLabel(), 2, Unit.PX, 15, Unit.PX);
			this.adminTitlePanel.setStyleName("titlePanel");
		}
		return this.adminTitlePanel;
	}
	
	private Label getAdminTitleLabel(){
		if(this.adminTitleLabel == null){
			this.adminTitleLabel = new HTML(messages.adminLabel());
			this.adminTitleLabel.setStyleName("titleLabel");
		}
		return this.adminTitleLabel;
	}

	private TabLayoutPanel getAdminTabs(){
		if(this.adminTabs == null){
			this.adminTabs = new TabLayoutPanel(30, Unit.PX);
			this.adminTabs.add(getUserActionPanel(), messages.users(), true);
			this.adminTabs.add(new HTML("CREAR GRUPOS"), messages.groups(), true);
		}		return this.adminTabs;
	}
	
	
	private CreateUserForm getCreateUserForm(){
		if(this.createUserForm == null){
			this.createUserForm = new CreateUserForm();
			this.createUserForm.setAnimationEnabled(true);
			this.createUserForm.setGlassEnabled(true);
		}
		return this.createUserForm;
	}

	
	private FlowPanel getUserActionPanel(){
		if(this.userActionPanel == null){
			this.userActionPanel = new FlowPanel();
			this.userActionPanel.add(getCreateUserButton());
		}
		return this.userActionPanel;
	}
	
	private Button getCreateUserButton(){
		if(this.createUserButton == null){
			this.createUserButton = new Button(messages.createUser());
			this.createUserButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent clickevent) {
					getCreateUserForm().show();
					getCreateUserForm().center();
					
				}
			});
		}
		return this.createUserButton;
	}
	
}
