package es.viewerfree.gwt.client.viewer;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
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
import es.viewerfree.gwt.client.viewer.ViewerPanel.UpdateFoldersRightPanel;

public class FavouriteListPanel extends VerticalPanel {


	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private final Constants constants = GWT.create(Constants.class);

	private static Resources resources = GWT.create(Resources.class);

	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private DisclosurePanel albumsListPanel;

	private FlowPanel listPanel; 

	public FavouriteListPanel() {
		setStyleName("albums");
		add(getAlbumsListPanel());
		setWidth("100%");
		setSpacing(10);
	}

	public DisclosurePanel getAlbumsListPanel(){
		if(this.albumsListPanel == null){
			this.albumsListPanel =  new DisclosurePanel(messages.favouriteAlbums());
			this.albumsListPanel.setAnimationEnabled(true);
			this.albumsListPanel.setContent(getListPanel());
		}
		return this.albumsListPanel;
	}

	private FlowPanel getListPanel(){
		if(this.listPanel == null){
			this.listPanel = new FlowPanel();
		}
		return this.listPanel;
	}

	public void addTag(final String tag,final ClickHandler clickHandler, final UpdateFoldersRightPanel updateFoldersRightPanel){
		final DisclosurePanel disclosurePanel = new DisclosurePanel(resources.tagIcon(),resources.tagIcon(),tag);
		disclosurePanel.setAnimationEnabled(true);
		getListPanel().add(disclosurePanel);
		CloseOpenClickHandler closeOpenHandler = new CloseOpenClickHandler(disclosurePanel, tag, updateFoldersRightPanel,
				clickHandler);
		disclosurePanel.addOpenHandler(closeOpenHandler);
		disclosurePanel.addCloseHandler(closeOpenHandler);
	}


	public void addFolder(List<String> albums, DisclosurePanel disclosurePanel,ClickHandler clickHandler){
		FlowPanel albumsPanel = new FlowPanel();
		disclosurePanel.setContent(albumsPanel);
		for (String album : albums) {
			HTML albumLink = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageFolder()+"\">"+album);
			if(clickHandler!=null){
				albumLink.addClickHandler(clickHandler);
			}
			albumsPanel.add(albumLink);
		}
	}

	private final class CloseOpenClickHandler implements
	OpenHandler<DisclosurePanel> , CloseHandler<DisclosurePanel>{
		private final DisclosurePanel disclosurePanel;
		private final String tag;
		private final UpdateFoldersRightPanel updateFoldersRightPanel;
		private final ClickHandler clickHandler;

		private CloseOpenClickHandler(DisclosurePanel disclosurePanel, String tag,
				UpdateFoldersRightPanel updateFoldersRightPanel,
				ClickHandler clickHandler) {
			this.disclosurePanel = disclosurePanel;
			this.tag = tag;
			this.updateFoldersRightPanel = updateFoldersRightPanel;
			this.clickHandler = clickHandler;
		}

		@Override
		public void onOpen(OpenEvent<DisclosurePanel> event) {
			action();
		}

		@Override
		public void onClose(CloseEvent<DisclosurePanel> arg0) {
			action();
		}
		
		private void action() {
			disclosurePanel.clear();
			viewerService.geAlbumsByTag(tag, new AsyncCallback<List<String>>() {

				@Override
				public void onSuccess(List<String> albums) {
					addFolder(albums,disclosurePanel,clickHandler);
					updateFoldersRightPanel.update(albums);
				}

				@Override
				public void onFailure(Throwable arg0) {
					MessageDialogUtil.getErrorDialogBox(messages.serverError());
				}
			});
		}


	}

}
