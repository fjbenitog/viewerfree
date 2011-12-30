package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.common.FitImageLoader;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;

public class SlidePanel extends PopupPanel {

	private int sizeImage = 400;
	
	private HorizontalPanel imagePanel;
	
	private String albumName;

	private String imageName;
	
	public SlidePanel(String albumName, String imageName) {
		this.albumName = albumName;
		this.imageName = imageName;
		setSize(sizeImage+"px", sizeImage+"px");
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		add(getImagePanel());
		center();
	}
	
	private HorizontalPanel getImagePanel(){
		if(this.imagePanel == null){
			this.imagePanel = new HorizontalPanel();
			this.imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			FitImageLoader image = new FitImageLoader("images/big-ajax-loader.gif",
					ViewerHelper.createUrlImage(albumName, imageName, Action.SHOW_PICTURE),800,800);
			image.addFitImageAttachHandler(new Handler(){
				@Override
				public void onAttachOrDetach(AttachEvent event) {
					if(event.isAttached()){
						center();				
					}
				}
				
			});
			this.imagePanel.add(image);
		}
		return this.imagePanel;
	}

	
	
}
