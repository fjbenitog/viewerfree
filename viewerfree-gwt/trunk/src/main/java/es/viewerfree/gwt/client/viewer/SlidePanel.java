package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.common.FitImageLoader;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;

public class SlidePanel extends PopupPanel {
	
	private final Constants constants = GWT.create(Constants.class);

	private HorizontalPanel imagePanel;
	
	private String albumName;

	private String imageName;
	
	private LayoutPanel mainPanel;
	
	public SlidePanel(String albumName, String imageName) {
		this.albumName = albumName;
		this.imageName = imageName;
		setSize(constants.imageLoaderSize()+"px", constants.imageLoaderSize()+"px");
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		add(getMainPanel());
		center();
	}
	
	private LayoutPanel getMainPanel(){
		if(this.mainPanel == null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.setTitle("main");
			this.mainPanel.add(getImagePanel());
		}
		return this.mainPanel;
	}
	
	private HorizontalPanel getImagePanel(){
		if(this.imagePanel == null){
			this.imagePanel = new HorizontalPanel();
			this.imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			this.imagePanel.setWidth("100%");
			final FitImageLoader image = new FitImageLoader(constants.viewerImagesPath()+constants.imageLoaderBig(),
					ViewerHelper.createUrlImage(albumName, imageName, Action.SHOW_PICTURE),
					constants.imageSize(),constants.imageSize());
			image.addFitImageAttachHandler(new Handler(){
				@Override
				public void onAttachOrDetach(AttachEvent event) {
					if(event.isAttached()){ 
						setImageSizeHeight(image.getFitImage());
						center();				
					}
				}
				
			});
			this.imagePanel.add(image);
		}
		return this.imagePanel;
	}

	private void setImageSizeHeight(FitImage image){
		int delta = Window.getClientHeight()-(image.getHeight()+100);
		if(delta< 5){
			image.setMaxHeight(Window.getClientHeight()-100-5);
			setSize((image.getWidth()+10)+"px",(Window.getClientHeight()-10)+"px");
		}else{
			setSize((image.getWidth()+10)+"px",(image.getHeight()+100)+"px");
		}
		
		getMainPanel().setWidgetLeftWidth(getImagePanel(), 5, Unit.PX, 100, Unit.PCT);
		getMainPanel().setWidgetTopHeight(getImagePanel(), 5, Unit.PX, 100, Unit.PCT);
	}
	
}
