package es.viewerfree.gwt.client.viewer.left;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.viewer.Subject;

public class LeftPanel extends ScrollPanel{

	private VerticalPanel leftPanel;
	
	private FoldersListPanel foldersListPanel;
	
	private FavouriteListPanel favouriteListPanel;
	
	public LeftPanel() {
		super();
		add(getLeftPanel());
	}

	private VerticalPanel getLeftPanel(){
		if(this.leftPanel == null){
			this.leftPanel = new VerticalPanel();
			this.leftPanel.add(getFoldersListPanel());
			this.leftPanel.add(getFavouriteListPanel());
		}
		return this.leftPanel;
	}
	

	private FoldersListPanel getFoldersListPanel(){
		if(this.foldersListPanel == null){
			this.foldersListPanel = new FoldersListPanel();
		}
		return this.foldersListPanel;
	}
	
	private FavouriteListPanel getFavouriteListPanel(){
		if(this.favouriteListPanel == null){
			this.favouriteListPanel = new FavouriteListPanel();
		}
		return this.favouriteListPanel;
	}
	
	
}
