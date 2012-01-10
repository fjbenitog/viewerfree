package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.dto.AlbumDto;

public class SlidePanel extends PopupPanel {

	private static final int PANEL_PADDING = 10;

	private static final int IMAGE_PADDING = 5;

	private static final int BUTTONS_PANEL_HEIGHT = 100;

	private final Constants constants = GWT.create(Constants.class);

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private HorizontalPanel imagePanel;

	private AlbumDto albumDto;

	private LayoutPanel mainPanel;

	private HorizontalPanel rightArrowPanel;

	private HorizontalPanel leftArrowPanel;

	private Image rightArrow;

	private Image leftArrow;
	
	private Image imageLoader;

	public SlidePanel(AlbumDto albumDto) {
		this.albumDto = albumDto;
		setSize(constants.imageLoaderSize()+"px", constants.imageLoaderSize()+"px");
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		add(getMainPanel());
		getImagePanel().add(getImageLoader());
		getFitImage();
		center();
	}

	private Image getImageLoader(){
		if(this.imageLoader == null){
			this.imageLoader = new Image(constants.viewerImagesPath()+constants.imageLoaderBig());
		}
		return this.imageLoader;
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
			this.leftArrowPanel.setHeight("100%");
			this.leftArrowPanel.add(getLeftArrow());
			this.leftArrowPanel.setTitle(messages.previousPicture());
		}
		return this.leftArrowPanel;
	}

	private Image getLeftArrow(){
		if(this.leftArrow == null){
			this.leftArrow = new Image(constants.viewerImagesPath()+constants.imageLeftArrow());
			this.leftArrow.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					previousPicture();
					update();
				}


			});
			this.leftArrow.setStyleName("link");
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
			this.rightArrowPanel.setHeight("100%");
			this.rightArrowPanel.add(getRightArrow());
			this.rightArrowPanel.setTitle(messages.nextPicture());
		}
		return this.rightArrowPanel;
	}

	private Image getRightArrow(){
		if(this.rightArrow == null){
			this.rightArrow = new Image(constants.viewerImagesPath()+constants.imageRightArrow());
			this.rightArrow.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					nextPicture();
					update();
				}


			});
			this.rightArrow.setStyleName("link");
		}
		return this.rightArrow;
	}
	
	private void nextPicture() {
		if(albumDto.getSelectedPic()<albumDto.getPictures().length-1){
			albumDto.setSelectedPic(albumDto.getSelectedPic()+1);
		}else{
			albumDto.setSelectedPic(0);
		}
	}
	
	private void previousPicture() {
		if(albumDto.getSelectedPic()>0){
			albumDto.setSelectedPic(albumDto.getSelectedPic()-1);
		}else{
			albumDto.setSelectedPic(albumDto.getPictures().length-1);
		}
	}

	private void update() {
		getImagePanel().clear();
		getImagePanel().add(getImageLoader());
		getFitImage();
	}


	private void getFitImage() {
		new FitImage(ViewerHelper.createUrlImage(albumDto.getName(), albumDto.getPictures()[albumDto.getSelectedPic()], Action.SHOW_PICTURE),
				constants.imageSize(),constants.imageSize(),
				new FitImageLoadHandler() {
					@Override
					public void imageLoaded(FitImageLoadEvent event) {
						getImagePanel().remove(getImageLoader());
						getImagePanel().add(event.getFitImage());
						pack(event.getFitImage());
					}
				});
	}

	
	private void pack(FitImage image){
		int delta = Window.getClientHeight()-(image.getHeight()+BUTTONS_PANEL_HEIGHT);
		int maxHeight = 0;
		if(delta< IMAGE_PADDING){
			maxHeight = Window.getClientHeight()-BUTTONS_PANEL_HEIGHT-IMAGE_PADDING;
			image.setMaxHeight(maxHeight);
			setHeight((Window.getClientHeight()-PANEL_PADDING)+"px");
		}else{
			maxHeight = image.getHeight();
			setHeight((image.getHeight()+BUTTONS_PANEL_HEIGHT)+"px");
		}

		setWidth((image.getWidth()+PANEL_PADDING)+"px");
		getMainPanel().setWidgetTopHeight(getImagePanel(), IMAGE_PADDING, Unit.PX, 100, Unit.PCT);
		
		getImagePanel().setHeight(maxHeight+"px");

		getMainPanel().setWidgetTopHeight(getRightArrowPanel(), 0, Unit.PX, maxHeight, Unit.PX);
		getMainPanel().setWidgetLeftRight(getRightArrowPanel(), 70, Unit.PCT, IMAGE_PADDING, Unit.PX);

		getMainPanel().setWidgetTopHeight(getLeftArrowPanel(), 0, Unit.PX, maxHeight, Unit.PX);
		getMainPanel().setWidgetLeftWidth(getLeftArrowPanel(), IMAGE_PADDING, Unit.PX, 30, Unit.PCT);
		
		center();

	}

}
