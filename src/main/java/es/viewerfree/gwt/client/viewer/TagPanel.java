package es.viewerfree.gwt.client.viewer;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.viewer.ViewerPanel.UpdateFoldersRightPanel;

public class TagPanel extends ScrollPanel {
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private final Constants constants = GWT.create(Constants.class);
	
	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private FlowPanel tagPanel;
	
	public TagPanel() {
		setStyleName("pictures");
		add(getTagPanel());
	}

	private FlowPanel getTagPanel(){
		if(this.tagPanel == null){
			this.tagPanel = new FlowPanel();
		}
		return this.tagPanel;
	}

	public void addTag(final String tag, final UpdateFoldersRightPanel updateFoldersRightPanel){
		HTML html = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageTagBigIcon()+"\"><div class='label'>"+tag+"</div>");
		html.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				viewerService.geAlbumsByTag(tag, new AsyncCallback<List<String>>() {
					
					@Override
					public void onSuccess(List<String> albums) {
						updateFoldersRightPanel.update(albums);
					}
					
					@Override
					public void onFailure(Throwable arg0) {
						MessageDialogUtil.getErrorDialogBox(messages.serverError());
					}
				});
			}
		});
		getTagPanel().add(html);
	}
}
