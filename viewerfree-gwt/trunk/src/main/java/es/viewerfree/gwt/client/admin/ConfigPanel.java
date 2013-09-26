package es.viewerfree.gwt.client.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.service.ConfigurationService;
import es.viewerfree.gwt.client.service.ConfigurationServiceAsync;
import es.viewerfree.gwt.client.util.MessageDialogUtil;
import es.viewerfree.gwt.shared.dto.ConfigDto;

public class ConfigPanel extends LayoutPanel{
	
	private static final ViewerFreeMessages messages = GWT.create(ViewerFreeMessages.class);
	
	private static final ConfigurationServiceAsync configService = GWT.create(ConfigurationService.class);
	
	private final Constants constants = GWT.create(Constants.class);

	private LayoutPanel mainPanel;
	
	private HorizontalPanel buttonsPanel;
	
	private Button applyChangesButton;
	
	private Button reloadButton;
	
	private Button cleanChacheButton;
	
	private ScrollPanel configScrollPanel;
	
	private FlexTable formPanel;
	
	private int column;

	private int row = 1;
	
	public ConfigPanel() {
		add(getMainPanel());
	}
	
	private LayoutPanel getMainPanel(){
		if(this.mainPanel == null){
			this.mainPanel = new LayoutPanel();
			this.mainPanel.add(getButtonsPanel());
			this.mainPanel.setWidgetTopHeight(getButtonsPanel(), 20, Unit.PX, 40, Unit.PCT);
			this.mainPanel.setWidgetLeftWidth(getButtonsPanel(), 45, Unit.PX, 100, Unit.PCT);
			this.mainPanel.add(getListScrollPanel());
			this.mainPanel.setWidgetLeftRight(getListScrollPanel(), 40, Unit.PX, 40, Unit.PX);
			this.mainPanel.setWidgetTopBottom(getListScrollPanel(), 80, Unit.PX, 100, Unit.PX);
		}
		return this.mainPanel;
	}
	

	private HorizontalPanel getButtonsPanel(){
		if(this.buttonsPanel==null){
			this.buttonsPanel = new HorizontalPanel();
			this.buttonsPanel.setSpacing(10);
			this.buttonsPanel.add(getApplyChangesButton());
			this.buttonsPanel.add(getReloadButton());
		}
		return this.buttonsPanel;
	}
	
	private Button getApplyChangesButton(){
		if(this.applyChangesButton == null){
			this.applyChangesButton = new Button(messages.applyChangesLabel());
			this.applyChangesButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					List<ConfigDto> configDtos = new ArrayList<ConfigDto>();
					for (int i = 0; i < getFormPanel().getRowCount(); i++) {
						for (int j = 1; j < getFormPanel().getCellCount(i); j+=2) {
							ConfigTextBox textBox = (ConfigTextBox) getFormPanel().getWidget(i, j);
							ConfigDto configDto = new ConfigDto();
							configDto.setFileName(textBox.getFileName());
							configDto.setKey(textBox.getName());
							configDto.setLabel(textBox.getTitle());
							configDto.setValue(textBox.getValue());
							configDtos.add(configDto);
						}
					}
					 
					configService.updateConfigValues(configDtos, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							setMessage("Actualización realizada con exito. Pulse en recargar para hacer efectivas las nuevas propiedades del sistema.");
						}
						
						@Override
						public void onFailure(Throwable caught) {
							MessageDialogUtil.getErrorDialogBox("Error Updating Config Properties ");
							
						}
					});
				}
			});
		}
		return this.applyChangesButton;
	}
	
	private Button getReloadButton(){
		if(this.reloadButton == null){
			this.reloadButton = new Button(messages.reloadLabel());
			this.reloadButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
					configService.reload(new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							Window.Location.replace(GWT.getHostPageBaseURL()+constants.adminPath()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName());
						}
						
						@Override
						public void onFailure(Throwable caught) {
							MessageDialogUtil.getErrorDialogBox("Error Reolading the server");
						}
					});
				}
			});
		}
		return this.reloadButton;
	}
	
	
	private Button getCleanChacheButton(){
		if(this.cleanChacheButton == null){
			this.cleanChacheButton = new Button(messages.reloadLabel());
			this.cleanChacheButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent clickevent) {
				}
			});
		}
		return this.cleanChacheButton;
	}
	
	
	private ScrollPanel getListScrollPanel(){
		if(this.configScrollPanel == null){
			this.configScrollPanel = new ScrollPanel();
			this.configScrollPanel.add(getFormPanel());
			this.configScrollPanel.setStyleName("configForm");
		}
		return this.configScrollPanel;
	}
	
	
	private FlexTable getFormPanel(){
		if(this.formPanel == null){
			this.formPanel = new FlexTable();
			this.formPanel.setCellSpacing(6);
			this.formPanel.setWidth("100%");
			this.formPanel.setCellPadding(6);
			addConfigDtos();
		}
		return this.formPanel;
	}
	
	private void setMessage(String message) {
		getFormPanel().setHTML(0, 0,"<div class='message'>"+message+"</div>");
		FlexCellFormatter cellFormatter = getFormPanel().getFlexCellFormatter();
		cellFormatter.setColSpan(0, 0, 4);
		cellFormatter.setHorizontalAlignment(0,0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	private void addConfigDtos() {
		configService.getConfigValues(new AsyncCallback<List<ConfigDto>>() {
			
			@Override
			public void onSuccess(List<ConfigDto> configDtos) {
				addPropertyFields(configDtos);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageDialogUtil.getErrorDialogBox("Error loading Config Properties ");
			}
		});
	}
	
	private void addPropertyFields(List<ConfigDto> configDtos){
		for (ConfigDto configDto : configDtos) {
			ConfigTextBox field= new ConfigTextBox();
			field.setWidth("300px");
			field.setValue(configDto.getValue());
			field.setTitle(configDto.getLabel());
			field.setName(configDto.getKey());
			field.setFileName(configDto.getFileName());
			addField(configDto.getLabel(), field);
		}
		
	}
	
	private void addField(String label, Widget field) {

		this.formPanel.setText(row, column, label);
		this.formPanel.setWidget(row, column+1, field);
		column+=2;
		if(column>2){
			column=0;
			row++;
		}
	}
	
	private static class ConfigTextBox extends TextBox{
		private String fileName;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}
	
}
