package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.common.FitImageLoader;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;

public class SlidePanel extends PopupPanel {
	
	private static final int PANEL_PADDING = 10;

	private static final int IMAGE_PADDING = 5;

	private static final int BUTTONS_PANEL_HEIGHT = 100;

	private final Constants constants = GWT.create(Constants.class);

	private HorizontalPanel imagePanel;
	
	private String albumName;

	private String imageName;
	
	private LayoutPanel mainPanel;
	
	private HorizontalPanel rightArrowPanel;
	
	private HorizontalPanel leftArrowPanel;
	
	private Image rightArrow;
	
	private Image leftArrow;
	
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
			this.mainPanel.add(getImagePanel());
			this.mainPanel.add(getRightArrowPanel());
			this.mainPanel.add(getLeftArrowPanel());

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
						setSize(image.getFitImage());
						
						center();				
					}
				}
				
			});
			this.imagePanel.add(image);

			
		}
		return this.imagePanel;
	}
	
	private HorizontalPanel getLeftArrowPanel(){
		if(this.leftArrowPanel == null){
			this.leftArrowPanel = new HorizontalPanel();
			this.leftArrowPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.leftArrowPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
			this.leftArrowPanel.setStyleName("arrows");
			this.leftArrowPanel.setWidth("100%");
			this.leftArrowPanel.add(getLeftArrow());
		}
		return this.leftArrowPanel;
	}
	
	private Image getLeftArrow(){
		if(this.leftArrow == null){
			this.leftArrow = new Image(constants.viewerImagesPath()+constants.imageLeftArrow());
			this.leftArrow.setTitle("Previous Picture");
		}
		return this.leftArrow;
	}
	
	private HorizontalPanel getRightArrowPanel(){
		if(this.rightArrowPanel == null){
			this.rightArrowPanel = new HorizontalPanel();
			this.rightArrowPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.rightArrowPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
			this.rightArrowPanel.setStyleName("arrows");
			this.rightArrowPanel.setWidth("100%");
			this.rightArrowPanel.add(getRightArrow());
			this.rightArrowPanel.setTitle("Next Picture");
		}
		return this.rightArrowPanel;
	}
	
	private Image getRightArrow(){
		if(this.rightArrow == null){
			this.rightArrow = new Image(constants.viewerImagesPath()+constants.imageRightArrow());
		}
		return this.rightArrow;
	}

	private void setSize(FitImage image){
		int delta = Window.getClientHeight()-(image.getHeight()+BUTTONS_PANEL_HEIGHT);
		int maxHeight = 0;
		if(delta< IMAGE_PADDING){
			maxHeight = Window.getClientHeight()-BUTTONS_PANEL_HEIGHT-IMAGE_PADDING;
			image.setMaxHeight(maxHeight);
			setSize((image.getWidth()+PANEL_PADDING)+"px",(Window.getClientHeight()-PANEL_PADDING)+"px");
		}else{
			maxHeight = image.getHeight();
			setSize((image.getWidth()+PANEL_PADDING)+"px",(image.getHeight()+BUTTONS_PANEL_HEIGHT)+"px");
		}
		
		getMainPanel().setWidgetLeftWidth(getImagePanel(), IMAGE_PADDING, Unit.PX, 100, Unit.PCT);
		getMainPanel().setWidgetTopHeight(getImagePanel(), IMAGE_PADDING, Unit.PX, 100, Unit.PCT);
		
		getMainPanel().setWidgetTopHeight(getRightArrowPanel(), 0, Unit.PX, maxHeight, Unit.PX);
		getMainPanel().setWidgetLeftRight(getRightArrowPanel(), 90, Unit.PCT, 0, Unit.PCT);
		
		getMainPanel().setWidgetTopHeight(getLeftArrowPanel(), 0, Unit.PX, maxHeight, Unit.PX);
		getMainPanel().setWidgetLeftWidth(getLeftArrowPanel(), 0, Unit.PCT, 10, Unit.PCT);

	}
	
}
