package hu.deadcode.renamer;

import java.io.File;
import java.util.Scanner;

public class UI {
	public static String GetPath(){
		System.out.println("Image directory: ");
		Scanner in = new Scanner(System.in);
		return in.nextLine();
	}
	
	public static void PrintRename(String oldFilename, String newFilename){
		System.out.println(oldFilename + " >>> " + newFilename);
	}
	
	public static void PrintOut(String msg){
		System.out.println(msg);
	}
	
	public static void PrintFileNames(File[] files){
		for(File file:files){
			System.out.println(file.getPath() + "\\" + file.getName());
		}
	}
}
