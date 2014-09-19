package hu.deadcode.renamer.findfiles;

import hu.deadcode.renamer.config.ConfigController;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileFinder {
	public static List<File> listFilesInDirectory() {
		File f = new File(ConfigController.getInputPath());
		File[] filesArray = f.listFiles(new DefaultFilenameFilter());
		List<File> filesList = Arrays.asList(filesArray);
		Collections.sort(filesList, new FileNameComparator());
		return filesList;
	}
}
