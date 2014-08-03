package hu.deadcode.renamer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.java2d.pipe.NullPipe;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class Logic {

	private String directory;
	private File[] imageFiles;

	public Logic(String directory) {
		super();
		this.directory = directory;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public File[] getImageFiles() {
		return imageFiles;
	}

	public int RenameFiles() {
		imageFiles = ListPicturesInDirectory();
		return RenameImgFiles(imageFiles);
	}

	private File[] ListPicturesInDirectory() {
		File f = new File(directory);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toUpperCase();
				return name.endsWith("JPG") || 
					   name.endsWith("JPEG") || 
					   name.endsWith("MP4") ||
					   name.endsWith("MOV") ||
					   name.endsWith("GIF");
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
		} catch (NullPointerException| ImageProcessingException | IOException e) {
			date = GetModificationDate(img);
		}
		return date;
	}

	private Date GetImgOriginalDate(Metadata metadata) throws NullPointerException {
		ExifSubIFDDirectory dir = metadata.getDirectory(ExifSubIFDDirectory.class);
		Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		if (date == null) throw new NullPointerException();
		return date;
	}

	private Date GetModificationDate(File file) {
		return new Date(file.lastModified());
	}

	private String GetNewFileName(Date date, int num, String extension) {
		return directory + "\\" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date) + (num > 0 ? "(" + Integer.toString(num) + ")" : "") + "." + extension;
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
	
	private String GetExtension(File file){
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
}
