package es.viewerfree.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import es.viewerfree.gwt.shared.DateUtil;

public class Login implements EntryPoint {

	private TextBox userLogin;
	private PasswordTextBox userPassword;
	private Button sendButton;
	private DockPanel mainPanel;
	private HorizontalPanel headerPanel;
	private HorizontalPanel titlePanel;
	private HorizontalPanel userPanel;
	private Label titleViewerfree;
	private Label titleBy;
	private Label dateLabel;
	private HorizontalPanel languagePanel;
	private HorizontalPanel footerPanel;
	private HorizontalPanel contentPanel;
	private HorizontalPanel linksPanel;


	public void onModuleLoad() {
		RootPanel.get().add(getMainPanel());
		getUserLogin().setFocus(true);
	}

	private PasswordTextBox getUserPassword() {
		if(this.userPassword==null){
			this.userPassword = new PasswordTextBox();
		}
		return userPassword;
	}
	
	private TextBox getUserLogin(){
		if(this.userLogin==null){
			this.userLogin = new TextBox();
		}
		return this.userLogin;
	}
	
	private Button getSendButton(){
		if(this.sendButton==null){
			this.sendButton = new Button("Aceptar");
		}
		return this.sendButton;
	}
	
	private DockPanel getMainPanel(){
		if(this.mainPanel==null){
			this.mainPanel = new DockPanel();
			this.mainPanel.setWidth("100%");
			this.mainPanel.add(getHeaderPanel(), DockPanel.NORTH);
			this.mainPanel.add(getFooterPanel(), DockPanel.SOUTH);
			this.mainPanel.add(getContentPanel(), DockPanel.SOUTH);

		}
		return this.mainPanel;
	}
	
	private HorizontalPanel getHeaderPanel(){
		if(this.headerPanel==null){
			this.headerPanel = new HorizontalPanel();
			this.headerPanel.addStyleName("headerPanel");
			this.headerPanel.setSpacing(20);
			this.headerPanel.add(getTitlePanel());
			this.headerPanel.setCellWidth(getTitlePanel(), "55%");
			this.headerPanel.add(getUserPanel());
			this.headerPanel.setCellHorizontalAlignment(getUserPanel(), HorizontalPanel.ALIGN_RIGHT);
			this.headerPanel.setCellVerticalAlignment(getUserPanel(), HorizontalPanel.ALIGN_MIDDLE);
			this.headerPanel.setCellWidth(getUserPanel(), "45%");
			
		}
		return this.headerPanel;
	}
	
	private HorizontalPanel getFooterPanel(){
		if(this.footerPanel==null){
			this.footerPanel = new HorizontalPanel();
			this.footerPanel.addStyleName("footerPanel");
			this.footerPanel.add(getLinksPanel());
		}
		return this.footerPanel;
	}
	
	private HorizontalPanel getLinksPanel(){
		if(this.linksPanel==null){
			this.linksPanel = new HorizontalPanel();
			this.linksPanel.setSpacing(20);
			this.linksPanel.add(new HTML("&copy; <a href=\"http://sourceforge.net/projects/viewerfree/\">ViewerFree 3.5.3-SNAPSHOT</a>"+
					". GNU General Public License (GPL). Design by"+
					"<a href=\"http://javi.viewerfree.es\">Javier Benito</a>"));
		}
		return this.linksPanel;
	}
	
	private HorizontalPanel getContentPanel(){
		if(this.contentPanel==null){
			this.contentPanel = new HorizontalPanel();
			this.contentPanel.addStyleName("footerPanel");
			this.contentPanel.setWidth("100%");
		}
		return this.contentPanel;
	}
	
	private HorizontalPanel getTitlePanel(){
		if(this.titlePanel==null){
			this.titlePanel = new HorizontalPanel();
			this.titlePanel.setSpacing(5);
			this.titlePanel.add(getTitleViewerfree());
			this.titlePanel.setCellHorizontalAlignment(getTitleViewerfree(), HorizontalPanel.ALIGN_RIGHT);
			this.titlePanel.setCellVerticalAlignment(getTitleViewerfree(), HorizontalPanel.ALIGN_BOTTOM);
			this.titlePanel.add(getTitleBy());
			this.titlePanel.setCellHorizontalAlignment(getTitleBy(), HorizontalPanel.ALIGN_LEFT);
			this.titlePanel.setCellVerticalAlignment(getTitleBy(), HorizontalPanel.ALIGN_BOTTOM);
		}
		return this.titlePanel;
	}
	
	private HorizontalPanel getUserPanel(){
		if(this.userPanel==null){
			this.userPanel = new HorizontalPanel();
			this.userPanel.setSpacing(5);
			this.userPanel.add(getDateLabel());
			this.userPanel.setCellHorizontalAlignment(getDateLabel(), HorizontalPanel.ALIGN_RIGHT);
			this.userPanel.setCellVerticalAlignment(getDateLabel(), HorizontalPanel.ALIGN_MIDDLE);
			this.userPanel.add(getLanguageMenu());
			this.userPanel.setCellHorizontalAlignment(getLanguageMenu(), HorizontalPanel.ALIGN_LEFT);
			this.userPanel.setCellVerticalAlignment(getLanguageMenu(), HorizontalPanel.ALIGN_MIDDLE);
		}
		return this.userPanel;
	}
	
	private Label getTitleViewerfree(){
		if(this.titleViewerfree==null){
			this.titleViewerfree = new Label("Viewerfree");
			this.titleViewerfree.addStyleName("title");
		}
		return this.titleViewerfree;
	}
	
	private Label getTitleBy(){
		if(this.titleBy==null){
			this.titleBy = new Label("by Javier Benito");
			this.titleBy.addStyleName("titleBy");
		}
		return this.titleBy;
	}
	
	private Label getDateLabel(){
		if(this.dateLabel==null){
			this.dateLabel = new Label(DateUtil.getToDay());
			this.dateLabel.addStyleName("toDay");
		}
		return this.dateLabel;
	}
	
	private HorizontalPanel getLanguageMenu(){
		if(this.languagePanel==null){
			this.languagePanel = new HorizontalPanel();
			Hyperlink hyperlink = new Hyperlink("<img border=\"0\" src=\"/images/flags/es.gif\">",true,"");
			hyperlink.addStyleName("flag");
			languagePanel.add(hyperlink);
			Hyperlink hyperlink2 = new Hyperlink("<img border=\"0\" src=\"/images/flags/gb.gif\">",true,"");
			hyperlink2.addStyleName("flag");
			languagePanel.add(hyperlink2);
		}
		return this.languagePanel;
	}
	

	
}
