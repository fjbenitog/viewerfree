package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.dto.AlbumDto;

public class ImagesPanel extends LayoutPanel {

	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private final Constants constants = GWT.create(Constants.class);

	private FlowPanel imagesFlowPanel;

	private LayoutPanel albumTitlePanel;

	private Label albumTitleLabel;

	private ScrollPanel scrollImagesPanel;

	private int total;

	private AlbumDto albumDto;

	public ImagesPanel() {
		add(getAlbumTitlePanel());
		setWidgetTopHeight(getAlbumTitlePanel(), 0, Unit.PX, 70, Unit.PX);
		add(getScrollImagesPanel());
		setWidgetTopBottom(getScrollImagesPanel(), 70, Unit.PX, 0, Unit.PX);
		Window.addResizeHandler( new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				addPictureIfisNecessary();
			}
		});
	}

	private LayoutPanel getAlbumTitlePanel(){
		if(this.albumTitlePanel == null){
			this.albumTitlePanel = new LayoutPanel();
			this.albumTitlePanel.add(getAlbumTitleLabel());
			this.albumTitlePanel.setWidgetLeftRight(getAlbumTitleLabel(), 50, Unit.PX, 100, Unit.PX);
			this.albumTitlePanel.setWidgetTopBottom(getAlbumTitleLabel(), 2, Unit.PX, 15, Unit.PX);
			this.albumTitlePanel.setStyleName("titlePanel");
		}
		return this.albumTitlePanel;
	}

	private Label getAlbumTitleLabel(){
		if(this.albumTitleLabel == null){
			this.albumTitleLabel = new Label();
			this.albumTitleLabel.setStyleName("titleLabel");
		}
		return this.albumTitleLabel;
	}

	private ScrollPanel getScrollImagesPanel(){
		if(this.scrollImagesPanel == null){
			this.scrollImagesPanel = new ScrollPanel();
			this.scrollImagesPanel.setStyleName("pictures");
			this.scrollImagesPanel.add(getImagesFlowPanel());
			this.scrollImagesPanel.setAlwaysShowScrollBars(true);
			this.scrollImagesPanel.addScrollHandler(new ScrollHandler() {
				
				private int lastScrollPos = 0;
				@Override
				public void onScroll(ScrollEvent event) {
					// If scrolling up, ignore the event.
					int oldScrollPos = lastScrollPos;
					lastScrollPos = scrollImagesPanel.getVerticalScrollPosition();
					if (oldScrollPos >= lastScrollPos && albumDto.getPictures().length-total<=0) {
						return;
					}
					int maxScrollTop = scrollImagesPanel.getWidget().getOffsetHeight()
							- scrollImagesPanel.getOffsetHeight();
					if (lastScrollPos >= maxScrollTop) {
						addPictureIfisNecessary();
					}
				}
				
			});
			

			
		}
		return this.scrollImagesPanel;
	}
	
	private void addPictureIfisNecessary() {
		int picByRow = getOffsetWidth()/150;
		int diffPics = albumDto.getPictures().length-total;
		int initcount = total;
		for (int j = initcount; j < (Math.min(picByRow, diffPics)+initcount); j++) {
			total++;
			addPictureToPanel(albumDto, j);
		}
	}

	private FlowPanel getImagesFlowPanel(){
		if(this.imagesFlowPanel == null){
			this.imagesFlowPanel = new FlowPanel();
		}
		return this.imagesFlowPanel;
	}

	public void init(final String albumName){
		getAlbumTitleLabel().setText(albumName);
		viewerService.getPictures(albumName, new CallGetPictures(this));
	}

	public void clear(){
		getImagesFlowPanel().clear();
	}

	private void addPictureToPanel(AlbumDto album, int i) {
		System.err.println("Siguiente Foto:"+album.getPictures()[i]);
		ShowImageHandler handler = new ShowImageHandler(this,album,i);
		final Image loaderImage = new Image(constants.viewerImagesPath()+constants.imageLoader());
		final HorizontalPanel imagePanel = createImagePanel(loaderImage);
		final FitImage fitImage = new FitImage(ViewerHelper.createUrlImage(album.getCryptedName(), album.getCryptedPictures()[i], Action.SHOW_THUMBNAIL),
				constants.imageThumbnailSize(),constants.imageThumbnailSize() ,
				new FitImageLoadHandler() {

			@Override
			public void imageLoaded(FitImageLoadEvent event) {
				imagePanel.remove(loaderImage);
				imagePanel.add(event.getFitImage());
			}
		});
		fitImage.setTitle(album.getPictures()[i]);
		fitImage.addClickHandler(handler);
		getImagesFlowPanel().add(imagePanel);

	}
	
	private HorizontalPanel createImagePanel(final Image loaderImage) {
		final HorizontalPanel imagePanel = new HorizontalPanel();
		imagePanel.setSize(constants.imageThumbnailSize()+"px", constants.imageThumbnailSize()+"px");
		imagePanel.setStyleName("image");
		imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		imagePanel.add(loaderImage);
		return imagePanel;
	}
	
	private final class CallGetPictures implements AsyncCallback<AlbumDto> {

		private ImagesPanel imagesPanel;
		
		public CallGetPictures(ImagesPanel imagesPanel) {
			super();
			this.imagesPanel = imagesPanel;
		}



		@Override
		public void onSuccess(AlbumDto album) {
			albumDto = album; 
			total = (imagesPanel.getOffsetWidth()/150)*(imagesPanel.getOffsetHeight()/150);
			int maxNum = total<=album.getPictures().length?total:album.getPictures().length;
			for (int i = 0; i<maxNum ; i++){
				addPictureToPanel(album, i);
			}
		}

		

		@Override
		public void onFailure(Throwable arg0) {
			ErrorMessageUtil.getErrorDialogBox(messages.serverError());
		}



	}
}
