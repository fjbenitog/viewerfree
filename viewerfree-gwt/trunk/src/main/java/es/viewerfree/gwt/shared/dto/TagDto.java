package es.viewerfree.gwt.shared.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TagDto {

	private String name;
	
	private List<String> albums;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "albums")
	@XmlElement(name = "album")
	public List<String> getAlbums() {
		return albums;
	}

	public void setAlbums(List<String> albums) {
		this.albums = albums;
	}
	
	
}
