package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ShowImageHandler implements ClickHandler {

	private String albumName;

	private String imageName;
	
	private SlidePanel slidePanel;
	
	public ShowImageHandler(String albumName, String imageName) {
		super();
		this.albumName = albumName;
		this.imageName = imageName;
	}
	@Override

	public void onClick(ClickEvent event) {
		getSlidePanel().show();
	}
	
	private SlidePanel getSlidePanel(){
		if(this.slidePanel == null){
			this.slidePanel = new SlidePanel(albumName,imageName);
		}
		return this.slidePanel;
	}
	
}
