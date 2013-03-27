package es.viewerfree.gwt.client.viewer.left;

public interface AlbumTagObserver {

	void actualizar(Type type, String name);
	
	public static enum Type {
		ALBUM, TAG, TOTAL_ALBUM, TOTAL_TAGS;
	}
	
}
