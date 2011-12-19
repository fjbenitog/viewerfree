package es.viewerfree.gwt.server.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;


public  class FileFilter implements FilenameFilter{

	private String[] _includes;
	private String[] _excludes;
	private boolean _default = true;

	//TODO: Cambiarlo por expresiones regulares
	
	
	public FileFilter(String includes, String excludes,boolean default_) {
		_includes= includes.split(",");
		_excludes = excludes.split(",");
		_default = default_;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_default ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(_excludes);
		result = prime * result + Arrays.hashCode(_includes);
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
		final FileFilter other = (FileFilter) obj;
		if (_default != other._default)
			return false;
		if (!Arrays.equals(_excludes, other._excludes))
			return false;
		if (!Arrays.equals(_includes, other._includes))
			return false;
		return true;
	}

	public boolean accept(File dir, String name) {
		for (String exclude : _excludes) {
			if(name.toLowerCase().endsWith(exclude.toLowerCase()))
				return false;
		}
		for (String include : _includes) {
			if(name.toLowerCase().endsWith(include.toLowerCase())){
				return true;
			}
		}
		return _default;
	}
	
}