package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String savePath = ConstantProject.ASSET_FOLDER;
		String filename = ConstantProject.EMPTY_STRING;
		ServletInputStream in = request.getInputStream();
		ByteArrayOutputStream buffer = null;
		byte[] line = new byte[128];
		int i = in.readLine(line, 0, 128);
		int boundaryLength = i - 2;
		String boundary = new String(line, 0, boundaryLength); // -2 discards
																// the newline
		// graphics/ccc.gifcharacter
		while (i != -1) {
			String newLine = new String(line, 0, i);
			if (newLine.startsWith(ConstantProject.CONTENT_DISPOSITION
					+ ConstantProject.FORM_DATA_TEXT)) {
				i = in.readLine(line, 0, 128);

				newLine = new String(line, 0, i);

				buffer = new ByteArrayOutputStream();
				String text = null;
				while (i != -1 && !newLine.startsWith(boundary)) {
					i = in.readLine(line, 0, 128);
					newLine = new String(line, 0, i);
					if (!newLine.startsWith(boundary)) {
						buffer.write(line, 0, i);
					}
					if (!(i != -1 && !newLine.startsWith(boundary))) {
						byte[] bytes = buffer.toByteArray();
						text = new String(bytes, ConstantProject.N_CODE).trim();
						System.out.println(text);
					}
				}
				File file = new File(savePath + ConstantProject.TXT_FILE_NAME);
				PrintWriter projecText = new PrintWriter(file);
				if (text != null) {
					projecText.println(text);
				}
				projecText.close();
				buffer.close();
				buffer = null;

			} else if (newLine.startsWith(ConstantProject.CONTENT_DISPOSITION
					+ ConstantProject.FORM_DATA_IMAGE)) {
				String s = new String(line, 0, i - 2);
				int pos = s.indexOf(ConstantProject.POSITION_FILE_NAME);
				if (pos != -1) {
					String filepath = s.substring(pos + 10, s.length() - 1);
					// Windows browsers include the full path on the client
					// But Linux/Unix and Mac browsers only send the filename
					// test if this is from a Windows browser
					pos = filepath
							.lastIndexOf(ConstantProject.LAST_CHAR_FILE_NAME);
					if (pos != -1)
						filename = filepath.substring(pos + 1);
					else
						filename = filepath;
				}

				// this is the file content
				i = in.readLine(line, 0, 128);
				i = in.readLine(line, 0, 128);
				// blank line
				i = in.readLine(line, 0, 128);

				buffer = new ByteArrayOutputStream();
				newLine = new String(line, 0, i);

				while (i != -1 && !newLine.startsWith(boundary)) {
					// the problem is the last line of the file content
					// contains the new line character.
					// So, we need to check if the current line is
					// the last line.
					buffer.write(line, 0, i);
					i = in.readLine(line, 0, 128);
					newLine = new String(line, 0, i);
				}
				try {
					// save the uploaded file
					RandomAccessFile f = new RandomAccessFile(savePath
							+ ConstantProject.IMAGE_FILE_NAME
							+ getFileType(filename), "rw");
					byte[] bytes = buffer.toByteArray();
					f.write(bytes, 0, bytes.length - 2);
					f.close();
				} catch (Exception e) {
				}
			}
			i = in.readLine(line, 0, 128);
		}

		String[] outlist = runCommand(ConstantProject.COMMAND_FOR_COMPILE_APK_USING_MAVEN);
		for (i = 0; i < outlist.length; i++) {
			System.out.println(outlist[i]);
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				ConstantProject.JUMP_TO_DOWNLOAD_PAGE);
		rd.forward(request, response);
	}

	private String getFileType(String fileName) {
		return fileName.substring(fileName.indexOf(ConstantProject.SEPARATOR));
	}

	private String[] runCommand(String cmd) throws IOException {

		// The actual procedure for process execution:
		// runCommand(String cmd);
		// Create a list for storing output.
		List<String> list = new ArrayList<String>();
		// Execute a command and get its process handle
		Process proc = Runtime.getRuntime().exec(cmd);
		// Get the handle for the processes InputStream
		InputStream istr = proc.getInputStream();
		// Create a BufferedReader and specify it reads
		// from an input stream.

		BufferedReader br = new BufferedReader(new InputStreamReader(istr));
		String str; // Temporary String variable
		// Read to Temp Variable, Check for null then
		// add to (ArrayList)list
		while ((str = br.readLine()) != null)
			list.add(str);
		// Wait for process to terminate and catch any Exceptions.
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			System.err.println(Exceptions.ERROR_PROCES);
		}
		// Note: proc.exitValue() returns the exit value.
		// (Use if required)
		br.close(); // Done.
		// Convert the list to a string and return
		return (String[]) list.toArray(new String[0]);
	}

}
