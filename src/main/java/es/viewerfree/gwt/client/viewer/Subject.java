package es.viewerfree.gwt.client.viewer;

import java.util.HashSet;
import java.util.Set;

import es.viewerfree.gwt.client.viewer.ClickItemObserver.Type;

public class Subject {
	private Set<ClickItemObserver> clickItemObservers = new HashSet<ClickItemObserver>();
	
	private Set<ModificationObserver> modificationObservers = new HashSet<ModificationObserver>();

	private static Subject subject = new Subject();
	
	private Subject(){
	}
	
	public static Subject getInstance(){
		return subject;
	}
	
	public void addObserver(ClickItemObserver clickItemObserver){
		clickItemObservers.add(clickItemObserver);
	}
	
	public void removeObserver(ClickItemObserver clickItemObserver){
		clickItemObservers.remove(clickItemObserver);
	}
	
	public void addObserver(ModificationObserver modificationObserver){
		modificationObservers.add(modificationObserver);
	}
	
	public void removeObserver(ModificationObserver modificationObserver){
		modificationObservers.remove(modificationObserver);
	}
	
	public void update(Type type, String name){
		for (ClickItemObserver observer : clickItemObservers) {
			observer.update(type, name);
		}
	}
	
	public void update(){
		for (ModificationObserver observer : modificationObservers) {
			observer.update();
		}
	}
}
