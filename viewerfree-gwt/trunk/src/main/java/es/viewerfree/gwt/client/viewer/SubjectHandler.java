package es.viewerfree.gwt.client.viewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;

import es.viewerfree.gwt.client.viewer.ClickItemObserver.Type;


public final class SubjectHandler implements ClickHandler , OpenHandler<DisclosurePanel>, CloseHandler<DisclosurePanel>{

	private String name;
	private Type type;
	
	public SubjectHandler(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public void onClick(ClickEvent event) {
		update();
	}

	@Override
	public void onClose(CloseEvent<DisclosurePanel> event) {
		update();
	}

	@Override
	public void onOpen(OpenEvent<DisclosurePanel> event) {
		update();
	}
	private void update() {
		Subject.getInstance().update(type, name);
	}
}