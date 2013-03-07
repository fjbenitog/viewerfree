package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;
import java.util.List;

public class AlbumDto implements Serializable{

	private static final long serialVersionUID = -3696606355765868550L;

	private String name;
	
	private String cryptedName;
	
	private List<PictureDto> pictures;
	
	private int selectedPic;

	public AlbumDto(String name, List<PictureDto> pictures) {
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

	public List<PictureDto> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureDto> pictures) {
		this.pictures = pictures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cryptedName == null) ? 0 : cryptedName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + selectedPic;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlbumDto other = (AlbumDto) obj;
		if (cryptedName == null) {
			if (other.cryptedName != null)
				return false;
		} else if (!cryptedName.equals(other.cryptedName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pictures == null) {
			if (other.pictures != null)
				return false;
		} else if (!pictures.equals(other.pictures))
			return false;
		if (selectedPic != other.selectedPic)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlbumDto [name=" + name + ", cryptedName=" + cryptedName
				+ ", pictures=" + pictures + ", selectedPic=" + selectedPic
				+ "]";
	}


	
}
