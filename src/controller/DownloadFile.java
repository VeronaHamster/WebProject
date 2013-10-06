package controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadFile
 */
@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filePath = ConstantProject.APK_FILE_PATH;
		File file = new File(filePath);
		ServletOutputStream outStream = response.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filePath);
		if (mimetype == null) {
			mimetype = "application/octet-stream";
		}
		response.setContentType(mimetype);
		response.setContentLength((int) file.length());
		response.setHeader(
				ConstantProject.CONTENT_DISPOSITION,
				"attachment; filename*=utf-8\''"
						+ URLEncoder.encode(ConstantProject.APK_FILE_NAME,
								ConstantProject.N_CODE).replace("+", "%20"));
		downloadFile(outStream, file);
	}

	private void downloadFile(ServletOutputStream os, File file)
			throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		byte[] byteBuffer = new byte[1024];
		int length = 0;

		while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
			os.write(byteBuffer, 0, length);
		}

		in.close();
	}

}
