package es.viewerfree.gwt.client.viewer;

public interface ClickItemObserver {

	void update(Type type, String name);
	
	public static enum Type {
		ALBUM, TAG, TOTAL_ALBUM, TOTAL_TAGS;
	}
	
}
