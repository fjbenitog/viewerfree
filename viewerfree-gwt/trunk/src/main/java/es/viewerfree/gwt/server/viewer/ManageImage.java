package es.viewerfree.gwt.server.viewer;

import java.io.IOException;
import java.io.OutputStream;

public interface ManageImage {

	public  void resize(String file,int height, OutputStream out)
			throws IOException;
	
	public void getDefaultImage(OutputStream out) throws IOException;

}