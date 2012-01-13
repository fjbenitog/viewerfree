package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
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

	private static final int PANEL_BUTTONS_WIDTH = 120;

	private static final int slideTime = 3000;

	private static final int PANEL_PADDING = 10;

	private static final int IMAGE_PADDING = 5;

	private static final int BUTTONS_PANEL_HEIGHT = 60;

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

	private Image imagePlay;

	private Image imageStop;

	private Image imageNext;

	private Image imagePrevious;

	private HTML imageFileDownload;

	private HorizontalPanel buttonsPanel;

	private HorizontalPanel utilityPanel;

	private Timer picTimer;


	public SlidePanel(AlbumDto albumDto) {
		this.albumDto = albumDto;
		setSize(constants.imageLoaderSize()+"px", constants.imageLoaderSize()+"px");
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		add(getMainPanel());
		getImagePanel().add(getImageLoader());
		getFitImage();
		addCloseHandler(new HandlerStopSlideshow());
		center();
	}

	private Timer getPicTimer(){
		if(this.picTimer == null){
			this.picTimer = new Timer() {

				@Override
				public void run() {
					nextPicture();
					update();
				}
			};
		}
		return this.picTimer;
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
			this.mainPanel.add(getUtilityPanel());
			this.mainPanel.add(getButtonsPanel());
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
			this.leftArrow.addClickHandler(new ClickHandlerPreviousPic());
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
			this.rightArrow.addClickHandler(new ClickHandlerNextPic());
			this.rightArrow.setStyleName("link");
		}
		return this.rightArrow;
	}

	private Image getImagePlay(){
		if(this.imagePlay == null){
			this.imagePlay = new Image(constants.viewerImagesPath()+constants.imagePlay());
			this.imagePlay.setStyleName("buttons");
			this.imagePlay.setTitle(messages.startSlideShow());
			this.imagePlay.addClickHandler(new ClickHandlerStartSlideshow());
		}
		return this.imagePlay;
	}

	private Image getImageNext(){
		if(this.imageNext == null){
			this.imageNext = new Image(constants.viewerImagesPath()+constants.imageNext());
			this.imageNext.setStyleName("buttons");
			this.imageNext.setTitle(messages.nextPicture());
			this.imageNext.addClickHandler(new ClickHandlerNextPic());
		}
		return this.imageNext;
	}

	private Image getImagePrevious(){
		if(this.imagePrevious == null){
			this.imagePrevious = new Image(constants.viewerImagesPath()+constants.imagePrevious());
			this.imagePrevious.setStyleName("buttons");
			this.imagePrevious.setTitle(messages.previousPicture());
			this.imagePrevious.addClickHandler(new ClickHandlerPreviousPic());
		}
		return this.imagePrevious;
	}

	private Image getImageStop(){
		if(this.imageStop == null){
			this.imageStop = new Image(constants.viewerImagesPath()+constants.imageDisabledStop());
			this.imageStop.setStyleName("disabledbuttons");
			this.imageStop.setTitle(messages.stopSlideShow());
			this.imageStop.addClickHandler(new HandlerStopSlideshow());
		}
		return this.imageStop;
	}

	private HTML getImageFileDownload(){
		if(this.imageFileDownload == null){
			this.imageFileDownload = new HTML(createDownloadImageLink());
			this.imageFileDownload.setTitle(messages.downloadPic());
			this.imageFileDownload.setStyleName("link");
			this.imageFileDownload.addClickHandler(new HandlerStopSlideshow());
		}
		return this.imageFileDownload;
	}

	private String createDownloadImageLink() {
		return "<a target='_blank' href='"+ViewerHelper.createUrlImage(albumDto.getCryptedName(), getSelectedCriptedImage(), Action.SHOW_REAL_PICTURE)+
		"'><img border='0' src='"+constants.viewerImagesPath()+constants.imageFileDownload()+"'/></a>";
	}

	private HorizontalPanel getButtonsPanel(){
		if(this.buttonsPanel == null){
			this.buttonsPanel = new HorizontalPanel();
			this.buttonsPanel.setSize("100%", "100%");
			this.buttonsPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.buttonsPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			this.buttonsPanel.add(getImageStop());
			this.buttonsPanel.add(getImagePrevious());
			this.buttonsPanel.add(getImagePlay());
			this.buttonsPanel.add(getImageNext());
		}
		return this.buttonsPanel;
	}

	private HorizontalPanel getUtilityPanel(){
		if(this.utilityPanel == null){
			this.utilityPanel = new HorizontalPanel();
			this.utilityPanel.setSize("100%", "100%");
			this.utilityPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			this.utilityPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
			this.utilityPanel.add(getImageFileDownload());
		}
		return this.utilityPanel;
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
		getImageFileDownload().setHTML(createDownloadImageLink());
	}


	private void getFitImage() {
		FitImage fitImage = new FitImage(ViewerHelper.createUrlImage(albumDto.getCryptedName(), getSelectedCriptedImage(), Action.SHOW_PICTURE),
				constants.imageSize(),constants.imageSize(),
				new FitImageLoadHandler() {
			@Override
			public void imageLoaded(FitImageLoadEvent event) {
				getImagePanel().remove(getImageLoader());
				getImagePanel().add(event.getFitImage());
				resize(event.getFitImage());
			}
		});
		fitImage.setTitle(getSelectedImage());
	}

	private String getSelectedImage() {
		return albumDto.getPictures()[albumDto.getSelectedPic()];
	}
	
	private String getSelectedCriptedImage() {
		return albumDto.getCryptedPictures()[albumDto.getSelectedPic()];
	}


	private void resize(FitImage image){
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

		getMainPanel().setWidgetTopHeight(getButtonsPanel(), maxHeight+IMAGE_PADDING+1, Unit.PX, BUTTONS_PANEL_HEIGHT-IMAGE_PADDING, Unit.PX);
		int offset = ((image.getWidth()+PANEL_PADDING)/2)-PANEL_BUTTONS_WIDTH;
		getMainPanel().setWidgetLeftRight(getButtonsPanel(), offset, Unit.PX, offset, Unit.PX);

		getMainPanel().setWidgetTopHeight(getUtilityPanel(), maxHeight+IMAGE_PADDING+1, Unit.PX, BUTTONS_PANEL_HEIGHT-IMAGE_PADDING, Unit.PX);
		getMainPanel().setWidgetLeftWidth(getUtilityPanel(), 5*IMAGE_PADDING, Unit.PX, 30, Unit.PCT);

		center();

	}

	private void updateButtonsStop() {
		getImageStop().setStyleName("disabledbuttons");
		getImageStop().setUrl(constants.viewerImagesPath()+constants.imageDisabledStop());
		getImagePlay().setStyleName("buttons");
		getImagePlay().setUrl(constants.viewerImagesPath()+constants.imagePlay());
	}

	private class ClickHandlerNextPic implements ClickHandler{

		public void onClick(ClickEvent clickevent) {
			getPicTimer().cancel();
			updateButtonsStop();
			nextPicture();
			update();
		}
	}
	
	private class ClickHandlerPreviousPic implements ClickHandler{

		public void onClick(ClickEvent clickevent) {
			getPicTimer().cancel();
			updateButtonsStop();
			previousPicture();
			update();
		}
	}
	
	private class ClickHandlerStartSlideshow implements ClickHandler{

		@Override
		public void onClick(ClickEvent clickevent) {
			getImageStop().setStyleName("buttons");
			getImageStop().setUrl(constants.viewerImagesPath()+constants.imageStop());
			getImagePlay().setStyleName("disabledbuttons");
			getImagePlay().setUrl(constants.viewerImagesPath()+constants.imageDisabledPlay());
			getPicTimer().scheduleRepeating(slideTime);
		}
	}
	
	private class HandlerStopSlideshow implements ClickHandler,CloseHandler<PopupPanel>{

		public void onClick(ClickEvent clickevent) {
			stop();
		}

		private void stop() {
			getPicTimer().cancel();
			updateButtonsStop();
		}

		@Override
		public void onClose(CloseEvent<PopupPanel> event) {
			stop();
		}
	}

}
