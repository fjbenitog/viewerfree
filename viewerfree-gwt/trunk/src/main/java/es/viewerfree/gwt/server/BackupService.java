package es.viewerfree.gwt.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;

import es.viewerfree.gwt.server.service.IUserService;
import es.viewerfree.gwt.server.util.ServletUtils;
import es.viewerfree.gwt.shared.Action;
import es.viewerfree.gwt.shared.ParamKey;
import es.viewerfree.gwt.shared.service.ServiceException;

public class BackupService implements HttpRequestHandler{

	private IUserService userService;

	private static final int MAX_FILE_SIZE = 5242880;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Action action = Action.IMPORT_USERS;
			String parameter = request.getParameter(ParamKey.ACTION.toString());
			if(parameter!=null){
				action = Action.valueOf(parameter);
			}
			switch (action) {
			case EXPORT_USERS:
				exportUsers(request, response);
				break;
			default:
				importUsers(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void importUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PrintWriter writer = response.getWriter();
		if(getFileSize(request)>MAX_FILE_SIZE){
			writer.println("Error: Image File size too big(10Mb max)");
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		((DiskFileItemFactory) factory).setSizeThreshold(1000);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		userService.createUsersByXml(items.get(0).getString());
		writer.println("OK");
	}

	private void exportUsers(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException, ServiceException {
		response.setContentType("text/xml");
		response.setHeader("Content-Disposition","inline; filename="+URLEncoder.encode("Viewerfree-backup.xml","utf-8"));
		PrintWriter writer = response.getWriter();
		writer.print(userService.exportUsers(Arrays.asList(ServletUtils.getParameters(request, ParamKey.USERS))));
	}

	private int getFileSize(HttpServletRequest request) {
		String contentLength = request.getHeader("content-length");
		return StringUtils.hasText(contentLength)?Integer.parseInt(contentLength):0;
	}


	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


}
