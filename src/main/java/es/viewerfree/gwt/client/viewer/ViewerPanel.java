package es.viewerfree.gwt.client.viewer;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

public class ViewerPanel extends SplitLayoutPanel {
	
	private FoldersListPanel foldersListPanel;
	
	private FolderPanel floderPanel;
	
	private ImagesPanel imagesPanel;

	private LayoutPanel rightPanel;
	
	private ScrollPanel scrollAlbumPanel;
	
	public ViewerPanel() {
		super(7);
		addWest(getScrollImagesPanel(), 200);
		add(getRightPanel());
	}

	private ScrollPanel getScrollImagesPanel(){
		if(this.scrollAlbumPanel == null){
			this.scrollAlbumPanel = new ScrollPanel();
			this.scrollAlbumPanel.add(getFoldersListPanel());
		}
		return this.scrollAlbumPanel;
	}
	
	private LayoutPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new LayoutPanel();
			this.rightPanel.add(getFolderPanel());
		}
		return this.rightPanel;
	}
	
	private FoldersListPanel getFoldersListPanel(){
		if(this.foldersListPanel == null){
			this.foldersListPanel = new FoldersListPanel();
		}
		return this.foldersListPanel;
	}
	
	
	private FolderPanel getFolderPanel(){
		if(this.floderPanel == null){
			this.floderPanel = new FolderPanel();
		}
		return this.floderPanel;
	}
	
	private ImagesPanel getImagesPanel(){
		if(this.imagesPanel == null){
			this.imagesPanel = new ImagesPanel();
		}
		return this.imagesPanel;
	}
	
	public void setAlbumsList(List<String>  albums){
		AlbumClickHandler albumClickHandler = new AlbumClickHandler(this);
		for (String album : albums) {
			getFolderPanel().addFolder(album,albumClickHandler);
			getFoldersListPanel().addFolder(album,albumClickHandler);
		}
	}
	
	private static class AlbumClickHandler implements ClickHandler{

		private ViewerPanel viewerPanel;
		
		public AlbumClickHandler(ViewerPanel viewerPanel) {
			this.viewerPanel = viewerPanel;
		}

		@Override
		public void onClick(ClickEvent clickevent) {
			if(clickevent.getSource() instanceof HTML){
				viewerPanel.getRightPanel().clear();
				viewerPanel.getImagesPanel().clear();
				HTML albumLink = (HTML) clickevent.getSource();
				String albumName = albumLink.getText();
				viewerPanel.getFoldersListPanel().markFolder(albumName);
				viewerPanel.getImagesPanel().init(albumName);
				viewerPanel.getRightPanel().add(viewerPanel.getImagesPanel());
			}
			
		}
		
	}
}
