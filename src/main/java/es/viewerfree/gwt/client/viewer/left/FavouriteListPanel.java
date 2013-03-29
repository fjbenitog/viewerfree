package es.viewerfree.gwt.client.viewer.left;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.Resources;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.viewer.ClickItemObserver.Type;
import es.viewerfree.gwt.client.viewer.SubjectHandler;

public class FavouriteListPanel extends VerticalPanel  {


	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);

	private static Resources resources = GWT.create(Resources.class);

	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private DisclosurePanel tagsListPanel;

	private FlowPanel listPanel; 
	
	public FavouriteListPanel() {
		setStyleName("albums");
		add(getAlbumsListPanel());
		setWidth("100%");
		setSpacing(10);
		setTags();
	}

	private void setTags() {
		viewerService.getTags(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String>  tags) {
				addTags(tags);
			}

			@Override
			public void onFailure(Throwable arg0) {
				showServerError();
			}
		});
	}

	public DisclosurePanel getAlbumsListPanel(){
		if(this.tagsListPanel == null){
			this.tagsListPanel =  new DisclosurePanel(messages.favouriteAlbums());
			this.tagsListPanel.setAnimationEnabled(true);
			this.tagsListPanel.setContent(getListPanel());
			SubjectHandler handler = new SubjectHandler(messages.favouriteAlbums(), Type.TOTAL_TAGS);
			tagsListPanel.addOpenHandler(handler);
			tagsListPanel.addCloseHandler(handler);
		}
		return this.tagsListPanel;
	}

	private FlowPanel getListPanel(){
		if(this.listPanel == null){
			this.listPanel = new FlowPanel();
		}
		return this.listPanel;
	}

	public void addTags( List<String> tags){
		getListPanel().clear();
		for (final String tag : tags) {
			final DisclosurePanel disclosurePanel = new DisclosurePanel(resources.tagIcon(),resources.tagIcon(),tag);
			disclosurePanel.setAnimationEnabled(true);
			getListPanel().add(disclosurePanel);
			addAlbumsToTag(tag, disclosurePanel);
			SubjectHandler handler = new SubjectHandler(tag, Type.TAG);
			disclosurePanel.addOpenHandler(handler);
			disclosurePanel.addCloseHandler(handler);
		}
	}

	private void addAlbumsToTag(final String tag,
			final DisclosurePanel disclosurePanel) {
		viewerService.geAlbumsByTag(tag, new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> albums) {
				FlowPanel albumsPanel = new FlowPanel();
				disclosurePanel.setContent(albumsPanel);
				for (final String album : albums) {
					HTML albumLink = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageFolder()+"\">"+album);
					albumsPanel.add(albumLink);
					albumLink.addClickHandler(new SubjectHandler(album,Type.ALBUM));
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				showServerError();
			}

		});
	}
	
	private static void showServerError() {
		MessageDialogUtil.getErrorDialogBox(messages.serverError());
	}

	public void update() {
		setTags();
	}



}
