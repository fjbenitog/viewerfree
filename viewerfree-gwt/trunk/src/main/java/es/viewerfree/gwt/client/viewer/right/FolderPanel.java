package es.viewerfree.gwt.client.viewer.right;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.viewer.SubjectHandler;
import es.viewerfree.gwt.client.viewer.ClickItemObserver.Type;

public class FolderPanel extends ScrollPanel {

	private static final Constants constants = GWT.create(Constants.class);
	
	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private FlowPanel folderPanel;
	
	public FolderPanel() {
		setStyleName("pictures");
		add(getFolderPanel());
		viewerService.getUserAlbums(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> albums) {
				setAlbumsList(albums);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageDialogUtil.getErrorDialogBox(messages.serverError());
			}
		});
	}

	private FlowPanel getFolderPanel(){
		if(this.folderPanel == null){
			this.folderPanel = new FlowPanel();
		}
		return this.folderPanel;
	}
	
	public void setAlbumsList(List<String>  albums){
		getFolderPanel().clear();
		for (String album : albums) {
			HTML html = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageHugeFolder()+"\"><div class='label'>"+album+"</div>");
			html.addClickHandler(new SubjectHandler(album, Type.ALBUM));
			getFolderPanel().add(html);
		}
	}
	
	
}
