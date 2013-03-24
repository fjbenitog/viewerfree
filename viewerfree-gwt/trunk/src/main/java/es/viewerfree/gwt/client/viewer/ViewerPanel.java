package es.viewerfree.gwt.client.viewer;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewerPanel extends SplitLayoutPanel {
	
	private FoldersListPanel foldersListPanel;
	
	private FavouriteListPanel favouriteListPanel;
	
	private FolderPanel floderPanel;
	
	private FavouriteFolderPanel favouriteFolderPanel;
	
	private TagPanel tagPanel;
	
	private ImagesPanel imagesPanel;

	private LayoutPanel rightPanel;
	
	private ScrollPanel scrollAlbumPanel;
	
	private VerticalPanel leftPanel;
	
	public ViewerPanel() {
		super(7);
		addWest(getScrollImagesPanel(), 200);
		add(getRightPanel());
	}

	private ScrollPanel getScrollImagesPanel(){
		if(this.scrollAlbumPanel == null){
			this.scrollAlbumPanel = new ScrollPanel();
			this.scrollAlbumPanel.add(getLeftPanel());
		}
		return this.scrollAlbumPanel;
	}
	
	private VerticalPanel getLeftPanel(){
		if(this.leftPanel == null){
			this.leftPanel = new VerticalPanel();
			this.leftPanel.add(getFoldersListPanel());
			this.leftPanel.add(getFavouriteListPanel());
		}
		return this.leftPanel;
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
			CloseOpenHandler closeOpenHandler = new CloseOpenHandler(getFolderPanel());
			this.foldersListPanel.getAlbumsListPanel().addOpenHandler(closeOpenHandler);
			this.foldersListPanel.getAlbumsListPanel().addCloseHandler(closeOpenHandler);
		}
		return this.foldersListPanel;
	}
	
	private FavouriteListPanel getFavouriteListPanel(){
		if(this.favouriteListPanel == null){
			this.favouriteListPanel = new FavouriteListPanel();
			CloseOpenHandler closeOpenHandler = new CloseOpenHandler(getTagPanel());
			this.favouriteListPanel.getAlbumsListPanel().addOpenHandler(closeOpenHandler);
			this.favouriteListPanel.getAlbumsListPanel().addCloseHandler(closeOpenHandler);
		}
		return this.favouriteListPanel;
	}
	
	
	private FolderPanel getFolderPanel(){
		if(this.floderPanel == null){
			this.floderPanel = new FolderPanel();
		}
		return this.floderPanel;
	}
	
	private FavouriteFolderPanel getFavouriteFolderPanel(){
		if(this.favouriteFolderPanel == null){
			this.favouriteFolderPanel = new FavouriteFolderPanel();
		}
		return this.favouriteFolderPanel;
	}
	
	private TagPanel getTagPanel(){
		if(this.tagPanel == null){
			this.tagPanel = new TagPanel();
		}
		return this.tagPanel;
	}
	
	private ImagesPanel getImagesPanel(){
		if(this.imagesPanel == null){
			this.imagesPanel = new ImagesPanel();
		}
		return this.imagesPanel;
	}
	
	public void setAlbumsList(List<String>  albums){
		AlbumClickHandler albumClickHandler = new AlbumClickHandler();
		for (String album : albums) {
			getFolderPanel().addFolder(album,albumClickHandler);
			getFoldersListPanel().addFolder(album,albumClickHandler);
		}
	}
	
	public void setTagsList(List<String>  tags){
		UpdateFoldersRightPanel updateFoldersRightPanel = new UpdateFoldersRightPanel();
		for (String tag : tags) {
			getTagPanel().addTag(tag,updateFoldersRightPanel);
			getFavouriteListPanel().addTag(tag,new AlbumClickHandler(), updateFoldersRightPanel);
		}
	}
	
	private class AlbumClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent clickevent) {
			if(clickevent.getSource() instanceof HTML){
				getRightPanel().clear();
				getImagesPanel().clear();
				HTML albumLink = (HTML) clickevent.getSource();
				String albumName = albumLink.getText();
				getFoldersListPanel().markFolder(albumName);
				getImagesPanel().init(albumName);
				getRightPanel().add(getImagesPanel());
			}
			
		}
		
	}
	
	public class UpdateFoldersRightPanel{
		
		public void update(List<String> albums){
			getFavouriteFolderPanel().clear();
			getRightPanel().clear();
			getRightPanel().add(getFavouriteFolderPanel());
			for (String album : albums) {
				getFavouriteFolderPanel().addFolder(album,new AlbumClickHandler());
			}
		}
	}
	
	private class CloseOpenHandler implements OpenHandler<DisclosurePanel>,CloseHandler<DisclosurePanel>{

		private Widget widget;
		
		public CloseOpenHandler(Widget widget) {
			super();
			this.widget = widget;
		}

		@Override
		public void onClose(CloseEvent<DisclosurePanel> e) {
			refreshRightPanel();
		}

		@Override
		public void onOpen(OpenEvent<DisclosurePanel> e) {
			refreshRightPanel();
		}
		
		
		private void refreshRightPanel(){
			getRightPanel().clear();
			getRightPanel().add(widget);
		}
		
	}
	
}
