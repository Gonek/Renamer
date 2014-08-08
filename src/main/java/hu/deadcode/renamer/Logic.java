package hu.deadcode.renamer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class Logic {

	private Config config;
	private File[] imageFiles;

	public Logic(Config config) {
		this.config = config;
	}

	public File[] getImageFiles() {
		return imageFiles;
	}

	public int RenameFiles() {
		imageFiles = ListPicturesInDirectory();
		return RenameImgFiles(imageFiles);
	}

	private File[] ListPicturesInDirectory() {
		File f = new File(config.GetInputPath());
		final List<String> fileTypes = config.GetFileTypes();
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				name = name.toUpperCase();
				boolean isFileType = false;
				for (String fileType : fileTypes) {
					isFileType = isFileType || name.endsWith(fileType);
				}
				return isFileType;
			}
		});
		return matchingFiles;
	}

	private int RenameImgFiles(File[] imageFiles) {
		int count = 0;
		for (File img : imageFiles) {
			File newImg = GetNewFile(img);
			img.renameTo(newImg);
			UI.PrintRename(img.getName(), newImg.getName());
			count++;
		}
		return count;
	}

	private Date GetImgDate(File img) {
		Date date;
		Metadata metadata;
		try {
			metadata = ImageMetadataReader.readMetadata(img);
			date = GetImgOriginalDate(metadata);
		} catch (NullPointerException | ImageProcessingException | IOException e) {
			date = GetModificationDate(img);
		}
		return date;
	}

	private Date GetImgOriginalDate(Metadata metadata) throws NullPointerException {
		ExifSubIFDDirectory dir = metadata.getDirectory(ExifSubIFDDirectory.class);
		Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		if (date == null)
			throw new NullPointerException();
		return date;
	}

	private Date GetModificationDate(File file) {
		return new Date(file.lastModified());
	}

	private String GetNewFileName(Date date, int num, String extension) {
		return config.GetOutputPath() + "\\" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date) + (num > 0 ? "(" + Integer.toString(num) + ")" : "")
				+ "." + extension;
	}

	private File GetNewFile(File oldFile) {
		File file;
		int num = 0;
		do {
			file = new File(GetNewFileName(GetImgDate(oldFile), num, GetExtension(oldFile)));
			num++;
		} while (file.exists());

		return file;
	}

	private String GetExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}
}
