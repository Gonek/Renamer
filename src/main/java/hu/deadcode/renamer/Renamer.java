package hu.deadcode.renamer;

import static hu.deadcode.renamer.config.ConfigController.*;
import hu.deadcode.renamer.config.ConfigController;
import hu.deadcode.renamer.renamefiles.FileRenamer;
import hu.deadcode.renamer.ui.UI;

public class Renamer {
	private static FileRenamer fileRenamer = new FileRenamer();

	public static void main(String[] args) {
		try {
			ConfigController.checkConfigParams();
			UI.printOut("Finding image files in : " + getInputPath());
			int count = fileRenamer.renameFiles();
			UI.printOut("The program rename " + Integer.toString(count) + " files");
		} catch (Exception e) {
			System.out.println("ERROR! " + e.getMessage());
		}
		UI.PressKeyToClose();
	}
}
