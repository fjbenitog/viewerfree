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

public class TagPanel extends ScrollPanel {

	private static final Constants constants = GWT.create(Constants.class);

	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private FlowPanel tagPanel;
	
	public TagPanel() {
		setStyleName("pictures");
		add(getTagPanel());
		viewerService.getTags(new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> tags) {
				setTagsList(tags);
			}

			@Override
			public void onFailure(Throwable caught) {
				MessageDialogUtil.getErrorDialogBox(messages.serverError());
			}
		});
	}

	private FlowPanel getTagPanel(){
		if(this.tagPanel == null){
			this.tagPanel = new FlowPanel();
		}
		return this.tagPanel;
	}
	
	public void setTagsList(List<String>  tags){
		getTagPanel().clear();
		for (String tag : tags) {
			HTML html = new HTML("<img border=\"0\" src=\""+constants.viewerImagesPath()+constants.imageTagBigIcon()+"\"><div class='label'>"+tag+"</div>");
			html.addClickHandler(new SubjectHandler(tag, Type.TAG));
			getTagPanel().add(html);
		}
	}

}
