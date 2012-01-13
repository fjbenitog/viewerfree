package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;

public class AlbumDto implements Serializable{

	private static final long serialVersionUID = -3696606355765868550L;

	private String name;
	
	private String cryptedName;
	
	private String[] pictures;
	
	private String[] cryptedPictures;
	
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

	public String getCryptedName() {
		return cryptedName;
	}

	public void setCryptedName(String cryptedName) {
		this.cryptedName = cryptedName;
	}

	public String[] getCryptedPictures() {
		return cryptedPictures;
	}

	public void setCryptedPictures(String[] cryptedPictures) {
		this.cryptedPictures = cryptedPictures;
	}
	
}
