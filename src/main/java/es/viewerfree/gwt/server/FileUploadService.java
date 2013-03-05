package es.viewerfree.gwt.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;

import es.viewerfree.gwt.shared.ParamKey;


public class FileUploadService implements HttpRequestHandler{

	private static final int MAX_FILE_SIZE = 5242880;

	private String albumPath;

	private String applicationPath;

	private Collection<String> fileExtensions;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		if(getFileSize(request)>MAX_FILE_SIZE){
			writer.println("Error: Image File size too big(10Mb max)");
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		((DiskFileItemFactory) factory).setSizeThreshold(1000);
		ServletFileUpload upload = new ServletFileUpload(factory); 
		
		try{
			List<FileItem> items = validateFileItem(upload.parseRequest(request));
			copyImageToAlbum(items, getAlbumName(items), validateImageFile(items));
			writer.println("OK");
		}
		catch(FileUploadException ex){
			writer.println(ex.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			writer.println("Error processing upload image");
		}
	}

	private int getFileSize(HttpServletRequest request) {
		String contentLength = request.getHeader("content-length");
		return StringUtils.hasText(contentLength)?Integer.parseInt(contentLength):0;
	}

	private List<FileItem> validateFileItem(List<FileItem> items)throws FileUploadException {
		if(items.size()<2){
			throw new FileUploadException("Error: Some field is missing.");
		}
		return items;
	}

	private void copyImageToAlbum(List<FileItem> items, String album,
			String imageName) throws  IOException, FileUploadException {
		File fileImage = new File(getPath()+"/"+album+"/"+imageName);
		if(fileImage.exists()){
			throw new FileUploadException("Error: This picture already exists in this album.");
		}
		FileOutputStream output = new FileOutputStream(fileImage);
		IOUtils.copyLarge(items.get(1).getInputStream(), output);
		IOUtils.closeQuietly(output);
	}

	private String validateImageFile(List<FileItem> items) throws FileUploadException {
		FileItem fileImage = items.get(1);
		String imageName = fileImage.getName();
		if(!FilenameUtils.isExtension(imageName, fileExtensions)){
			throw new FileUploadException("Error: Only image files are valid");
		}
		return imageName;
	}

	private String getAlbumName(List<FileItem> items) throws FileUploadException {
		String album = items.get(0).getString();
		if(!items.get(0).getFieldName().equals(ParamKey.ALBUM_NAME.toString()) || !StringUtils.hasText(album)){
			throw new FileUploadException("Error: Not album selected");
		}
		return album;
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

	public Collection<String> getFileExtensions() {
		return fileExtensions;
	}

	public void setFileExtensions(Collection<String> fileExtensions) {
		this.fileExtensions = fileExtensions;
	}

	private class FileUploadException extends Exception{

		public FileUploadException(String message) {
			super(message);
		}
		
	}
}
