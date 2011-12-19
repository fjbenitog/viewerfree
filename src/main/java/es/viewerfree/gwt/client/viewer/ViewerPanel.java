package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class ViewerPanel extends SplitLayoutPanel {
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);

	private VerticalPanel leftPanel;
	
	private LayoutPanel rightPanel;
	
	private DisclosurePanel albumsListPanel;
	
	public ViewerPanel() {
		super(5);
		addWest(getLeftPanel(), 200);
		add(getRightPanel());
	}

	private VerticalPanel getLeftPanel(){
		if(this.leftPanel == null){
			this.leftPanel = new VerticalPanel();
			this.leftPanel.setStyleName("albums");
			this.leftPanel.add(getAlbumsListPanel());
			this.leftPanel.setWidth("100%");
			this.leftPanel.setSpacing(10);
		}
		return this.leftPanel;
	}
	
	private DisclosurePanel getAlbumsListPanel(){
		if(this.albumsListPanel == null){
			this.albumsListPanel =  new DisclosurePanel(messages.albumsLabel());
			this.albumsListPanel.setAnimationEnabled(true);
			this.albumsListPanel.setOpen(true);
		}
		return this.albumsListPanel;
	}
	
	private LayoutPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new LayoutPanel();
			this.rightPanel.setStyleName("pictures");
		}
		return this.rightPanel;
	}
	
	public void setAlbumsList(String[] albums){
		FlowPanel list = new FlowPanel();
		for (String album : albums) {
			list.add(new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+"folder.png"+"\">"+album));
			
		}
		getAlbumsListPanel().setContent(list);
	}
	
}
