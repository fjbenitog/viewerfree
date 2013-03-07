package es.viewerfree.gwt.client.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.admin.ui.ActionPanel;
import es.viewerfree.gwt.client.common.UploadPicForm;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.util.ViewerHelper;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.dto.AlbumDto;
import es.viewerfree.gwt.shared.dto.PictureDto;

public class AlbumActionPanel extends ActionPanel<String>{

	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);

	private static Constants constants = GWT.create(Constants.class);

	private Button createAlbumButton;

	private MenuItem uploadPicturerItem;

	private CellList<PictureDto> imagesList;

	private ScrollPanel listScrollPanel;

	private HorizontalPanel imagePanel;

	private LayoutPanel rightPanel;

	private AlbumDto albumDto;

	private Label picTitle;

	public AlbumActionPanel() {
		super();
		setWidgetLeftWidth(getMainPanel(), 20, Unit.PX, 45, Unit.PCT);
		add(getRightPanel());
		setWidgetRightWidth(getRightPanel(), 20, Unit.PX, 45, Unit.PCT);

		getButtonsPanel().add(getCreateAlbumButton());
		getButtonsPanel().add(getActionsMenu());
		getMenuBarActions().addItem(getUploadPicturerItem());
		getTableScrollPanel().setStyleName("albumPanel");
		getTable().getSelectionModel().addSelectionChangeHandler(new GetPicturesCallback());
	}

	private LayoutPanel getRightPanel(){
		if(this.rightPanel == null){
			this.rightPanel = new LayoutPanel();
			this.rightPanel.add(getPicTitle());
			this.rightPanel.setWidgetTopHeight(getPicTitle(), 50, Unit.PX, 22, Unit.PX);
			this.rightPanel.add(getListScrollPanel());
			this.rightPanel.setWidgetTopHeight(getListScrollPanel(), 70, Unit.PX, 35, Unit.PCT);
			this.rightPanel.add(getImagePanel());
			this.rightPanel.setWidgetTopHeight(getImagePanel(), 55, Unit.PCT, 40, Unit.PCT);
		}
		return this.rightPanel;
	}

	private Label getPicTitle(){
		if(this.picTitle==null){
			this.picTitle = new Label(messages.picturesLabel());
			this.picTitle.setStyleName("picTitle");
		}
		return this.picTitle;
	}

	private ScrollPanel getListScrollPanel(){
		if(this.listScrollPanel == null){
			this.listScrollPanel = new ScrollPanel();
			this.listScrollPanel.add(getImagesList());
			this.listScrollPanel.setStyleName("albumPanel");
		}
		return this.listScrollPanel;
	}

	private CellList<PictureDto> getImagesList(){
		if(this.imagesList==null){
			this.imagesList = new CellList<PictureDto>(new PictureCell());
			final SingleSelectionModel<PictureDto> selectionModel = new SingleSelectionModel<PictureDto>();
			this.imagesList.setSelectionModel(selectionModel);
			selectionModel.addSelectionChangeHandler(new SelectionPictureHandler());

		}
		return this.imagesList;
	}

	private Button getCreateAlbumButton(){
		if(this.createAlbumButton == null){
			this.createAlbumButton = new Button(messages.createAlbum());
			this.createAlbumButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					newAlbumForm();
				}
			});
		}
		return this.createAlbumButton;
	}

	private MenuItem getUploadPicturerItem(){
		if(this.uploadPicturerItem==null){
			this.uploadPicturerItem = new MenuItem(messages.uploadPicture(), new Command() {

				@Override
				public void execute() {
					uploadPicForm(getSelectedItem());
				}
			});
			this.uploadPicturerItem.setEnabled(false);
		}
		return this.uploadPicturerItem;
	}
	
	private HorizontalPanel getImagePanel() {
		if(this.imagePanel==null){
			this.imagePanel = new HorizontalPanel();
			this.imagePanel.setWidth("100%");
			this.imagePanel.setHeight("100%");
			this.imagePanel.setStyleName("image");
			this.imagePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			this.imagePanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		}
		return imagePanel;
	}

	private void newAlbumForm(){
		AlbumForm createAlbumForm = new AlbumForm();
		createAlbumForm.setRefreshWidgetListener(new RefreshPanelListener());
		createAlbumForm.setAnimationEnabled(true);
		createAlbumForm.setGlassEnabled(true);
	}

	private void uploadPicForm(String albumName){
		UploadPicForm UploadPicForm = new UploadPicForm(albumName);
		UploadPicForm.setRefreshWidgetListener(new RefreshPanelListener());
		UploadPicForm.setAnimationEnabled(true);
		UploadPicForm.setGlassEnabled(true);
	}


	private String getSelectedItem(){
		SingleSelectionModel<String> selectionModel= (SingleSelectionModel<String>)getTable().getSelectionModel();
		return selectionModel.getSelectedObject();
	}


	@Override
	protected void addColumns(ListHandler<String> columnSortHandler) {
		final SelectionModel<String> selectionModel = new SingleSelectionModel<String>(
				new ProvidesKey<String>() {
					@Override
					public Object getKey(String name) {
						return name;
					}
				});
		getTable().setSelectionModel(selectionModel);

		TextColumn<String> nameColumn = new TextColumn<String>() {

			@Override
			public String getValue(String name) {
				return name;
			}
		};
		nameColumn.setSortable(true);
		getTable().addColumn(nameColumn,messages.albumLabel());
		getTable().setColumnWidth(nameColumn, "100%");

		columnSortHandler.setComparator(nameColumn,
				new Comparator<String>() {
			public int compare(String o1, String o2) {
				return stringComparator.compare(o1,o2);
			}
		});
	}

	@Override
	protected void getResults(AsyncCallback<List<String>> asyncCallbackList) {
		viewerService.getAlbums(asyncCallbackList);

	}

	private void addPictureToPanel(PictureDto pictureDto) {
		if(pictureDto==null){
			return;
		}
		final Image loaderImage = new Image(constants.viewerImagesPath()+constants.imageLoader());
		getImagePanel().clear();
		getImagePanel().add(loaderImage);
		final FitImage fitImage = new FitImage(ViewerHelper.createUrlImage(getAlbumDto().getCryptedName(), pictureDto.getCriptedName(), Action.SHOW_THUMBNAIL),
				constants.imageThumbnailSize(),constants.imageThumbnailSize() ,
				new FitImageLoadHandler() {

			@Override
			public void imageLoaded(FitImageLoadEvent event) {
				getImagePanel().remove(loaderImage);
				getImagePanel().add(event.getFitImage());
			}
		});
		fitImage.setTitle(pictureDto.getName());

	}

	private void loadPictures(final String albumName) {
		viewerService.getPictures(albumName, new AsyncCallback<AlbumDto>() {

			@Override
			public void onSuccess(AlbumDto albumDto) {
				setAlbumDto(albumDto);
				getImagesList().setRowCount(albumDto.getPictures().size(),true);
				getImagesList().setRowData(0,albumDto.getPictures());

			}

			@Override
			public void onFailure(Throwable ex) {
				MessageDialogUtil.getErrorDialogBox("Error loading Pictures");

			}
		});
	}

	private void unselectPicture() {
		PictureDto selectedObject = getSelectionModelPictures().getSelectedObject();
		if(selectedObject!=null){
			getSelectionModelPictures().setSelected(selectedObject,false);
		}
	}


	private AlbumDto getAlbumDto() {
		return albumDto;
	}


	private void setAlbumDto(AlbumDto albumDto) {
		this.albumDto = albumDto;
	}


	private SingleSelectionModel<PictureDto> getSelectionModelPictures() {
		return (SingleSelectionModel<PictureDto>)getImagesList().getSelectionModel();
	}

	@Override
	protected void update() {
		loadPictures(albumDto.getName());
		getDataProvider().getList().clear();
		getResults(asyncCallbackList);
	}

	private final class PictureCell extends AbstractCell<PictureDto>{

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				PictureDto picture, SafeHtmlBuilder sb) {
			if (picture == null) {
				return;
			}
			sb.appendHtmlConstant("<table>");

			// Add  image.
			sb.appendHtmlConstant("<tr><td >");
			sb.appendHtmlConstant(picture.getName());
			sb.appendHtmlConstant("</td></tr></table>");
		}

	}

	private final class SelectionPictureHandler implements
	SelectionChangeEvent.Handler {
		public void onSelectionChange(SelectionChangeEvent event) {
			addPictureToPanel(getSelectionModelPictures().getSelectedObject());
		}
	}

	private final class GetPicturesCallback implements Handler {
		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			final String albumName = getSelectedItem();
			unselectPicture();
			getImagePanel().clear();
			if(albumName!=null){
				getUploadPicturerItem().setEnabled(true);
				loadPictures(albumName);
			}
			else{
				getUploadPicturerItem().setEnabled(false);
				getImagesList().setRowCount(0,true);
				getImagesList().setRowData(0,new ArrayList<PictureDto>());
			}
		}


	}
}
