package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;

public class AlbumDto implements Serializable{

	private static final long serialVersionUID = -3696606355765868550L;

	private String name;
	
	private String[] pictures;
	
	private int selectedPic;

	public AlbumDto(String name, String[] pictures) {
		super();
		this.name = name;
		this.pictures = pictures;
	}
	
	public AlbumDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getPictures() {
		return pictures;
	}

	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}

	public int getSelectedPic() {
		return selectedPic;
	}

	public void setSelectedPic(int picSelected) {
		this.selectedPic = picSelected;
	}
	
}
