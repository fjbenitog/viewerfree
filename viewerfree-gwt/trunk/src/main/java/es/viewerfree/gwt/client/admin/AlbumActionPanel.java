package es.viewerfree.gwt.client.admin;

import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

import es.viewerfree.gwt.client.admin.ui.ActionPanel;
import es.viewerfree.gwt.client.common.UploadPicForm;
import es.viewerfree.gwt.client.service.ViewerService;
import es.viewerfree.gwt.client.service.ViewerServiceAsync;

public class AlbumActionPanel extends ActionPanel<String>{
	
	private static final ViewerServiceAsync viewerService = GWT.create(ViewerService.class);
	
	private Button createAlbumButton;
	
	private MenuItem uploadPicturerItem;
	

	
	public AlbumActionPanel() {
		super();
		getButtonsPanel().add(getCreateAlbumButton());
		getButtonsPanel().add(getActionsMenu());
		getMenuBarActions().addItem(getUploadPicturerItem());
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
	
	private void newAlbumForm(){
		AlbumForm createAlbumForm = new AlbumForm(this);
		createAlbumForm.setAnimationEnabled(true);
		createAlbumForm.setGlassEnabled(true);
	}
	
	private void uploadPicForm(String albumName){
		UploadPicForm UploadPicForm = new UploadPicForm(albumName);
		UploadPicForm.setAnimationEnabled(true);
		UploadPicForm.setGlassEnabled(true);
	}

	
	@Override
	protected void addColumns(ListHandler<String> columnSortHandler) {
		final SelectionModel<String> selectionModel = new MultiSelectionModel<String>(
				new ProvidesKey<String>() {
					@Override
					public Object getKey(String name) {
						return name;
					}
				});
		getTable().setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<String> createCheckboxManager());

		Column<String, Boolean> checkColumn = new Column<String, Boolean>(
				new CheckboxCell(true, true)) {
			@Override
			public Boolean getValue(String object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<String, Boolean>() {
			
			@Override
			public void update(int index, String name, Boolean value) {
				getTable().getSelectionModel().setSelected(name, value);
				int count = 0;
				List<String> items = getDataProvider().getList();
				for (String albums : items) {
					if(getTable().getSelectionModel().isSelected(albums)){
						count++;
					}
				}
				getUploadPicturerItem().setEnabled(count==1);

			}
		});
		getTable().addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		getTable().setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<String> nameColumn = new TextColumn<String>() {

			@Override
			public String getValue(String name) {
				return name;
			}
		};
		nameColumn.setSortable(true);
		getTable().addColumn(nameColumn,messages.albumLabel());
		getTable().setColumnWidth(nameColumn, "100%");
	}

	@Override
	protected void getResults(AsyncCallback<List<String>> asyncCallbackList) {
		viewerService.getAlbums(asyncCallbackList);
		
	}

}
