package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;

public class ShowImageHandler implements ClickHandler {
	
	public ShowImageHandler() {
		super();
	}
	@Override

	public void onClick(ClickEvent event) {
		if(event.getSource() instanceof Image){
			Image image = (Image) event.getSource();
			String[] params = image.getTitle().split(":");
			getSlidePanel(params[0],params[1]).show();
		}
	}
	
	private SlidePanel getSlidePanel(String albumName, String imageName){
		return new SlidePanel(albumName,imageName);
	}
	
}
