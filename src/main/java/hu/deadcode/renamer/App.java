package hu.deadcode.renamer;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String path = UI.GetPath();
    	UI.PrintOut("Finding image files in : " + path);
        Logic logic = new Logic(path);
        int count = logic.RenameFiles();
        UI.PrintOut("The program rename " + Integer.toString(count) + " files");
    }
}
