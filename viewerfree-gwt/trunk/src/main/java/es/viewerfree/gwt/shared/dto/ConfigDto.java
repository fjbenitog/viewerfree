package es.viewerfree.gwt.shared.dto;

import java.io.Serializable;

public class ConfigDto implements Serializable, Comparable<ConfigDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4017996463706804961L;

	private String key;
	
	private String value;
	
	private String label;
	
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return "ConfigDto [key=" + key + ", value=" + value + ", label="
				+ label + ", fileName=" + fileName + "]";
	}

	@Override
	public int compareTo(ConfigDto o) {
		return -String.CASE_INSENSITIVE_ORDER.compare(o.getLabel(), this.label);
	}



}
