package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import exceptions.NotMultipartException;
import exceptions.UploadWrittingException;

/**
 * Service to handle clients uploads like profile images.
 * 
 * @author Daniel RADEAU
 *
 */

public class Data {

	public static String UPLOAD_DIRECTORY = null;
	public static String DOWNLOAD_PATH = "/KasuKasu/data/";

	private static int MEMORY_THRESHOLD   = 1024 * 1024 * 3; 
	private static int MAX_FILE_SIZE      = 1024 * 1024 * 40;
	private static int MAX_REQUEST_SIZE   = 1024 * 1024 * 50;

	/**
	 * Using the current request allow to upload any kind of file
	 * 
	 * @param request
	 * @throws NotMultipartException
	 * @throws FileUploadException
	 * @throws UploadWrittingException
	 */

	public static String uploadFile(HttpServletRequest request) 
			throws 	NotMultipartException,
			FileUploadException, 
			UploadWrittingException 
	{
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new NotMultipartException();
		} 
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		File uploadDir = new File(UPLOAD_DIRECTORY);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		File test = new File(".");
		try {
			System.out.println(test.getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println(UPLOAD_DIRECTORY);
		
		List<FileItem> formItems = upload.parseRequest(request);

		if (formItems != null) {
			for (FileItem item : formItems) {
				if (!item.isFormField()) {
					String filename = new File(item.getName()).getName();
					File storeFile = new File(UPLOAD_DIRECTORY + filename);

					try {
						item.write(storeFile);
						String path = storeFile.getAbsolutePath();
						path = path.replace("\\", "/");
						return path.replace(UPLOAD_DIRECTORY, DOWNLOAD_PATH);
					} catch (Exception e) {
						throw new UploadWrittingException();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Get data paths visible by client side
	 * 
	 * @return
	 */

	public static List<String> getDownloadPaths() {
		ArrayList<String> downloadPaths = new ArrayList<String>();

		try {
			for (File file : getFiles()) {
				String downloadPath = file.getAbsolutePath();
				downloadPath = downloadPath.replace(UPLOAD_DIRECTORY, DOWNLOAD_PATH);
				downloadPaths.add(downloadPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return downloadPaths;
	}

	/**
	 * Get files objects inside the server upload directory 
	 * 
	 * @return
	 * @throws NotDirectoryException
	 */

	public static File[] getFiles() throws NotDirectoryException {
		File uploadDirectory = new File(UPLOAD_DIRECTORY);

		if (uploadDirectory.isDirectory())
			return uploadDirectory.listFiles();
		else
			throw new NotDirectoryException("UPLOAD_DIRECTORY is not a directory");

	}

	/**
	 * Handle the file deletion by passing files paths.
	 * Files paths can be from client and server side.
	 * 
	 * @param filepaths
	 * @throws IOException
	 */

	public static void deleteFiles(List<String> filepaths) throws IOException {		
		for (String filepath : filepaths) {
			filepath = filepath.replace(DOWNLOAD_PATH, UPLOAD_DIRECTORY);
			File file = new File(filepath);
			if (file.exists())
				if (!file.delete())
					throw new IOException("File " + file.getAbsolutePath() + " couldn't been deleted");
		}

	}
}
