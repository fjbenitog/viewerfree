package es.viewerfree.gwt.server.viewer;

import java.io.IOException;
import java.io.OutputStream;

public interface ManageImage {

	public  void resize(String inImage,String outImage,int height)
			throws Exception;
	
	public void getDefaultImage(OutputStream out) throws IOException;
	
	public void rotate(String inImage,String outImage,int angle) throws Exception;

}