package es.viewerfree.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;

import es.viewerfree.gwt.client.common.BarPanel;
import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.client.viewer.ViewerPanel;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class Viewer extends BaseEntryPoint {
	  
	private LayoutPanel contentPanel;
	
	private BarPanel barPanel;
	
	private HorizontalPanel adminPanel;
	
	private Label adminLabel;
	
	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private static final Constants constants = GWT.create(Constants.class);
	
	private static final UserServiceAsync userService = GWT.create(UserService.class);
	
	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private ViewerPanel viewerPanel;

	private Timer sessionTimer;
	
	@Override
	protected void initValues() {
		userService.getUser(new AsyncCallback<UserDto>() {
			@Override
			public void onSuccess(UserDto user) {
				getBarPanel().getUserName().setText(getFullName(user));
				if(user.getProfile().equals(UserProfile.ADMIN)){
					getBarPanel().insert(getAdminPanel(),0);
					getBarPanel().setCellHorizontalAlignment(getAdminPanel(), HorizontalPanel.ALIGN_LEFT);
				}
			}
			
			@Override
			public void onFailure(Throwable throwable) {
				showErrorDialogBox();					
			}

		});
		
		viewerService.getAlbums(new AsyncCallback<String[]>() {
			
			@Override
			public void onSuccess(String[] albums) {
				getViewerPanel().setAlbumsList(albums);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				showErrorDialogBox();
			}
		});
		
	}
	
	private void showErrorDialogBox() {
		ErrorMessageUtil.getErrorDialogBox(messages.serverError());
	}

	@Override
	protected Panel getContentPanel() {
		if(this.contentPanel==null){
			this.contentPanel = new LayoutPanel();
			this.contentPanel.add(getBarPanel());
			this.contentPanel.setWidgetTopHeight(getBarPanel(), 0, Unit.PX, 25, Unit.PX);
			this.contentPanel.add(getViewerPanel());
			this.contentPanel.setWidgetTopBottom(getViewerPanel(), 25, Unit.PX, 0, Unit.PX);
		}
		return this.contentPanel ;
	}

	private BarPanel getBarPanel(){
		if(this.barPanel==null){
			this.barPanel = new BarPanel();
		}
		return this.barPanel;
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
			this.adminLabel = new HTML(messages.adminLabel());
			this.adminLabel.setStyleName("barLink");
			this.adminLabel.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					Window.open(GWT.getHostPageBaseURL()+constants.adminPath()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName(),"_blank","");
				}
			});
		}
		return this.adminLabel;
	}


	private ViewerPanel getViewerPanel(){
		if(this.viewerPanel==null){
			this.viewerPanel = new ViewerPanel();
		}
		return this.viewerPanel;
	}

	private String getFullName(UserDto user) {
		StringBuffer fullName = new StringBuffer();
		if(user.getFullName()!=null && !user.getFullName().isEmpty()){
			fullName.append(user.getFullName());
		}
		if(user.getSurname()!=null && !user.getSurname().isEmpty()){
			fullName.append(" ").append(user.getSurname());
		}
		return fullName.toString().isEmpty()?user.getName():fullName.toString();
	}
	
}
