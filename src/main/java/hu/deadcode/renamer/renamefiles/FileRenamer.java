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
			if(renameImgFile(img)){
				count++;
			}
		}
		return count;
	}
	
	private boolean renameImgFile(File oldImg) {
		try{
			File newImg = getNewFile(oldImg);
			oldImg.renameTo(newImg);
			UI.printRename(oldImg.getName(), newImg.getName());
			return true;
		}catch(Exception e){
			UI.printErrorOnRename(e.getMessage(), oldImg.getName());
			return false;	
		}
	}

	private String getNewFileName(Date date, int num, String extension) {
		return ConfigController.getOutputPath() + "\\" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date)
				+ (num > 0 ? "(" + Integer.toString(num) + ")" : "") + "." + extension;
	}

	private File getNewFile(File oldFile) throws Exception {
		File file;
		int num = 0;
		boolean newNameFound = false;
		do {
			file = new File(getNewFileName(getImgDate(oldFile), num, getExtension(oldFile)));
			if(!file.exists()){
				newNameFound = true;
			}else if(isEquals(oldFile, file)){
				throw new Exception("File already exist");
			}else{
				num++;
			}
		} while (!newNameFound);

		return file;
	}

	public List<File> getImageFiles() {
		return imageFiles;
	}
}
