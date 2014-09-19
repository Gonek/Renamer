package hu.deadcode.renamer.ui;

import java.io.File;
import java.util.Scanner;

public class UI {
	public static String getPath() {
		System.out.println("Image directory: ");
		String input = "";
		try (Scanner in = new Scanner(System.in);) {
			input = in.nextLine();
		}
		return input;
	}

	public static void printRename(String oldFilename, String newFilename) {
		System.out.println(oldFilename + " >>> " + newFilename);
	}

	public static void printOut(String msg) {
		System.out.println(msg);
	}

	public static void printFileNames(File[] files) {
		for (File file : files) {
			System.out.println(file.getPath() + "\\" + file.getName());
		}
	}

	public static void PressKeyToClose() {
		System.out.println("Press <Enter> to exit... ");
		try (Scanner in = new Scanner(System.in);) {
			in.hasNext();
		}
	}
}
