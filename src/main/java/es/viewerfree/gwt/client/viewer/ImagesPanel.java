package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ImagesPanel extends ScrollPanel {

	private FlowPanel imagesPanel;

	public ImagesPanel() {
		add(getImagesPanel());
	}

	private FlowPanel getImagesPanel(){
		if(this.imagesPanel == null){
			this.imagesPanel = new FlowPanel();
		}
		return this.imagesPanel;
	}

	public void addImage(String image,ClickHandler clickHandler){
		Label label = new  Label(image);
		if(clickHandler!=null){
			label.addClickHandler(clickHandler);
		}
		getImagesPanel().add(label);
	}
}
