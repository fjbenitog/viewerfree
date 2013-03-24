package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import es.viewerfree.gwt.client.Constants;

public class FavouriteFolderPanel extends ScrollPanel {

	private final Constants constants = GWT.create(Constants.class);
	
	private FlowPanel folderPanel;
	
	public FavouriteFolderPanel() {
		setStyleName("pictures");
		add(getFolderPanel());
	}

	private FlowPanel getFolderPanel(){
		if(this.folderPanel == null){
			this.folderPanel = new FlowPanel();
		}
		return this.folderPanel;
	}

	public void addFolder(String album, ClickHandler clickHandler){
		HTML html = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageHugeFolder()+"\"><div class='label'>"+album+"</div>");
		html.addClickHandler(clickHandler);
		getFolderPanel().add(html);
	}

	@Override
	public void clear() {
		getFolderPanel().clear();
	}
	
	
}
