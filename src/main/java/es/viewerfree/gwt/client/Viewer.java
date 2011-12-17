package es.viewerfree.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.common.BaseEntryPoint;
import es.viewerfree.gwt.client.common.ErrorDialogBox;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.client.viewer.BarPanel;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class Viewer extends BaseEntryPoint {
	  
	private LayoutPanel contentPanel;
	
	private BarPanel barPanel;
	
	private HorizontalPanel adminPanel;
	
	private Label adminLabel;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private SplitLayoutPanel viewerPanel;
	
	@Override
	protected void initValues() {
		userService.getUser(new AsyncCallback<UserDto>() {
			@Override
			public void onSuccess(UserDto user) {
				getBarPanel().getUserName().setText(user.getFullName()+" "+user.getSurname());
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
		}
		return this.adminLabel;
	}


	private SplitLayoutPanel getViewerPanel(){
		if(this.viewerPanel==null){
			this.viewerPanel = new SplitLayoutPanel(5);
			HTML left = new HTML("navigation");
			left.setStyleName("albums");
			this.viewerPanel.addWest(left, 128);
//			this.viewerPanel.addNorth(new HTML("list"), 384);
			HTML right = new HTML("details");
			right.setStyleName("pictures");
			this.viewerPanel.add(right);
		}
		return this.viewerPanel;
	}
	
	
}
