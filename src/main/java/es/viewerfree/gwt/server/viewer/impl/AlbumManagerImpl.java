package es.viewerfree.gwt.server.viewer.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;

import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.server.viewer.ManageImage;
import es.viewerfree.gwt.shared.dto.UserDto;


public class AlbumManagerImpl implements AlbumManager {
	
	private  FilenameFilter dirfilenameFilter;
	private String albumPath;
	private String applicationPath;
	private FilenameFilter filenameFilter;	
	private ManageImage manageImage;
	
	private static  Comparator<String> comparatorAlphanumeric = new ComparatorAlphanumeric();

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
		String[] albums = albumsPath.list(dirfilenameFilter);
		Arrays.sort(albums,comparatorAlphanumeric);
		return  albums;
	}
	
	public String[] getPictures(UserDto user,String albumName){

		File pictures = new File(getPathUser(user)+"/"+albumName);
		String[] pics = pictures.list(filenameFilter);
		Arrays.sort(pics,comparatorAlphanumeric);
		return pics;
	}
	
	public void getCachedPicture(UserDto user, String albumName,
			String pictureName, String cachedPath, int height,OutputStream out)
			throws Exception {
		File fotoCacheada = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName);
		if(!fotoCacheada.exists()){
			File path = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName);
			path.mkdirs();
			manageImage.resize(getPathUser(user)+"/"+albumName+"/"+pictureName,
					getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName,height);
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

	@Override
	public void getDefaultImage(OutputStream out) throws IOException {
		manageImage.getDefaultImage(out);
		
	}

	@Override
	public void rotateCachedPicture(UserDto user, String albumName,String[] cachedPaths,
			String pictureName,int angle)
			throws Exception {
		for(String cachedPath:cachedPaths){
		String pathname = getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName;
		File fotoCacheada = new File(pathname);
		if(fotoCacheada.exists()){
			manageImage.rotate(pathname, pathname,angle);
		}
			
			
		}
	}
	
	private static class ComparatorAlphanumeric implements Comparator<String>{

		@Override
		public int compare(String s1, String s2) {
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}

		
		
	}


}
