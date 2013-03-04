package es.viewerfree.gwt.client.admin.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.util.ErrorMessageUtil;

public abstract class ActionPanel<T>  extends LayoutPanel {

	protected final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private HorizontalPanel buttonsPanel;
	
	private ScrollPanel tableScrollPanel;
	
	private VerticalPanel tablePanel;
	
	private SimplePager pager;
	
	private CellTable<T> table;
	
	private ListDataProvider<T> dataProvider;
	
	private MenuBar actionsMenu;
	
	private MenuBar menuBarActions;
	
	protected AsyncCallbackList asyncCallbackList= new AsyncCallbackList();
	
	public ActionPanel() {
		add(getButtonsPanel());
		setWidgetTopHeight(getButtonsPanel(), 20, Unit.PX, 40, Unit.PX);
		setWidgetLeftWidth(getButtonsPanel(), 25, Unit.PX, 100, Unit.PCT);
		add(getTableScrollPanel());
		setWidgetTopBottom(getTableScrollPanel(), 70, Unit.PX, 10, Unit.PX);
		setWidgetLeftRight(getTableScrollPanel(), 25, Unit.PX, 25, Unit.PX);

	}

	protected HorizontalPanel getButtonsPanel(){
		if(this.buttonsPanel==null){
			this.buttonsPanel = new HorizontalPanel();
			this.buttonsPanel.setSpacing(10);
		}
		return this.buttonsPanel;
	}
	
	protected MenuBar getActionsMenu(){
		if(this.actionsMenu == null){
			this.actionsMenu = new MenuBar();
			this.actionsMenu.addItem(new MenuItem( messages.moreActions(), true, getMenuBarActions()));
		}
		return this.actionsMenu;
	}
	
	protected MenuBar getMenuBarActions(){
		if(this.menuBarActions == null){
			this.menuBarActions = new MenuBar(true);
		}
		return this.menuBarActions;
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
			this.tablePanel.add(getTable());
			this.tablePanel.setCellHorizontalAlignment(getTable(), HorizontalPanel.ALIGN_CENTER);
			this.tablePanel.setCellWidth(getTable(), "100%");
			this.tablePanel.add(getPager());
			this.tablePanel.setCellHorizontalAlignment(getPager(), HorizontalPanel.ALIGN_CENTER);
		}
		return this.tablePanel;
	}
	
	private SimplePager getPager(){
		if(this.pager==null){
			SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			this.pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			this.pager.setDisplay(getTable());
			this.pager.setPageSize(15);
		}
		return this.pager;
	}
	
	protected  CellTable<T> getTable(){
		if(this.table==null){
			this.table = new CellTable<T>();
			this.table.setStyleName("cellTable");
			this.table.setWidth("100%");
			getResults(asyncCallbackList);
			// Connect the table to the data provider.
			getDataProvider().addDataDisplay(this.table);

			List<T> list = getDataProvider().getList();

			addColumns(list);
		}
		return this.table;
	}
	
	public void refresh(){
		getDataProvider().getList().clear();
		getResults(asyncCallbackList);
		getTable().getColumnSortList().push(getTable().getColumn(0));
	}

	protected ListDataProvider<T> getDataProvider(){
		if(this.dataProvider == null){
			this.dataProvider = new ListDataProvider<T>();
		}
		return this.dataProvider;
	}
	
	private void addColumns(List<T> list) {
		ListHandler<T> columnSortHandler = new ListHandler<T>(list);
		getTable().addColumnSortHandler(columnSortHandler);
		addColumns(columnSortHandler);
	}
	
	public T getSelectedItem(){
		MultiSelectionModel<T> selectionModel= (MultiSelectionModel<T>)getTable().getSelectionModel();
		return selectionModel.getSelectedSet().iterator().next();
	}
	protected abstract void addColumns(ListHandler<T> columnSortHandler) ;

	protected abstract void getResults(AsyncCallback<List<T>> asyncCallbackList);
	
	protected final class AsyncCallbackList implements AsyncCallback<List<T>>{

		@Override
		public void onFailure(Throwable e) {
			ErrorMessageUtil.getErrorDialogBox("Error loading values");
		}

		@Override
		public void onSuccess(List<T> results) {
			getDataProvider().getList().addAll(results);
		}
		
	}
}
