package es.viewerfree.gwt.client.viewer;

import com.google.gwt.user.client.ui.SplitLayoutPanel;

import es.viewerfree.gwt.client.viewer.left.LeftPanel;
import es.viewerfree.gwt.client.viewer.right.RightPanel;

public class ViewerPanel extends SplitLayoutPanel{
	
	private LeftPanel leftPanel;
	
	private RightPanel rightPanel;
	
	public ViewerPanel() {
		super(7);
		addWest(getLeftPanel(), 200);
		add(getRightPanel());
	}
	
	private LeftPanel getLeftPanel(){
		if(this.leftPanel == null){
			this.leftPanel = new LeftPanel();
		}
		return this.leftPanel;
	}
	
	private RightPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new RightPanel();
			
		}
		return this.rightPanel;
	}

}
