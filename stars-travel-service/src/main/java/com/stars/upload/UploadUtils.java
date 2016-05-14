package com.stars.upload;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class UploadUtils {

	/**
	 * 36个小写字母和数字
	 */
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	
	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("/yyyyMM/ddHHmmss");
	
	public static final DateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
	
	public static final DateFormat SHORT_FORMAT = new SimpleDateFormat("ddHHmmss");
	
	public static String generateMonthname(){
		return YEAR_MONTH_FORMAT.format(new Date());
	}
	
	public static String generateFilename(String path,String ext){
		return path + MONTH_FORMAT.format(new Date()) + RandomStringUtils.random(4, N36_CHARS) + "." + ext;
	}
	
	public static String generateByFilename(String path,String filename,String ext){
		return path + filename +"."+  ext;
	}
	
	public static final Pattern ILLEGAL_CURRENT_FOLDER_PATTERN = Pattern.compile("^[^/]|[^/]$|/\\.{1,2}|\\\\|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}");
	
	public static String sanitizeFileName(final String filename) {

		if (StringUtils.isBlank(filename))
			return filename;

		String name = forceSingleExtension(filename);

		// Remove \ / | : ? * " < > 'Control Chars' with _
		return name.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}
	
	public static String sanitizeFolderName(final String folderName) {

		if (StringUtils.isBlank(folderName))
			return folderName;

		// Remove . \ / | : ? * " < > 'Control Chars' with _
		return folderName.replaceAll(
				"\\.|\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}
	
	public static boolean isValidPath(final String path) {
		if (StringUtils.isBlank(path))
			return false;

		if (ILLEGAL_CURRENT_FOLDER_PATTERN.matcher(path).find())
			return false;

		return true;
	}
	
	public static String forceSingleExtension(final String filename) {
		return filename.replaceAll("\\.(?![^.]+$)", "_");
	}
	
	public static boolean isSingleExtension(final String filename) {
		return filename.matches("[^\\.]+\\.[^\\.]+");
	}
	
	public static void checkDirAndCreate(File dir) {
		if (!dir.exists())
			dir.mkdirs();
	}

	
	public static File getUniqueFile(final File file) {
		if (!file.exists())
			return file;

		File tmpFile = new File(file.getAbsolutePath());
		File parentDir = tmpFile.getParentFile();
		int count = 1;
		String extension = FilenameUtils.getExtension(tmpFile.getName());
		String baseName = FilenameUtils.getBaseName(tmpFile.getName());
		do {
			tmpFile = new File(parentDir, baseName + "(" + count++ + ")."
					+ extension);
		} while (tmpFile.exists());
		return tmpFile;
	}

	public static void main(String[] args) {
		System.out.println(generateFilename("/base", "gif"));
	}
}
