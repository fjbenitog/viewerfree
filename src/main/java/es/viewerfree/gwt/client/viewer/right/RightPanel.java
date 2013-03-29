package es.viewerfree.gwt.client.viewer.right;

import com.google.gwt.user.client.ui.LayoutPanel;

import es.viewerfree.gwt.client.viewer.ClickItemObserver;
import es.viewerfree.gwt.client.viewer.Subject;

public class RightPanel extends LayoutPanel implements ClickItemObserver{
	
	private FolderPanel folderPanel;
	
	private TagPanel tagPanel;
	
	private ImagesPanel imagesPanel;
	
	private FolderPanelFromTags folderPanelFromTags;
	
	public RightPanel() {
		super();
		add(getFolderPanel());
		Subject.getInstance().addObserver(this);
	}

	private FolderPanel getFolderPanel(){
		if(this.folderPanel == null){
			this.folderPanel = new FolderPanel();
		}
		return this.folderPanel;
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
	
	private FolderPanelFromTags getFolderPanelFromTags(){
		if(this.folderPanelFromTags == null){
			this.folderPanelFromTags = new FolderPanelFromTags();
		}
		return this.folderPanelFromTags;
	}
	
	@Override
	public void update(Type type, String name) {
		clear();
		switch (type) {
		case TOTAL_ALBUM:{
			add(getFolderPanel());
			break;
		}
		case ALBUM:{
			getImagesPanel().clear();
			getImagesPanel().init(name);
			add(getImagesPanel());
			break;
		}
		case TOTAL_TAGS:{
			add(getTagPanel());
			break;
		}
		
		case TAG:{
			getFolderPanelFromTags().setAlbumsList(name);
			add(getFolderPanelFromTags());
			break;
		}

		default:
			break;
		}
		
	}

}
