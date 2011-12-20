package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;

public class ViewerPanel extends SplitLayoutPanel {
	
	private FoldersListPanel foldersListPanel;
	
	private FolderPanel rightPanel;
	
	public ViewerPanel() {
		super(7);
		addWest(getFoldersListPanel(), 200);
		add(getRightPanel());
	}

	private FoldersListPanel getFoldersListPanel(){
		if(this.foldersListPanel == null){
			this.foldersListPanel = new FoldersListPanel();
		}
		return this.foldersListPanel;
	}
	
	
	private FolderPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new FolderPanel();
		}
		return this.rightPanel;
	}
	
	public void setAlbumsList(String[] albums){
		AlbumClickHandler albumClickHandler = new AlbumClickHandler();
		for (String album : albums) {
			getRightPanel().addFolder(album,albumClickHandler);
			getFoldersListPanel().addFolder(album,albumClickHandler);
		}
	}
	
	private static class AlbumClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent clickevent) {
			if(clickevent.getSource() instanceof HTML){
				HTML albumLink = (HTML) clickevent.getSource();
				ErrorMessageUtil.getErrorDialogBox(albumLink.getText());
			}
			
		}
		
	}
}
