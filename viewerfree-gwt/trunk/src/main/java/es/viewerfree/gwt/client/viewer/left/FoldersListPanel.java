package es.viewerfree.gwt.client.viewer.left;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.viewer.ClickItemObserver;
import es.viewerfree.gwt.client.viewer.Subject;
import es.viewerfree.gwt.client.viewer.SubjectHandler;

public class FoldersListPanel extends VerticalPanel implements ClickItemObserver{

	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private static final Constants constants = GWT.create(Constants.class);

	private DisclosurePanel albumsListPanel;

	private FlowPanel listPanel; 
	
	public FoldersListPanel() {
		Subject.getInstance().addObserver(this);
		setStyleName("albums");
		add(getAlbumsListPanel());
		setWidth("100%");
		setSpacing(10);
		addAlbums();
	}

	public DisclosurePanel getAlbumsListPanel(){
		if(this.albumsListPanel == null){
			this.albumsListPanel =  new DisclosurePanel(messages.albumsLabel());
			this.albumsListPanel.setAnimationEnabled(true);
			this.albumsListPanel.setContent(getListPanel());
			SubjectHandler clickAlbumHandler = new SubjectHandler(messages.albumsLabel(),Type.TOTAL_ALBUM);
			this.albumsListPanel.addOpenHandler(clickAlbumHandler);
			this.albumsListPanel.addCloseHandler(clickAlbumHandler);
		}
		return this.albumsListPanel;
	}

	private FlowPanel getListPanel(){
		if(this.listPanel == null){
			this.listPanel = new FlowPanel();
		}
		return this.listPanel;
	}

	private void setFolders(List<String> folders){
		getListPanel().clear();
		for (final String folder : folders) {
			HTML albumLink = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageFolder()+"\">"+folder);
			albumLink.addClickHandler(new SubjectHandler(folder,Type.ALBUM));
			albumLink.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					markFolder(folder);
				}
			});
			getListPanel().add(albumLink);
		}
	}
	

	private void markFolder(String album){
		for (int i = 0; i < getListPanel().getWidgetCount(); i++) {
			HTML widget = (HTML)getListPanel().getWidget(i);
			widget.setStyleName("gwt-HTML");
			if(album.equals(widget.getText())){
				widget.setStyleName("selectedFolder");
			}
		}
	}
	
	private void addAlbums() {
		viewerService.getUserAlbums(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String>  albums) {
				setFolders(albums);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				MessageDialogUtil.getErrorDialogBox(messages.serverError());
			}
		});
	}
	
	@Override
	public void update(Type type, String name) {
		if(type == Type.ALBUM){
			markFolder(name);
		}else{
			markFolder("");
		}
	}

}
