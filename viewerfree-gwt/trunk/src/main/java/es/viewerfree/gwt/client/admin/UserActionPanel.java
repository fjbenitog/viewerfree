package es.viewerfree.gwt.client.admin;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

public class UserActionPanel  extends LayoutPanel implements AsyncCallback<List<UserDto>>{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private HorizontalPanel buttonsPanel;
	
	private Button createUserButton;
	
	private MenuBar actionsMenu;
	
	private MenuItem modifyUserItem;
	
	private MenuItem deleteUsersItem;
	
	private Button refreshButton;
	
	private ListDataProvider<UserDto> dataProvider;
	
	private CellTable<UserDto> userTable;
	
	private ScrollPanel tableScrollPanel;
	
	private VerticalPanel tablePanel;
	
	private SimplePager pager;
	
	public UserActionPanel() {
		add(getButtonsPanel());
		setWidgetTopHeight(getButtonsPanel(), 20, Unit.PX, 40, Unit.PX);
		setWidgetLeftWidth(getButtonsPanel(), 25, Unit.PX, 100, Unit.PCT);
		add(getTableScrollPanel());
		setWidgetTopBottom(getTableScrollPanel(), 70, Unit.PX, 10, Unit.PX);
		setWidgetLeftRight(getTableScrollPanel(), 25, Unit.PX, 25, Unit.PX);
	}

	private HorizontalPanel getButtonsPanel(){
		if(this.buttonsPanel==null){
			this.buttonsPanel = new HorizontalPanel();
			this.buttonsPanel.setSpacing(10);
			this.buttonsPanel.add(getCreateUserButton());
			this.buttonsPanel.add(getActionsMenu());
			this.buttonsPanel.add(getRefreshButton());
		}
		return this.buttonsPanel;
	}
	
	private Button getCreateUserButton(){
		if(this.createUserButton == null){
			this.createUserButton = new Button(messages.createUser());
			this.createUserButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					CreateUserForm createUserForm = newCreateUserForm();
					createUserForm.show();
					createUserForm.center();

				}
			});
		}
		return this.createUserButton;
	}
	
	private CreateUserForm newCreateUserForm(){
		CreateUserForm createUserForm = new CreateUserForm(this);
		createUserForm.setAnimationEnabled(true);
		createUserForm.setGlassEnabled(true);
		return createUserForm;
	}
	
	private MenuBar getActionsMenu(){
		if(this.actionsMenu == null){
			this.actionsMenu = new MenuBar();
			MenuBar actions = new MenuBar(true);
			this.actionsMenu.addItem(new MenuItem( messages.moreActions(), true, actions));
			
			actions.addItem(getModifyUserItem());
			actions.addItem(getDeleteUsersItem());
		}
		return this.actionsMenu;
	}
	
	private MenuItem getModifyUserItem(){
		if(this.modifyUserItem==null){
			this.modifyUserItem = new MenuItem(messages.modifyUser(), new Command() {

				@Override
				public void execute() {
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
	
	private ListDataProvider<UserDto> getDataProvider(){
		if(this.dataProvider == null){
			this.dataProvider = new ListDataProvider<UserDto>();
		}
		return this.dataProvider;
	}
	
	private CellTable<UserDto> getUserTable(){
		if(this.userTable==null){
			this.userTable = new CellTable<UserDto>();
			this.userTable.setStyleName("cellTable");
			userService.getUsers(this);
			// Connect the table to the data provider.
			getDataProvider().addDataDisplay(this.userTable);

			List<UserDto> list = getDataProvider().getList();

			addColumns(list);
		}
		return this.userTable;
	}
	
	private void addColumns(List<UserDto> list) {
		// Add a ColumnSortEvent.ListHandler to connect sorting to the
		// java.util.List.
		ListHandler<UserDto> columnSortHandler = new ListHandler<UserDto>(
				list);
		getUserTable().addColumnSortHandler(columnSortHandler);

		final SelectionModel<UserDto> selectionModel = new MultiSelectionModel<UserDto>(
				new ProvidesKey<UserDto>() {
					@Override
					public Object getKey(UserDto dto) {
						return dto.getId();
					}
				});
		getUserTable().setSelectionModel(selectionModel,
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
				getUserTable().getSelectionModel().setSelected(dto, value);
				int count = 0;
				List<UserDto> items = getDataProvider().getList();
				for (UserDto userDto : items) {
					if(getUserTable().getSelectionModel().isSelected(userDto)){
						count++;
					}
				}
				getModifyUserItem().setEnabled(count==1);
				getDeleteUsersItem().setEnabled(count > 0);
			}
		});
		getUserTable().addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		getUserTable().setColumnWidth(checkColumn, 40, Unit.PX);

		TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getName();
			}
		};
		nameColumn.setSortable(true);
		getUserTable().addColumn(nameColumn,messages.user());
		getUserTable().setColumnWidth(nameColumn, "20%");

		TextColumn<UserDto> fullNameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getFullName();
			}
		};
		fullNameColumn.setSortable(true);
		getUserTable().addColumn(fullNameColumn,messages.name());
		getUserTable().setColumnWidth(fullNameColumn, "30%");

		TextColumn<UserDto> surnameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getSurname();
			}
		};
		surnameColumn.setSortable(true);
		getUserTable().addColumn(surnameColumn,messages.surname());
		getUserTable().setColumnWidth(surnameColumn, "30%");

		TextColumn<UserDto> emailColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getEmail();
			}
		};
		emailColumn.setSortable(true);
		getUserTable().addColumn(emailColumn,messages.email());
		getUserTable().setColumnWidth(emailColumn, "20%");

		TextColumn<UserDto> profileColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getProfile().toString();
			}
		};
		profileColumn.setSortable(true);
		getUserTable().addColumn(profileColumn,messages.profile());
		getUserTable().setColumnWidth(profileColumn, "5%");

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
		getUserTable().getColumnSortList().push(fullNameColumn);
	}
	
	@Override
	public void onFailure(Throwable ex) {
		ErrorMessageUtil.getErrorDialogBox("Error loading the users");
	}

	@Override
	public void onSuccess(List<UserDto> users) {
		getDataProvider().getList().addAll(users);
	}
	
	public void refresh(){
		getDataProvider().getList().clear();
		userService.getUsers(this);
		getUserTable().getColumnSortList().push(getUserTable().getColumn(0));
	}
	
	private ScrollPanel getTableScrollPanel(){
		if(this.tableScrollPanel == null){
			this.tableScrollPanel = new ScrollPanel();
			this.tableScrollPanel.add(getTablePanel());
		}
		return this.tableScrollPanel;
	}
	
	private VerticalPanel getTablePanel(){
		if(this.tablePanel==null){
			this.tablePanel = new VerticalPanel();
			this.tablePanel.setWidth("100%");
			this.tablePanel.add(getUserTable());
			this.tablePanel.setCellHorizontalAlignment(getUserTable(), HorizontalPanel.ALIGN_CENTER);
			this.tablePanel.add(getPager());
			this.tablePanel.setCellHorizontalAlignment(getPager(), HorizontalPanel.ALIGN_CENTER);
		}
		return this.tablePanel;
	}
	
	private SimplePager getPager(){
		if(this.pager==null){
			SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			this.pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			this.pager.setDisplay(getUserTable());
			this.pager.setPageSize(15);
		}
		return this.pager;
	}
}
