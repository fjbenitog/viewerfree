package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;

public class PictureDto implements Serializable{


	private static final long serialVersionUID = -99165239489838386L;

	private String name;
	
	private String criptedName;

	public PictureDto() {
		super();
	}

	public PictureDto(String name, String criptedName) {
		super();
		this.name = name;
		this.criptedName = criptedName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCriptedName() {
		return criptedName;
	}

	public void setCriptedName(String criptedName) {
		this.criptedName = criptedName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((criptedName == null) ? 0 : criptedName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PictureDto other = (PictureDto) obj;
		if (criptedName == null) {
			if (other.criptedName != null)
				return false;
		} else if (!criptedName.equals(other.criptedName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PictureDto [name=" + name + ", criptedName=" + criptedName
				+ "]";
	}
	
	
}
