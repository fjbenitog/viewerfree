package es.viewerfree.gwt.client.common;

import java.io.ObjectInputStream.GetField;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;

public class AddTagForm extends DialogBoxExt{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);
	
	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private String albumName;
	
	private LayoutPanel mainPanel;
	
	private HorizontalPanel addTagPanel;
	
	private TextBox tagField;
	
	private Image addTagImage;
	
	private FlowPanel albumTagsPanel;
	
	private Label albumTagLabel;
	
	public AddTagForm(String albumName) {
		super();
		this.albumName = albumName;
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.addTag()+"</div>");
		add(getMainPanel());
		initValues();
		show();
		center();
	}
	
	private void initValues() {
		viewerService.getTags(albumName, new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> tags) {
				for (String tag : tags) {
					getAlbumTagsPanel().add(new Label(tag));
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageDialogUtil.getErrorDialogBox(messages.serverError());
			}
		});
		
	}

	private LayoutPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.setStyleName("tagForm");
			this.mainPanel.add(getAddTagPanel());
			this.mainPanel.setSize("250px", "250px");
			this.mainPanel.setWidgetTopHeight(getAddTagPanel(), 10, Unit.PX, 40, Unit.PX);
			this.mainPanel.setWidgetLeftRight(getAddTagPanel(), 10, Unit.PX, 10, Unit.PX);
			this.mainPanel.add(getAlbumTagLabel());
			this.mainPanel.setWidgetTopHeight(getAlbumTagLabel(), 44, Unit.PX, 15, Unit.PX);
			this.mainPanel.setWidgetLeftRight(getAlbumTagLabel(), 10, Unit.PX, 10, Unit.PX);
			this.mainPanel.add(getAlbumTagsPanel());
			this.mainPanel.setWidgetTopBottom(getAlbumTagsPanel(), 65, Unit.PX, 10, Unit.PX);
			this.mainPanel.setWidgetLeftRight(getAlbumTagsPanel(), 10, Unit.PX, 10, Unit.PX);
		}
		return this.mainPanel;
	}
	
	private HorizontalPanel getAddTagPanel(){
		if(this.addTagPanel==null){
			this.addTagPanel = new HorizontalPanel();
			this.addTagPanel.setStyleName("editor");
			this.addTagPanel.setSize("100%", "100%");
			this.addTagPanel.add(getTagField());
			this.addTagPanel.setCellHorizontalAlignment(getTagField(), HorizontalPanel.ALIGN_CENTER);
			this.addTagPanel.add(getAddTagImage());
			this.addTagPanel.setCellHorizontalAlignment(getAddTagImage(), HorizontalPanel.ALIGN_LEFT);
			
		}
		return this.addTagPanel;
	}
	
	private TextBox getTagField(){
		if(this.tagField==null){
			this.tagField = new TextBox();
			this.tagField.setMaxLength(20);
		}
		return this.tagField;
	}
	
	private FlowPanel getAlbumTagsPanel(){
		if(this.albumTagsPanel==null){
			this.albumTagsPanel = new FlowPanel();
		}
		return this.albumTagsPanel;
	}
	
	private Label getAlbumTagLabel(){
		if(this.albumTagLabel==null){
			this.albumTagLabel = new Label(messages.albumTags());
			this.albumTagLabel.setStyleName("titleLabel");
		}
		return this.albumTagLabel;
	}
	
	private Image getAddTagImage(){
		if(this.addTagImage== null){
			this.addTagImage = new Image(constants.viewerImagesPath()+constants.imageAddTagIconSmall());
			this.addTagImage.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					if(getTagField().getText().isEmpty()){
						return;
					}
					
					viewerService.addTag(albumName, getTagField().getValue(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void arg0) {
							getTagField().setText("");
							getAlbumTagsPanel().clear();
							initValues();
						}
						
						@Override
						public void onFailure(Throwable t) {
							MessageDialogUtil.getErrorDialogBox(messages.serverError());
						}
					});
				}
			});
	
		}
		return this.addTagImage;
	}
}
