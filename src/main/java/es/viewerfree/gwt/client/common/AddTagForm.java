package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;

public class AddTagForm extends DialogBoxExt{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private static final Constants constants = GWT.create(Constants.class);
	
	private final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private String albumName;
	
	private LayoutPanel mainPanel;
	
	private HorizontalPanel addTagPanel;
	
	private TextBox tagField;
	
	private Image addTagImage;
	
	public AddTagForm(String albumName) {
		super();
		this.albumName = albumName;
		Image image = new Image(constants.imagesPath()+constants.imageCloseButton());
		image.setStyleName("close");
		setCloseWidget(image);
		setHTML("<div style='font-weight: bold;font-family: arial,sans-serif;font-size: 15px;'>"+messages.addTag()+"</div>");
		add(getMainPanel());
		show();
		center();
	}
	
	private LayoutPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.add(getAddTagPanel());
			this.mainPanel.setSize("250px", "200px");
			this.mainPanel.setWidgetTopHeight(getAddTagPanel(), 10, Unit.PX, 40, Unit.PX);
			this.mainPanel.setWidgetLeftRight(getAddTagPanel(), 10, Unit.PX, 10, Unit.PX);
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
	
	private Image getAddTagImage(){
		if(this.addTagImage== null){
			this.addTagImage = new Image(constants.viewerImagesPath()+constants.imageAddTagIconSmall());
			this.addTagImage.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent arg0) {
					viewerService.addTag(albumName, getTagField().getValue(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void arg0) {
							System.out.println("OK");
							
						}
						
						@Override
						public void onFailure(Throwable arg0) {
							System.err.println("KO");
						}
					});
				}
			});
	
		}
		return this.addTagImage;
	}
}
