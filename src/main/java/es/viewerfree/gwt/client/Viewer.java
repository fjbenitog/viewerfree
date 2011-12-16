package es.viewerfree.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.common.ErrorDialogBox;
import es.viewerfree.gwt.client.common.Logout;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class Viewer extends BaseEntryPoint {
	  
	private VerticalPanel contentPanel;
	
	private HorizontalPanel barPanel;
	
	private HorizontalPanel labelsPanel;
	
	private HorizontalPanel adminPanel;
	
	private Label logoutLink;
	
	private Label userName;
	
	private Label adminLabel;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	@Override
	protected void initValues() {
		userService.getUser(new AsyncCallback<UserDto>() {
			@Override
			public void onSuccess(UserDto user) {
				getUserName().setText(user.getFullName()+" "+user.getSurname());
				if(user.getProfile().equals(UserProfile.ADMIN)){
					getBarPanel().insert(getAdminPanel(),0);
					getBarPanel().setCellHorizontalAlignment(getAdminPanel(), HorizontalPanel.ALIGN_LEFT);
				}
			}
			
			@Override
			public void onFailure(Throwable throwable) {
				ErrorDialogBox errorDialogBox = ErrorMessageUtil.getErrorDialogBox(messages.serverError());
				errorDialogBox.center();
				errorDialogBox.show();
				errorDialogBox.focus();					
			}
		});
	}

	@Override
	protected Panel getContentPanel() {
		if(this.contentPanel==null){
			this.contentPanel = new VerticalPanel();
			this.contentPanel.setWidth("100%");
			this.contentPanel.add(getBarPanel());
			this.contentPanel.setCellHorizontalAlignment(getBarPanel(), VerticalPanel.ALIGN_RIGHT);
		}
		return this.contentPanel ;
	}

	private HorizontalPanel getBarPanel(){
		if(this.barPanel==null){
			this.barPanel = new HorizontalPanel();
			this.barPanel.setStyleName("barPanel");
			this.barPanel.add(getLabelsPanel());
			this.barPanel.setCellHorizontalAlignment(getLabelsPanel(), HorizontalPanel.ALIGN_RIGHT);

		}
		return this.barPanel;
	}
	
	private HorizontalPanel getLabelsPanel(){
		if(this.labelsPanel==null){
			this.labelsPanel = new HorizontalPanel();
			this.labelsPanel.add(getUserName());
			this.labelsPanel.add(getLogoutLink());
		}
		return this.labelsPanel;
	}
	
	private HorizontalPanel getAdminPanel(){
		if(this.adminPanel==null){
			this.adminPanel = new HorizontalPanel();
			this.adminPanel.add(getAdminLabel());
		}
		return this.adminPanel;
	}
	
	private Label getAdminLabel(){
		if(this.adminLabel==null){
			this.adminLabel = new Label(messages.adminLabel());
			this.adminLabel.setStyleName("barLink");
		}
		return this.adminLabel;
	}

	private Label getLogoutLink(){
		if(this.logoutLink==null){
			this.logoutLink = new Logout(messages.logout());
		}
		return this.logoutLink;
	}
	
	private Label getUserName(){
		if(this.userName==null){
			this.userName = new Label();
			this.userName.setStyleName("barLabel");
		}
		return this.userName;
	}
	
}
