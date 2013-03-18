package es.viewerfree.gwt.server.viewer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;

import es.viewerfree.gwt.server.dao.DaoException;
import es.viewerfree.gwt.server.dao.ITagDao;
import es.viewerfree.gwt.server.dao.IUserDao;
import es.viewerfree.gwt.server.entities.User;
import es.viewerfree.gwt.server.viewer.AlbumManager;
import es.viewerfree.gwt.server.viewer.ManageImage;


public class AlbumManagerImpl implements AlbumManager {

	private  FilenameFilter dirfilenameFilter;
	private String albumPath;
	private String applicationPath;
	private FilenameFilter filenameFilter;	
	private ManageImage manageImage;

	private static  Comparator<String> comparatorAlphanumeric = new ComparatorAlphanumeric();
	
	private ITagDao tagDao;
	
	private IUserDao userDao;

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public ITagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}

	public ManageImage getManageImage() {
		return manageImage;
	}

	public void setManageImage(ManageImage manageImage) {
		this.manageImage = manageImage;
	}

	public List<String> getAlbums(){
		String[] albums = getAlbumsPath().list(dirfilenameFilter);
		Arrays.sort(albums,comparatorAlphanumeric);
		return  Arrays.asList(albums);
	}


	public List<String> getPictures(String albumName){

		File pictures = new File(getPath()+"/"+albumName);
		String[] pics = pictures.list(filenameFilter);
		Arrays.sort(pics,comparatorAlphanumeric);
		return Arrays.asList(pics);
	}

	public void getCachedPicture(String albumName, String pictureName,
			String cachedPath, int height, OutputStream out)
					throws Exception {
		File fotoCacheada = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName);
		if(!fotoCacheada.exists()){
			File path = new File(getApplicationPath()+"/"+cachedPath+"/"+albumName);
			path.mkdirs();
			manageImage.resize(getPath()+"/"+albumName+"/"+pictureName,
					getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName,height);
		}

		getPicture(new File(getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName), out);

	}

	public void getPicture(File file,OutputStream out) throws IOException{
		IOUtils.copyLarge(new AutoCloseInputStream(new FileInputStream(file)), out);
	}

	public void getPicture(String albumName,String pictureName,OutputStream out) throws IOException{
		getPicture(new File(getPath()+"/"+albumName+"/"+pictureName),out);
	}
	
	@Override
	public void rotateCachedPicture(String albumName, String[] cachedPaths,String pictureName,
			int angle) throws Exception {
		for(String cachedPath:cachedPaths){
			String pathname = getApplicationPath()+"/"+cachedPath+"/"+albumName+"/"+pictureName;
			File fotoCacheada = new File(pathname);
			if(fotoCacheada.exists()){
				manageImage.rotate(pathname, pathname,angle);
			}
		}
	}

	@Override
	public void createAlbum(String albumName) {
		File album = new File(getAlbumsPath(), albumName) ;
		album.mkdir();
	}

	@Override
	public void deletePicture(String albumName, String pciture) throws IOException {
		File pictureFile = new File(getPath()+"/"+albumName+"/"+pciture);
		if(!pictureFile.delete()){
			throw new IOException("The file was not able to be deleted");
		}
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

	private String getPath() {
		return getApplicationPath()+"/"+getAlbumPath();
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
	public void addTag(String userName, String albumName, String tagName) throws DaoException {
		User user = userDao.getUser(userName);
		tagDao.addTag(user, albumName, tagName);
	}
	
	@Override
	public List<String> getAlbums(String userName, String tagName) {
		return tagDao.getAlbumsByTag(userName, tagName);
	}

	private File getAlbumsPath() {
		File albumsPath = new File(getPath());
		if(!albumsPath.exists()){
			albumsPath.mkdirs();
		}
		return albumsPath;
	}

	private static class ComparatorAlphanumeric implements Comparator<String>{

		@Override
		public int compare(String s1, String s2) {
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}



	}

}
