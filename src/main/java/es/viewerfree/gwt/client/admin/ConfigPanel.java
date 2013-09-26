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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import es.viewerfree.gwt.client.Constants;
import es.viewerfree.gwt.client.ViewerFreeMessages;
import es.viewerfree.gwt.client.common.ConfirmDialogBox;
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

	private Image loaderImage;

	private  ConfirmDialogBox confirmDialogBox;

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
			this.buttonsPanel.add(getCleanChacheButton());
		}
		return this.buttonsPanel;
	}

	private Button getApplyChangesButton(){
		if(this.applyChangesButton == null){
			this.applyChangesButton = new Button(messages.applyChangesLabel());
			this.applyChangesButton.addClickHandler(new ClickButtonHandler() {

				@Override
				public void startAction() {
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
							setError("No fue posible actualizar las propiedades del sistema.");
						}
					});
				}

				@Override
				protected String getConfirmMessage() {
					return messages.confirmChangeProperties();
				}

			});
		}
		return this.applyChangesButton;
	}

	private Button getReloadButton(){
		if(this.reloadButton == null){
			this.reloadButton = new Button(messages.reloadLabel());
			this.reloadButton.addClickHandler(new ClickButtonHandler() {

				@Override
				public void startAction() {
					configService.reload(new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.Location.replace(GWT.getHostPageBaseURL()+constants.adminPath()+"?locale="+LocaleInfo.getCurrentLocale().getLocaleName());
						}

						@Override
						public void onFailure(Throwable caught) {
							setError("No fue posible recargar el sistema.");
						}
					});
				}

				@Override
				protected String getConfirmMessage() {
					return messages.confirmReload();
				}


			});
		}
		return this.reloadButton;
	}


	private Button getCleanChacheButton(){
		if(this.cleanChacheButton == null){
			this.cleanChacheButton = new Button(messages.cleanCacheLabel());
			this.cleanChacheButton.addClickHandler(new ClickButtonHandler() {

				@Override
				public void startAction() {
					initAction();
					configService.cleanCache(new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							setMessage("Cache Limpiada con éxito.");
						}

						@Override
						public void onFailure(Throwable caught) {
							setError("No fue posible limpiar la cache del sistema.");
						}
					});
				}

				@Override
				protected String getConfirmMessage() {
					return messages.confirmCleanCache();
				}
			});
		}
		return this.cleanChacheButton;
	}

	private void initAction(){
		if(confirmDialogBox!=null)
			confirmDialogBox.hide();
		enableButtons(false);
		getButtonsPanel().add(getLoaderImage());
	}


	private void endAction(){
		getButtonsPanel().remove(getLoaderImage());
		enableButtons(true);
	}

	private void enableButtons(boolean enable) {
		for (int i = 0; i < getButtonsPanel().getWidgetCount(); i++) {
			Widget widget = getButtonsPanel().getWidget(i);
			if(widget instanceof Button){
				((Button)widget).setEnabled(enable);
			}

		}
	}

	private Image getLoaderImage(){
		if(this.loaderImage == null){
			this.loaderImage = new Image(constants.adminImagesPath()+constants.imageLoader());
		}
		return this.loaderImage;
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
		setMessage("message",message);
	}

	private void setError(String message) {
		setMessage("error",message);
	}

	private void setMessage(String type,String message) {
		endAction();
		getFormPanel().setHTML(0, 0,"<div class='"+type+"'>"+message+"</div>");
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

	private  abstract class ClickButtonHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			confirmDialogBox = new ConfirmDialogBox(getConfirmMessage());
			confirmDialogBox.addAcceptClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					initAction();
					startAction();
				}
			});
			confirmDialogBox.invoke();
		}

		protected abstract void startAction();

		protected abstract String getConfirmMessage();

	}
}
