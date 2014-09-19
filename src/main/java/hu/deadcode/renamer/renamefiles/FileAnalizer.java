package hu.deadcode.renamer.renamefiles;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class FileAnalizer {
	public static Date getImgDate(File img) {
		Date date;
		Metadata metadata;
		try {
			metadata = ImageMetadataReader.readMetadata(img);
			date = getImgOriginalDate(metadata);
		} catch (NullPointerException | ImageProcessingException | IOException e) {
			date = getModificationDate(img);
		}
		return date;
	}

	public static Date getImgOriginalDate(Metadata metadata) throws NullPointerException {
		ExifSubIFDDirectory dir = metadata.getDirectory(ExifSubIFDDirectory.class);
		Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		if (date == null)
			throw new NullPointerException();
		return date;
	}

	public static Date getModificationDate(File file) {
		return new Date(file.lastModified());
	}

	public static String getExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	public static boolean isEquals(File file1, File file2) {
		boolean compareResult = false;
		try {
			compareResult = FileUtils.contentEquals(file1, file2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compareResult;
	}
}
