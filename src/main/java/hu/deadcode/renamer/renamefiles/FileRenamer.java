package hu.deadcode.renamer.renamefiles;

import static hu.deadcode.renamer.renamefiles.FileAnalizer.*;
import hu.deadcode.renamer.config.ConfigController;
import hu.deadcode.renamer.findfiles.FileFinder;
import hu.deadcode.renamer.ui.UI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileRenamer {

	private List<File> imageFiles;

	public int renameFiles() {
		imageFiles = FileFinder.listFilesInDirectory();
		return renameImgFiles(imageFiles);
	}

	private int renameImgFiles(List<File> imageFiles) {
		int count = 0;
		for (File img : imageFiles) {
			File newImg = getNewFile(img);
			img.renameTo(newImg);
			UI.printRename(img.getName(), newImg.getName());
			count++;
		}
		return count;
	}

	private String getNewFileName(Date date, int num, String extension) {
		return ConfigController.getOutputPath() + "\\" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date)
				+ (num > 0 ? "(" + Integer.toString(num) + ")" : "") + "." + extension;
	}

	private File getNewFile(File oldFile) {
		File file;
		int num = 0;
		do {
			file = new File(getNewFileName(getImgDate(oldFile), num, getExtension(oldFile)));
			num++;
		} while (file.exists() && !isEquals(oldFile, file));

		return file;
	}

	public List<File> getImageFiles() {
		return imageFiles;
	}
}
