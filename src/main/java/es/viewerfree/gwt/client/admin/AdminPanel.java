package es.viewerfree.gwt.client.admin;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.UserService;
import es.viewerfree.gwt.client.service.UserServiceAsync;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;
import es.viewerfree.gwt.shared.dto.UserDto;

public class AdminPanel extends LayoutPanel implements AsyncCallback<List>{

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private LayoutPanel adminTitlePanel;

	private Label adminTitleLabel;

	private TabLayoutPanel adminTabs;

	private LayoutPanel userActionPanel;

	private Button createUserButton;

	private CellTable<UserDto> userTable;

	private SimplePager pager;

	private ScrollPanel tableScrollPanel;

	private VerticalPanel tablePanel;

	private VerticalPanel buttonsPanel;

	ListDataProvider<UserDto> dataProvider;

	public AdminPanel() {
		setStyleName("adminContent");
		add(getAdminTitlePanel());
		setWidgetTopHeight(getAdminTitlePanel(), 0, Unit.PX, 70, Unit.PX);
		add(getAdminTabs());
		setWidgetTopBottom(getAdminTabs(), 70, Unit.PX, 50, Unit.PX);
		setWidgetLeftRight(getAdminTabs(), 50, Unit.PX, 50, Unit.PX);
	}

	private LayoutPanel getAdminTitlePanel(){
		if(this.adminTitlePanel == null){
			this.adminTitlePanel = new LayoutPanel();
			this.adminTitlePanel.add(getAdminTitleLabel());
			this.adminTitlePanel.setWidgetLeftRight(getAdminTitleLabel(), 50, Unit.PX, 100, Unit.PX);
			this.adminTitlePanel.setWidgetTopBottom(getAdminTitleLabel(), 2, Unit.PX, 15, Unit.PX);
			this.adminTitlePanel.setStyleName("titlePanel");
		}
		return this.adminTitlePanel;
	}

	private Label getAdminTitleLabel(){
		if(this.adminTitleLabel == null){
			this.adminTitleLabel = new HTML(messages.adminLabel());
			this.adminTitleLabel.setStyleName("titleLabel");
		}
		return this.adminTitleLabel;
	}

	private TabLayoutPanel getAdminTabs(){
		if(this.adminTabs == null){
			this.adminTabs = new TabLayoutPanel(30, Unit.PX);
			this.adminTabs.add(getUserActionPanel(), messages.users(), true);
			this.adminTabs.add(new HTML("CREAR GRUPOS"), messages.groups(), true);
		}		return this.adminTabs;
	}


	private CreateUserForm newCreateUserForm(){
		CreateUserForm createUserForm = new CreateUserForm(this);
		createUserForm.setAnimationEnabled(true);
		createUserForm.setGlassEnabled(true);
		return createUserForm;
	}

	private LayoutPanel getUserActionPanel(){
		if(this.userActionPanel == null){
			this.userActionPanel = new LayoutPanel();
			this.userActionPanel.add(getButtonsPanel());
			this.userActionPanel.setWidgetTopHeight(getButtonsPanel(), 20, Unit.PX, 30, Unit.PX);
			this.userActionPanel.setWidgetLeftWidth(getButtonsPanel(), 25, Unit.PX, 100, Unit.PCT);
			this.userActionPanel.add(getTableScrollPanel());
			this.userActionPanel.setWidgetTopBottom(getTableScrollPanel(), 70, Unit.PX, 10, Unit.PX);
			this.userActionPanel.setWidgetLeftRight(getTableScrollPanel(), 25, Unit.PX, 25, Unit.PX);
		}
		return this.userActionPanel;
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

//	private void addColumns(List<UserDto> list){
//		ListHandler<UserDto> columnSortHandler = new ListHandler<UserDto>(
//				list);
//		getUserTable().addColumnSortHandler(columnSortHandler);
//		Method[] methods = UserDto.class.getDeclaredMethods();
//		for (final Method method : methods) {
//			if(method.getName().startsWith("get")){
//				TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {
//
//					@Override
//					public String getValue(UserDto userDto) {
//						try{
//							return (String) method.invoke(userDto);
//						}catch (Exception e) {
//							return "N/A";
//						}
//					}
//				};
//				nameColumn.setSortable(true);
//				getUserTable().addColumn(nameColumn,method.getName().substring(3));
//				
//				columnSortHandler.setComparator(nameColumn,
//						new Comparator<UserDto>() {
//					public int compare(UserDto o1, UserDto o2) {
//						if (o1 == o2) {
//							return 0;
//						}
//
//						// Compare the name columns.
//						if (o1 != null) {
//							String value1="";
//							String value2="";
//							try {
//								value1 = (String) method.invoke(o1);
//								value2 = (String) method.invoke(o1);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							return (o2 != null) ? value1.compareTo(value2) : 1;
//						}
//						return -1;
//					}
//				});
//			}
//
//		}
//		getUserTable().getColumnSortList().push(getUserTable().getColumn(0));
//	}

	private void addColumns(List<UserDto> list) {
		// Add a ColumnSortEvent.ListHandler to connect sorting to the
		// java.util.List.
		ListHandler<UserDto> columnSortHandler = new ListHandler<UserDto>(
				list);
		getUserTable().addColumnSortHandler(columnSortHandler);

		TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getName();
			}
		};
		nameColumn.setSortable(true);
		getUserTable().addColumn(nameColumn,"User");
		getUserTable().setColumnWidth(nameColumn, "20%");
		
		TextColumn<UserDto> fullNameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getFullName();
			}
		};
		fullNameColumn.setSortable(true);
		getUserTable().addColumn(fullNameColumn,"Name");
		getUserTable().setColumnWidth(fullNameColumn, "30%");

		TextColumn<UserDto> surnameColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getSurname();
			}
		};
		surnameColumn.setSortable(true);
		getUserTable().addColumn(surnameColumn,"Surname");
		getUserTable().setColumnWidth(surnameColumn, "30%");
		
		TextColumn<UserDto> emailColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getEmail();
			}
		};
		emailColumn.setSortable(true);
		getUserTable().addColumn(emailColumn,"Email");
		getUserTable().setColumnWidth(emailColumn, "20%");

		TextColumn<UserDto> profileColumn = new TextColumn<UserDto>() {

			@Override
			public String getValue(UserDto userDto) {
				return userDto.getProfile().toString();
			}
		};
		profileColumn.setSortable(true);
		getUserTable().addColumn(profileColumn,"Profile");
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

	private VerticalPanel getButtonsPanel(){
		if(this.buttonsPanel==null){
			this.buttonsPanel = new VerticalPanel();
			this.buttonsPanel.add(getCreateUserButton());
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

	private ListDataProvider<UserDto> getDataProvider(){
		if(this.dataProvider == null){
			this.dataProvider = new ListDataProvider<UserDto>();
		}
		return this.dataProvider;
	}

	@Override
	public void onFailure(Throwable ex) {
		ErrorMessageUtil.getErrorDialogBox("Error loading the users");
	}

	@Override
	public void onSuccess(List users) {
		getDataProvider().getList().addAll(users);
	}


	public void refresh(){
		getDataProvider().getList().clear();
		userService.getUsers(this);
		getUserTable().getColumnSortList().push(getUserTable().getColumn(0));
	}
}
