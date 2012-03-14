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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.shared.dto.UserDto;

public class AdminPanel extends LayoutPanel {

	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	private LayoutPanel adminTitlePanel;

	private Label adminTitleLabel;

	private TabLayoutPanel adminTabs;

	private LayoutPanel userActionPanel;

	private Button createUserButton;

	private CellTable<UserDto> userTable;
	
	private SimplePager pager;
	
	private ScrollPanel tableScrollPanel;
	
	private VerticalPanel tablePanel;

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
		CreateUserForm createUserForm = new CreateUserForm();
		createUserForm.setAnimationEnabled(true);
		createUserForm.setGlassEnabled(true);
		return createUserForm;
	}
	
	private LayoutPanel getUserActionPanel(){
		if(this.userActionPanel == null){
			this.userActionPanel = new LayoutPanel();
			this.userActionPanel.add(getCreateUserButton());
			this.userActionPanel.setWidgetTopHeight(getCreateUserButton(), 20, Unit.PX, 30, Unit.PX);
			this.userActionPanel.setWidgetLeftWidth(getCreateUserButton(), 25, Unit.PX, 100, Unit.PCT);
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
			TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {

				@Override
				public String getValue(UserDto userDto) {
					return userDto.getName();
				}
			};
			nameColumn.setSortable(true);
			TextColumn<UserDto> fullNameColumn = new TextColumn<UserDto>() {

				@Override
				public String getValue(UserDto userDto) {
					return userDto.getFullName();
				}
			};
			fullNameColumn.setSortable(true);
			this.userTable.addColumn(nameColumn,"User Name");
			this.userTable.addColumn(fullNameColumn,"Full Name");
			
			ListDataProvider<UserDto> dataProvider = new ListDataProvider<UserDto>();

		    // Connect the table to the data provider.
		    dataProvider.addDataDisplay(this.userTable);
		    
		    List<UserDto> list = dataProvider.getList();
		    for (int i = 0; i < 100; i++) {
		    	UserDto userDto = new UserDto();
		    	userDto.setName(i+" Name");
		    	userDto.setFullName(i+" FullName");
		    	list.add(userDto);
				
			}
		    
		    // Add a ColumnSortEvent.ListHandler to connect sorting to the
		    // java.util.List.
		    ListHandler<UserDto> columnSortHandler = new ListHandler<UserDto>(
		        list);
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
		    this.userTable.addColumnSortHandler(columnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    this.userTable.getColumnSortList().push(nameColumn);
		}
		return this.userTable;
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

}
