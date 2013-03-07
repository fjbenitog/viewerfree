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
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.RefreshWidgetListener;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.client.util.StringComparator;

public abstract class ActionPanel<T>  extends LayoutPanel {

	private static final int PAGE_SIZE = 15;


	protected final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);

	
	private HorizontalPanel buttonsPanel;
	
	private ScrollPanel tableScrollPanel;
	
	private HorizontalPanel pagerPanel;
	
	private SimplePager pager;
	
	private CellTable<T> table;
	
	private ListDataProvider<T> dataProvider;
	
	private MenuBar actionsMenu;
	
	private MenuBar menuBarActions;
	
	protected AsyncCallbackList asyncCallbackList= new AsyncCallbackList();
	
	private LayoutPanel mainPanel;

	protected StringComparator stringComparator = new StringComparator();
	
	public ActionPanel() {
		add(getMainPanel());
	}
	
	protected LayoutPanel getMainPanel(){
		if(this.mainPanel == null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.add(getButtonsPanel());
			this.mainPanel.setWidgetTopHeight(getButtonsPanel(), 20, Unit.PX, 40, Unit.PCT);
			this.mainPanel.setWidgetLeftWidth(getButtonsPanel(), 25, Unit.PX, 100, Unit.PCT);
			this.mainPanel.add(getTableScrollPanel());
			this.mainPanel.setWidgetTopBottom(getTableScrollPanel(), 70, Unit.PX, 55, Unit.PX);
			this.mainPanel.setWidgetLeftRight(getTableScrollPanel(), 25, Unit.PX, 25, Unit.PX);
			this.mainPanel.add(getPagerPanel());
			this.mainPanel.setWidgetBottomHeight(getPagerPanel(),25,Unit.PX,25,Unit.PX);
		}
		return this.mainPanel;
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
	
	
	protected ScrollPanel getTableScrollPanel(){
		if(this.tableScrollPanel == null){
			this.tableScrollPanel = new ScrollPanel();
			this.tableScrollPanel.add(getTable());
		}
		return this.tableScrollPanel;
	}
	
	protected HorizontalPanel getPagerPanel(){
		if(this.pagerPanel == null){
			this.pagerPanel = new HorizontalPanel();
			this.pagerPanel.setWidth("100%");
			this.pagerPanel.add(getPager());
			this.pagerPanel.setCellHorizontalAlignment(getPager(), HorizontalPanel.ALIGN_CENTER);
		}
		return this.pagerPanel;
	}
	
	protected SimplePager getPager(){
		if(this.pager==null){
			SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			this.pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			this.pager.setDisplay(getTable());
			this.pager.setPageSize(PAGE_SIZE);
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
	

	protected abstract void addColumns(ListHandler<T> columnSortHandler) ;

	protected abstract void getResults(AsyncCallback<List<T>> asyncCallbackList);
	
	protected abstract void update();
	protected final class AsyncCallbackList implements AsyncCallback<List<T>>{

		@Override
		public void onFailure(Throwable e) {
			MessageDialogUtil.getErrorDialogBox("Error loading values");
		}

		@Override
		public void onSuccess(List<T> results) {
			getDataProvider().getList().addAll(results);
		}
		
	}
	
	protected class RefreshPanelListener implements RefreshWidgetListener{

		public RefreshPanelListener() {
		}

		@Override
		public void refresh() {
			update();
		}

		
	}
}
