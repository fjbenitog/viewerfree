package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;
import java.util.List;

public class AlbumDto implements Serializable{

	private static final long serialVersionUID = -3696606355765868550L;

	private String name;
	
	private String cryptedName;
	
	private List<String> pictures;
	
	private List<String> cryptedPictures;
	
	private int selectedPic;

	public AlbumDto(String name, List<String> pictures) {
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

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
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

	public List<String> getCryptedPictures() {
		return cryptedPictures;
	}

	public void setCryptedPictures(List<String> cryptedPictures) {
		this.cryptedPictures = cryptedPictures;
	}
	
}
