package es.viewerfree.gwt.client.viewer;

import java.util.HashSet;
import java.util.Set;

import es.viewerfree.gwt.client.viewer.left.AlbumTagObserver;
import es.viewerfree.gwt.client.viewer.left.AlbumTagObserver.Type;

public class Subject {
	private Set<AlbumTagObserver> albumTagObservers = new HashSet<AlbumTagObserver>();

	private static Subject subject = new Subject();
	
	private Subject(){
	}
	
	public static Subject getInstance(){
		return subject;
	}
	
	public void addObserver(AlbumTagObserver albumTagObserver){
		albumTagObservers.add(albumTagObserver);
	}
	
	public void removeObserver(AlbumTagObserver albumTagObserver){
		albumTagObservers.remove(albumTagObserver);
	}
	
	public void notify(Type type, String name){
		for (AlbumTagObserver observer : albumTagObservers) {
			observer.actualizar(type, name);
		}
	}
}
