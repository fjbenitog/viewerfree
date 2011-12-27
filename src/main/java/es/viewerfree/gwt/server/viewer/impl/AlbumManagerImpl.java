package es.viewerfree.gwt.server.viewer.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import javax.media.jai.PlanarImage;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.server.viewer.ManageImage;
import es.viewerfree.gwt.shared.dto.UserDto;


public class AlbumManagerImpl implements AlbumManager {
	
	private  FilenameFilter dirfilenameFilter;
	private String albumPath;
	private String applicationPath;
	private FilenameFilter filenameFilter;	
	private ManageImage manageImage;

	public ManageImage getManageImage() {
		return manageImage;
	}

	public void setManageImage(ManageImage manageImage) {
		this.manageImage = manageImage;
	}

	public String[] getAlbums(UserDto user){
		File albumsPath = new File(getPathUser(user));
		if(!albumsPath.exists()){
			albumsPath.mkdirs();
		}
		return  albumsPath.list(dirfilenameFilter);
	}
	
	public String[] getPictures(UserDto user,String albumName){

		File pictures = new File(getPathUser(user)+"/"+albumName);
		return pictures.list(filenameFilter);
	}
	
	public void getCachedPicture(UserDto user, String albumName,
			String pictureName, String cachedPath, int height,OutputStream out)
			throws IOException {
		File fotoCacheada = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName);
		if(!fotoCacheada.exists()){
			File path = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName);
			path.mkdirs();
			FileOutputStream fileOutputStream = new FileOutputStream(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName);
			createCachedPicture(user,albumName,pictureName,height,fileOutputStream);
			fileOutputStream.close();
		}

		getPicture(new File(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName), out);
		
	}
	
	public void getPicture(File file,OutputStream out) throws IOException{
		RandomAccessFile fileReader = new RandomAccessFile(file,"r");
		byte[]  temp = new byte[256];
		int off =0;
		int length = (int)file.length();
		while((length-off)>temp.length){
			fileReader.read(temp,0,temp.length);
			out.write(temp);
			off+=temp.length-1;
		}
		temp = new byte[(int) (file.length()-off)];
		fileReader.read(temp,0,temp.length);
		out.write(temp);
		fileReader.close();
	}
	
	public void getPicture(UserDto user,String albumName,String pictureName,OutputStream out) throws IOException{
		getPicture(new File(getPathUser(user)+"/"+albumName+"/"+pictureName),out);
	}
	
	
	//TODO: Cached Image
	public void getDefaultImage(OutputStream out) throws IOException{

		int width = 300; int height = 300;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		PlanarImage planarimage = PlanarImage.wrapRenderedImage(bi);
		// We need a sample model. The most appropriate is created as shown:
		Font font = new Font("SansSerif",Font.BOLD,40);
		Graphics graphics = bi.getGraphics();
		graphics.setFont(font);
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width-1, height-1);
		graphics.drawString("Imagen", 90, 130);
		graphics.drawString("No Disponible", 25, 190);
		JPEGEncodeParam encodeParam = new JPEGEncodeParam();
		ImageEncoder encoder = ImageCodec.createImageEncoder("JPEG", out, encodeParam);
		encoder.encode(planarimage);
	}
	
	private void createCachedPicture(UserDto user,String albumName,String pictureName, int height,OutputStream out) throws IOException{
		manageImage.resize(getPathUser(user)+"/"+albumName+"/"+pictureName, height,out);
	}

	public FilenameFilter getDirfilenameFilter() {
		return dirfilenameFilter;
	}

	public FilenameFilter getFilenameFilter() {
		return filenameFilter;
	}

	public void setFilenameFilter(FilenameFilter _filenameFilter) {
		this.filenameFilter = _filenameFilter;
	}

	public void setDirfilenameFilter(FilenameFilter dirfilenameFilter) {
		this.dirfilenameFilter = dirfilenameFilter;
	}

	private String getPathUser(UserDto user) {
		return getApplicationPath()+"/"+getAlbumPath()+"/"+user.getName();
	}

	public String getAlbumPath() {
		return albumPath;
	}

	public void setAlbumPath(String albumPath) {
		this.albumPath = albumPath;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}


}
