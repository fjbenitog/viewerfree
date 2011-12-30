package es.viewerfree.gwt.client.viewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.FitImageLoader;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;

public class ImagesPanel extends LayoutPanel {

	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private FlowPanel imagesPanel;

	private LayoutPanel albumTitlePanel;

	private Label albumTitleLabel;

	private ScrollPanel scrollImagesPanel;

	public ImagesPanel() {
		add(getAlbumTitlePanel());
		setWidgetTopHeight(getAlbumTitlePanel(), 0, Unit.PX, 70, Unit.PX);
		add(getScrollImagesPanel());
		setWidgetTopBottom(getScrollImagesPanel(), 70, Unit.PX, 0, Unit.PX);
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
			this.albumTitleLabel.setStyleName("titleAlbum");
		}
		return this.albumTitleLabel;
	}

	private ScrollPanel getScrollImagesPanel(){
		if(this.scrollImagesPanel == null){
			this.scrollImagesPanel = new ScrollPanel();
			this.scrollImagesPanel.setStyleName("pictures");
			this.scrollImagesPanel.add(getImagesPanel());
		}
		return this.scrollImagesPanel;
	}

	private FlowPanel getImagesPanel(){
		if(this.imagesPanel == null){
			this.imagesPanel = new FlowPanel();
		}
		return this.imagesPanel;
	}

	public void init(final String albumName){
		getAlbumTitleLabel().setText(albumName);
		viewerService.getPictures(albumName, new CallGetPictures(albumName));
	}

	public void clear(){
		getImagesPanel().clear();
	}

	private final class CallGetPictures implements AsyncCallback<String[]> {


		private static final int imageSize = 150;
		private final String albumName;

		protected CallGetPictures(String albumName) {
			this.albumName = albumName;
		}

		@Override
		public void onSuccess(String[] images) {
			for (String imageName : images) {
				final FitImageLoader loaderImage = new FitImageLoader("images/ajax-loader.gif",
						ViewerHelper.createUrlImage(albumName, imageName, Action.SHOW_THUMBNAIL),imageSize,imageSize);
				loaderImage.addClickHandler(new ShowImageHandler(albumName, imageName));
				getImagesPanel().add(createImagePanel(loaderImage));
			}
		}

		@Override
		public void onFailure(Throwable arg0) {
			ErrorMessageUtil.getErrorDialogBox(messages.serverError());
		}

		private HorizontalPanel createImagePanel(final Image loaderImage) {
			final HorizontalPanel imagePanel = new HorizontalPanel();
			imagePanel.setSize(imageSize+"px", imageSize+"px");
			imagePanel.setStyleName("image");
			imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
			imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			imagePanel.add(loaderImage);
			return imagePanel;
		}

	}
}
