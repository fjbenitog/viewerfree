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
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
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
	
	private void newUserForm(UserAction userAction){
		UserForm createUserForm = new UserForm(this,userAction);
		createUserForm.setAnimationEnabled(true);
		createUserForm.setGlassEnabled(true);
	}
	
	
	private MenuItem getModifyUserItem(){
		if(this.modifyUserItem==null){
			this.modifyUserItem = new MenuItem(messages.modifyUser(), new Command() {

				@Override
				public void execute() {
					newUserForm(UserAction.MODIFY);
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
					userService.delete(getSelectedUsers(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void ex) {
							refresh();
						}
						
						@Override
						public void onFailure(Throwable ex) {
							ErrorMessageUtil.getErrorDialogBox("Error deleting the users");
						}
					});
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
					refresh();

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
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getName().compareTo(o2.getName()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(fullNameColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getFullName().compareTo(o2.getFullName()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(surnameColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getSurname().compareTo(o2.getSurname()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(emailColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null && o1.getEmail()!=null) {
					return (o2 != null && o2.getEmail()!=null) ? o1.getEmail().compareTo(o2.getEmail()) : 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(profileColumn,
				new Comparator<UserDto>() {
			public int compare(UserDto o1, UserDto o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getProfile().compareTo(o2.getProfile()) : 1;
				}
				return -1;
			}
		});

		// We know that the data is sorted alphabetically by default.
		getTable().getColumnSortList().push(fullNameColumn);
	}
	

	
	public List<String> getSelectedUsers(){
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


	

}
