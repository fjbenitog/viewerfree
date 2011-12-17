package es.viewerfree.gwt.client.viewer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ViewerPanel extends SplitLayoutPanel {

	private VerticalPanel leftPanel;
	
	private LayoutPanel rightPanel;
	
	public ViewerPanel() {
		super(5);
		addWest(getLeftPanel(), 128);
		add(getRightPanel());
	}

	private VerticalPanel getLeftPanel(){
		if(this.leftPanel == null){
			this.leftPanel = new VerticalPanel();
			this.leftPanel.setStyleName("albums");
			FlowPanel list = new FlowPanel();
			list.add(new HTML("Album1"));
			list.add(new HTML("Album2"));
			list.add(new HTML("Album3"));
			list.add(new HTML("Album4"));
			list.add(new HTML("Album5"));
			DisclosurePanel disclosurePanel = new DisclosurePanel("Albumes");
			disclosurePanel.setContent(list);
			disclosurePanel.setAnimationEnabled(true);
			disclosurePanel.setOpen(true);
			this.leftPanel.add(disclosurePanel);
			this.leftPanel.setWidth("100%");
			this.leftPanel.setSpacing(10);
		}
		return this.leftPanel;
	}
	
	private LayoutPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new LayoutPanel();
			this.rightPanel.setStyleName("pictures");
		}
		return this.rightPanel;
	}
	
}
