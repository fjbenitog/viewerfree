package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;

public class FoldersListPanel extends VerticalPanel {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	private DisclosurePanel albumsListPanel;
	
	private FlowPanel listPanel; 
	
	public FoldersListPanel() {
		setStyleName("albums");
		add(getAlbumsListPanel());
		setWidth("100%");
		setSpacing(10);
	}

	private DisclosurePanel getAlbumsListPanel(){
		if(this.albumsListPanel == null){
			this.albumsListPanel =  new DisclosurePanel(messages.albumsLabel());
			this.albumsListPanel.setAnimationEnabled(true);
			this.albumsListPanel.setOpen(true);
			this.albumsListPanel.setContent(getListPanel());
		}
		return this.albumsListPanel;
	}
	
	private FlowPanel getListPanel(){
		if(this.listPanel == null){
			this.listPanel = new FlowPanel();
		}
		return this.listPanel;
	}

	public void addFolder(String album,ClickHandler clickHandler){
			HTML albumLink = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+"folder.png"+"\">"+album);
			if(clickHandler!=null){
				albumLink.addClickHandler(clickHandler);
			}
			getListPanel().add(albumLink);
	}
	
	public void markFolder(String album){
		for (int i = 0; i < getListPanel().getWidgetCount(); i++) {
			HTML widget = (HTML)getListPanel().getWidget(i);
			widget.setStyleName("gwt-HTML");
			if(album.equals(widget.getText())){
				widget.setStyleName("selectedFolder");
			}
		}
	}
}
