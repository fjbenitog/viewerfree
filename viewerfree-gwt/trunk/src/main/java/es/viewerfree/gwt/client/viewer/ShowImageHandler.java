package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

import es.viewerfree.gwt.client.common.FitImageLoader;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;

public class ShowImageHandler implements ClickHandler {

	private int sizeImage = 400;

	private String albumName;

	private String imageName;
	
	private PopupPanel slidePanel;
	
	private HorizontalPanel imagePanel;

	public ShowImageHandler(String albumName, String imageName) {
		super();
		this.albumName = albumName;
		this.imageName = imageName;
	}
	@Override

	public void onClick(ClickEvent event) {
		getSlidePanel().show();
	}
	
	private PopupPanel getSlidePanel(){
		if(this.slidePanel == null){
			this.slidePanel = new PopupPanel();
			this.slidePanel.setSize(sizeImage+"px", sizeImage+"px");
			this.slidePanel.setGlassEnabled(true);
			this.slidePanel.setAnimationEnabled(true);
			this.slidePanel.setAutoHideEnabled(true);
//			this.slidePanel.setPopupPosition((Window.getClientWidth()-sizeImage)/2, (Window.getClientHeight()-sizeImage)/2);
			this.slidePanel.add(getImagePanel());
			this.slidePanel.center();
		}
		return this.slidePanel;
	}
	
	private HorizontalPanel getImagePanel(){
		if(this.imagePanel == null){
			this.imagePanel = new HorizontalPanel();
			this.imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			FitImageLoader image = new FitImageLoader("images/big-ajax-loader.gif",
					ViewerHelper.createUrlImage(albumName, imageName, Action.SHOW_PICTURE),800,800);
			image.addFitImageLoadHandler(new FitImageLoadHandler() {
				
				@Override
				public void imageLoaded(FitImageLoadEvent event) {
					getSlidePanel().center();
					
				}
			});
			this.imagePanel.add(image);
		}
		return this.imagePanel;
	}

}
