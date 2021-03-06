package es.viewerfree.gwt.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.admin.ui.ActionPanel;
import es.viewerfree.gwt.shared.dto.UserDto;

public class AdminPanel extends LayoutPanel {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private LayoutPanel adminTitlePanel;

	private Label adminTitleLabel;

	private TabLayoutPanel adminTabs;

	private ActionPanel<UserDto> userActionPanel;

	private ActionPanel<String> albumActionPanel;

	private ConfigPanel configPanel;

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
			this.adminTabs.add(getAlbumActionPanel(), messages.albumsLabel(), true);
			this.adminTabs.add(getConfigPanel(), messages.configLabel(), true);
		}		return this.adminTabs;
	}



	private ActionPanel<UserDto> getUserActionPanel(){
		if(this.userActionPanel == null){
			this.userActionPanel = new UserActionPanel();
		}
		return this.userActionPanel;
	}


	private ActionPanel<String> getAlbumActionPanel(){
		if(this.albumActionPanel == null){
			this.albumActionPanel = new AlbumActionPanel();
		}
		return this.albumActionPanel;
	}
	
	private ConfigPanel getConfigPanel(){
		if(this.configPanel == null){
			this.configPanel = new ConfigPanel();
		}
		return this.configPanel;
	}

}
