package es.viewerfree.gwt.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.shared.DateUtil;

public class HeaderPanel extends HorizontalPanel {

	private HorizontalPanel titlePanel;
	private Label titleViewerfree;
	private Label titleBy;
	private HorizontalPanel userPanel;
	private Label dateLabel;
	private HorizontalPanel languagePanel;
	
	private final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private final Constants constants = GWT.create(Constants.class);
	
	public HeaderPanel() {
		super();
		this.addStyleName("headerPanel");
		this.setSpacing(20);
		this.add(getTitlePanel());
		this.setCellWidth(getTitlePanel(), "55%");
		this.add(getUserPanel());
		this.setCellHorizontalAlignment(getUserPanel(), HorizontalPanel.ALIGN_RIGHT);
		this.setCellVerticalAlignment(getUserPanel(), HorizontalPanel.ALIGN_MIDDLE);
		this.setCellWidth(getUserPanel(), "45%");
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
	
	private Label getTitleViewerfree(){
		if(this.titleViewerfree==null){
			this.titleViewerfree = new Label(constants.viewerfree());
			this.titleViewerfree.addStyleName("title");
		}
		return this.titleViewerfree;
	}
	
	private Label getTitleBy(){
		if(this.titleBy==null){
			this.titleBy = new Label(messages.byName(constants.owner()));
			this.titleBy.addStyleName("titleBy");
		}
		return this.titleBy;
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
			for(String language:constants.languange()){
				HTML flag = new HTML("<a href=\"?locale="+language+"\"><img border=\"0\" src=\""+constants.flagsPath()+language+".gif"+"\"></a>");
				flag.addStyleName("flag");
				languagePanel.add(flag);
			}
		}
		return this.languagePanel;
	}
	
}
