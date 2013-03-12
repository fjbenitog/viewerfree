package es.viewerfree.gwt.client.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
import es.viewerfree.gwt.client.common.ConfirmDialogBox;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

public class UserActionPanel  extends ActionPanel<UserDto>{
	
	private Button createUserButton;
	
	private MenuItem modifyUserItem;
	
	private MenuItem deleteUsersItem;
	
	private Button refreshButton;
	
	private static final UserServiceAsync userService = GWT.create(UserService.class);
	
	public UserActionPanel() {
		super();
		getButtonsPanel().add(getCreateUserButton());
		getButtonsPanel().add(getActionsMenu());
		getMenuBarActions().addItem(getModifyUserItem());
		getMenuBarActions().addItem(getDeleteUsersItem());
		getButtonsPanel().add(getRefreshButton());
	}

	private Button getCreateUserButton(){
		if(this.createUserButton == null){
			this.createUserButton = new Button(messages.createUser());
			this.createUserButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					newUserForm(UserAction.CREATE);
				}
			});
		}
		return this.createUserButton;
	}
	
	private void newUserForm(String userName,UserAction userAction){
		UserForm userForm = new UserForm(userName,userAction);
		userForm.setRefreshWidgetListener(new RefreshPanelListener());
		userForm.setAnimationEnabled(true);
		userForm.setGlassEnabled(true);
	}
	
	private void newUserForm(UserAction userAction){
		newUserForm(null,userAction);
	}
	
	
	private MenuItem getModifyUserItem(){
		if(this.modifyUserItem==null){
			this.modifyUserItem = new MenuItem(messages.modifyUser(), new Command() {

				@Override
				public void execute() {
					newUserForm(getSelectedItem().getName(),UserAction.MODIFY);
				}
			});
			this.modifyUserItem.setEnabled(false);
		}
		return this.modifyUserItem;
	}
	
	private MenuItem getDeleteUsersItem(){
		if(this.deleteUsersItem==null){
			deleteUsersItem = new MenuItem(messages.deleteUsers(), new Command() {

				@Override
				public void execute() {
					final ConfirmDialogBox confirmDialogBox = new ConfirmDialogBox(messages.confirmDeleteUserMessage());
					confirmDialogBox.addAcceptClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent arg0) {
							userService.delete(getSelectedUsers(), new AsyncCallback<Void>() {
								
								@Override
								public void onSuccess(Void ex) {
									confirmDialogBox.hide();
									getModifyUserItem().setEnabled(false);
									getDeleteUsersItem().setEnabled(false);
									update();
								}
								
								@Override
								public void onFailure(Throwable ex) {
									confirmDialogBox.hide();
									MessageDialogUtil.getErrorDialogBox("Error deleting the users");
								}
							});
							
						}
					});
					confirmDialogBox.invoke();
				}
			});
			this.deleteUsersItem.setEnabled(false);
		}
		return this.deleteUsersItem;
	}
	
	private Button getRefreshButton(){
		if(this.refreshButton == null){
			this.refreshButton = new Button(messages.refresh());
			this.refreshButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					update();

				}
			});
		}
		return this.refreshButton;
	}
	

	
	protected void addColumns(ListHandler<UserDto> columnSortHandler) {

		final SelectionModel<UserDto> selectionModel = new MultiSelectionModel<UserDto>(
				new ProvidesKey<UserDto>() {
					@Override
					public Object getKey(UserDto dto) {
						return dto.getId();
					}
				});
		getTable().setSelectionModel(selectionModel,
				DefaultSelectionEventManager.<UserDto> createCheckboxManager());

		Column<UserDto, Boolean> checkColumn = new Column<UserDto, Boolean>(
				new CheckboxCell(true, true)) {
			@Override
			public Boolean getValue(UserDto object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<UserDto, Boolean>() {
			
			@Override
			public void update(int index, UserDto dto, Boolean value) {
				getTable().getSelectionModel().setSelected(dto, value);
				int count = 0;
				List<UserDto> items = getDataProvider().getList();
				for (UserDto userDto : items) {
					if(getTable().getSelectionModel().isSelected(userDto)){
						count++;
					}
				}
				getModifyUserItem().setEnabled(count==1);
				getDeleteUsersItem().setEnabled(count > 0);
			}
		});
		getTable().addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		getTable().setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getName();
			}
		};
		nameColumn.setSortable(true);
		getTable().addColumn(nameColumn,messages.user());
		getTable().setColumnWidth(nameColumn, "20%");

		TextColumn<UserDto> fullNameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getFullName();
			}
		};
		fullNameColumn.setSortable(true);
		getTable().addColumn(fullNameColumn,messages.name());
		getTable().setColumnWidth(fullNameColumn, "30%");

		TextColumn<UserDto> surnameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getSurname();
			}
		};
		surnameColumn.setSortable(true);
		getTable().addColumn(surnameColumn,messages.surname());
		getTable().setColumnWidth(surnameColumn, "30%");

		TextColumn<UserDto> emailColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getEmail();
			}
		};
		emailColumn.setSortable(true);
		getTable().addColumn(emailColumn,messages.email());
		getTable().setColumnWidth(emailColumn, "20%");

		TextColumn<UserDto> profileColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getProfile().toString();
			}
		};
		profileColumn.setSortable(true);
		getTable().addColumn(profileColumn,messages.profile());
		getTable().setColumnWidth(profileColumn, "5%");

		columnSortHandler.setComparator(nameColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				return stringComparator.compare(o1.getName(),o2.getName());
			}
		});
		columnSortHandler.setComparator(fullNameColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				return stringComparator.compare(o1.getFullName(),o2.getFullName());
			}
		});
		columnSortHandler.setComparator(surnameColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				return stringComparator.compare(o1.getSurname(),o2.getSurname());
			}
		});
		columnSortHandler.setComparator(emailColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				return stringComparator.compare(o1.getEmail(),o2.getEmail());
			}
		});
		columnSortHandler.setComparator(profileColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				return stringComparator.compare(o1.getProfile().toString(),o2.getProfile().toString());
			}
		});

		// We know that the data is sorted alphabetically by default.
		getTable().getColumnSortList().push(fullNameColumn);
	}
	

	private UserDto getSelectedItem(){
		MultiSelectionModel<UserDto> selectionModel= (MultiSelectionModel<UserDto>)getTable().getSelectionModel();
		return selectionModel.getSelectedSet().iterator().next();
	}
	
	private List<String> getSelectedUsers(){
		MultiSelectionModel<UserDto> selectionModel= (MultiSelectionModel<UserDto>)getTable().getSelectionModel();
		List<String> users = new ArrayList<String>();
		for (Iterator<UserDto> iterator = selectionModel.getSelectedSet().iterator(); iterator.hasNext();) {
			users.add(iterator.next().getName());
		}
		return users;
	}

	@Override
	protected void getResults(AsyncCallback<List<UserDto>> asyncCallback) {
		userService.getUsers(asyncCallback);
	}

	@Override
	protected void update(){
		((MultiSelectionModel<UserDto>)getTable().getSelectionModel()).clear();
		getDataProvider().getList().clear();
		getResults(asyncCallbackList);
		getTable().getColumnSortList().push(getTable().getColumn(0));
	}


	

}
