package es.viewerfree.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;

import es.viewerfree.gwt.client.admin.AdminPanel;
import es.viewerfree.gwt.client.common.BarPanel;
import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

public class Admin extends BaseEntryPoint {
	  
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	private LayoutPanel contentPanel;
	
	private BarPanel barPanel;
	
	private HorizontalPanel appPanel;
	
	private Label appLabel;
	
	private AdminPanel adminPanel;
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	@Override
	protected void initValues() {
		userService.getUser(new AsyncCallback<UserDto>() {
			@Override
			public void onSuccess(UserDto user) {
				getBarPanel().getUserName().setText(user.getFullName()+" "+user.getSurname());
			}
			
			@Override
			public void onFailure(Throwable throwable) {
				ErrorMessageUtil.getErrorDialogBox(messages.serverError());					
			}

		});
	}

	@Override
	protected Panel getContentPanel() {
		if(this.contentPanel==null){
			this.contentPanel = new LayoutPanel();
			this.contentPanel.add(getBarPanel());
			this.contentPanel.setWidgetTopHeight(getBarPanel(), 0, Unit.PX, 25, Unit.PX);
			this.contentPanel.add(getAdminPanel());
			this.contentPanel.setWidgetTopBottom(getAdminPanel(), 25, Unit.PX, 0, Unit.PX);
		}
		return this.contentPanel ;
	}

	private BarPanel getBarPanel() {
		if(this.barPanel == null){
			this.barPanel = new BarPanel();
			this.barPanel.insert(getAppPanel(),0);
			this.barPanel.setCellHorizontalAlignment(getAppPanel(), HorizontalPanel.ALIGN_LEFT);
		}
		return this.barPanel;
	}

	private HorizontalPanel getAppPanel(){
		if(this.appPanel==null){
			this.appPanel = new HorizontalPanel();
			this.appPanel.add(getAppLabel());
		}
		return this.appPanel;
	}
	
	private Label getAppLabel(){
		if(this.appLabel==null){
			this.appLabel = new HTML(constants.viewerfree());
			this.appLabel.setStyleName("barLink");
			this.appLabel.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					Window.open(GWT.getHostPageBaseURL()+constants.viewerAppPath()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName(),"_blank","");
				}
			});
		}
		return this.appLabel;
	}

	
	private AdminPanel getAdminPanel(){
		if(this.adminPanel==null){
			this.adminPanel = new AdminPanel();
		}
		return this.adminPanel;
	}
	
	
}
