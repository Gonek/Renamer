package hu.deadcode.renamer.findfiles;

import hu.deadcode.renamer.config.ConfigController;

import java.io.File;
import java.io.FilenameFilter;

public class DefaultFilenameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		name = name.toUpperCase();
		boolean isFileType = false;
		for (String fileType : ConfigController.getFileTypes()) {
			isFileType = isFileType || name.endsWith(fileType);
		}
		return isFileType;
	}

}
